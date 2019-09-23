package dicewarsSpring.Resource;

import dicewarsSpring.ModelRepo.Attack;
import dicewarsSpring.ModelRepo.Board;
import dicewarsSpring.Service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RobotService {
    @Autowired
    private Board board;

    @Autowired
    private GameService gameService;

    public List<Attack> choose() {
        int id = 0;
        List<Attack> robotAttackFields = new ArrayList<>();
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (board.getBoard()[i][j].getOwner() == false && j < board.getSize() - 1 && board.getBoard()[i][j + 1].getOwner() == true && board.getBoard()[i][j + 1].getDiceNumber() < board.getBoard()[i][j].getDiceNumber()) {
                    robotAttackFields.add(new Attack(id, board.getBoard()[i][j], board.getBoard()[i][j + 1]));
                    id++;
                }
                if (board.getBoard()[i][j].getOwner() == false && j > 0 && board.getBoard()[i][j - 1].getOwner() == true && board.getBoard()[i][j - 1].getDiceNumber() < board.getBoard()[i][j].getDiceNumber()) {
                    robotAttackFields.add(new Attack(id, board.getBoard()[i][j], board.getBoard()[i][j - 1]));
                    id++;
                }
                if (board.getBoard()[i][j].getOwner() == false && i < board.getSize() - 1 && board.getBoard()[i + 1][j].getOwner() == true && board.getBoard()[i + 1][j].getDiceNumber() < board.getBoard()[i][j].getDiceNumber()) {
                    robotAttackFields.add(new Attack(id, board.getBoard()[i][j], board.getBoard()[i + 1][j]));
                    id++;
                }
                if (board.getBoard()[i][j].getOwner() == false && i > 0 && board.getBoard()[i - 1][j].getOwner() == true && board.getBoard()[i - 1][j].getDiceNumber() < board.getBoard()[i][j].getDiceNumber()) {
                    robotAttackFields.add(new Attack(id, board.getBoard()[i][j], board.getBoard()[i - 1][j]));
                    id++;
                }
            }
        }

        for (int i = 0; i < robotAttackFields.size(); i++) {
            for (int j = 0; j < robotAttackFields.size(); j++) {
                if (robotAttackFields.get(i).getInvaded().getId() == robotAttackFields.get(j).getInvaded().getId()) {
                    robotAttackFields.remove(j);
                }
            }
        }
        return robotAttackFields;
    }
}
