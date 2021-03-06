package dicewarsSpring.Resource;

import dicewarsSpring.Model.Attack;
import dicewarsSpring.Model.Board;
import dicewarsSpring.Model.Field;
import dicewarsSpring.Service.GameService;
import dicewarsSpring.Service.RobotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GameResource {

    @Autowired
    public GameService gameService;

    @Autowired
    public RobotService robotService;

    @Autowired
    public Board board;

    @PostMapping("/attack")
    public int[] attack(@RequestBody Field[] war) {
        return gameService.attack(board.getBoard()[war[0].getRow()][war[0].getCol()], board.getBoard()[war[1].getRow()][war[1].getCol()]);
    }


    @PostMapping("/endturn")
    public void endTurn(@RequestBody String who) {
        if (who.equals("true")) {
            gameService.endOfTurn(true);
        } else {
            gameService.endOfTurn(false);
        }
    }

    @GetMapping("/robot")
    public List<Attack> robotAttack() {
        return robotService.robotAttacks();
    }

}
