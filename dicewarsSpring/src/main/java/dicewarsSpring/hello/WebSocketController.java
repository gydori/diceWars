package dicewarsSpring.hello;

import com.google.gson.Gson;
import dicewarsSpring.Model.Board;
import dicewarsSpring.Model.Field;
import dicewarsSpring.Service.BoardService;
import dicewarsSpring.Service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
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
    public String onReceivedMessage(String message) {
        //this.template.convertAndSend("/chat", new SimpleDateFormat("HH:mm:ss").format(new Date()) + "- " + message);
        //MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (message.startsWith("[")) {
            Gson gson = new Gson();
            Field[] war = gson.fromJson(message, Field[].class);
            try {
                int[] points = gameService.attack(board.getBoard()[war[0].getRow()][war[0].getCol()], board.getBoard()[war[1].getRow()][war[1].getCol()]);
                String json = new Gson().toJson(points);
                return json;
            } catch (ResponseStatusException e) {
                return new Gson().toJson(e);
            }
        }
        if (message.equals("getBoard")) {
            Field[] board = boardService.getBoard();
            String json = new Gson().toJson(board);
            return json;
        }
        if (message.equals("initBoard")) {
            Field[] board = boardService.initializeBoard();
            String json = new Gson().toJson(board);
            return json;
        }
        if (message.equals("true")) {
            gameService.endOfTurn(true);
            return "true";
        }
        if (message.equals("false")) {
            gameService.endOfTurn(false);
            return "false";
        }
        return null;
    }

/*    @SendToUser("/chat/{user}")
    @MessageMapping("/send/message/{user}")
    public String processMessageFromClient(@DestinationVariable String user, String message) throws Exception {
        System.out.println("itt vagyok");
        return "itt vagyok, true!";
    }*/
}
