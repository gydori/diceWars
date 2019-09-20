package dicewarsSpring.Service;

import dicewarsSpring.ModelRepo.Board;
import dicewarsSpring.ModelRepo.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class GameService {

    @Autowired
    public Board board;

    @Autowired
    public BoardService boardService;

    public int diceThrowing(Field field) {
        int sum = 0;
        for (int i = 0; i < field.getDiceNumber(); i++) {
            sum += new Random().nextInt(7 - 1) + 1;
        }
        return sum;
    }

    public int[] attack(Field me, Field enemy) {
        int myPoints = diceThrowing(me);
        int enemyPoints = diceThrowing(enemy);
        int[] points = {myPoints, enemyPoints};

        if (myPoints > enemyPoints) {
            enemy.setOwner(!enemy.getOwner());
            enemy.setDiceNumber(me.getDiceNumber() - 1);
            me.setDiceNumber(1);
/*            if (me.getOwner()) {
                purpleFields.add(enemy.getId());
                greenFields.remove(greenFields.indexOf(enemy.getId()));
            } else {
                greenFields.add(enemy.getId());
                purpleFields.remove(purpleFields.indexOf(enemy.getId()));
            }*/
        }
        if (myPoints <= enemyPoints) {
            me.setDiceNumber(1);
        }
        return points;
    }

    public void endOfTurn(Boolean owner) {
        /*for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (myFields.contains(board.getBoard()[i][j].getId()) && board.getBoard()[i][j].getDiceNumber() == 8) {
                    myFields.remove(myFields.indexOf(board.getBoard()[i][j].getId()));
                }
            }
        }*/
        List<Field> myFields = boardService.getOwnFields(owner);
        int numberOfDices = myFields.size() / 2;

        while (numberOfDices > 0) {
            Integer r = new Random().nextInt(myFields.size());
            //int id = myFields.get(r);
            //for (int i = 0; i < board.getSize(); i++) {
            //for (int j = 0; j < board.getSize(); j++) {
            if (myFields.get(r).getDiceNumber() < 8) {
                myFields.get(r).setDiceNumber(myFields.get(r).getDiceNumber() + 1);
/*                        if (board.getBoard()[i][j].getDiceNumber() == 8) {
                            myFields.remove(myFields.indexOf(board.getBoard()[i][j].getId()));
                        }*/
                numberOfDices--;
            }
            //}
            // }
        }
    }

}
