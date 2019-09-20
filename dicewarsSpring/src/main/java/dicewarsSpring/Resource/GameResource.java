package dicewarsSpring.Resource;

import dicewarsSpring.ModelRepo.Board;
import dicewarsSpring.ModelRepo.Field;
import dicewarsSpring.Service.BoardService;
import dicewarsSpring.Service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameResource {

    @Autowired
    public GameService gameService;

    @Autowired
    public BoardService boardService;

    @Autowired
    public Board board;

    @PostMapping("/attack")
    public int[] attack(@RequestBody Field[] war) {
        return gameService.attack(board.getBoard()[war[0].getRow()][war[0].getCol()], board.getBoard()[war[1].getRow()][war[1].getCol()]);
    }


    @PostMapping("/endturn")
    public void endGame(@RequestBody String who) {
        if (who.equals("true")) {
            gameService.endOfTurn(true);
        } else {
            gameService.endOfTurn(false);
        }
    }

}
