package dicewarsSpring.hello;

import com.google.gson.Gson;
import dicewarsSpring.Model.Attack;
import dicewarsSpring.Model.Board;
import dicewarsSpring.Model.Field;
import dicewarsSpring.Model.SocketMessage;
import dicewarsSpring.Service.BoardService;
import dicewarsSpring.Service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class WebSocketController {
    @Autowired
    public GameService gameService;

    @Autowired
    public BoardService boardService;

    @Autowired
    public Board board;

    @SendTo("/chat")
    @MessageMapping("/send/message")
    public String onReceivedMessage(Message message, String messageS) {
        MessageHeaders headers = message.getHeaders();
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        MultiValueMap<String, String> multiValueMap = headers.get(StompHeaderAccessor.NATIVE_HEADERS, MultiValueMap.class);
        String header = multiValueMap.get("header").get(0);
        Gson gson = new Gson();
        if (header.equals("war")) {
            Field[] war = gson.fromJson(messageS, Field[].class);
            Attack attack = new Attack(war[0], war[1]);
            int[] points = gameService.attack(board.getBoard()[war[0].getRow()][war[0].getCol()], board.getBoard()[war[1].getRow()][war[1].getCol()]);
            attack.setInvaderPoints(points[0]);
            attack.setInvadedPoints(points[1]);
            SocketMessage sm = new SocketMessage("attack", attack);
            return gson.toJson(sm);
        }
        if (header.equals("emptyWar")) {
            SocketMessage sm = new SocketMessage("emptyWar", "emptyWar");
            return gson.toJson(sm);
        }
        if (header.equals("getBoard")) {
            try {
                Field[] board = boardService.getBoard();
                SocketMessage sm = new SocketMessage("getBoard", board);
                return gson.toJson(sm);
            } catch (ResponseStatusException e) {
                SocketMessage sm = new SocketMessage("endGame", e);
                return gson.toJson(sm);
            }
        }
        if (header.equals("initBoard")) {
            Field[] board = boardService.initializeBoard();
            SocketMessage sm = new SocketMessage("initBoard", board);
            return gson.toJson(sm);
        }
        if (header.equals("whosTurn")) {
            if (messageS.equals("true")) {
                gameService.endOfTurn(true);
                SocketMessage sm = new SocketMessage("whosTurn", "true");
                return gson.toJson(sm);
            } else {
                gameService.endOfTurn(false);
                SocketMessage sm = new SocketMessage("whosTurn", "false");
                return gson.toJson(sm);
            }
        }

        return null;
    }
}
