package dicewarsSpring.Service;

import dicewarsSpring.Model.Board;
import dicewarsSpring.Model.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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
        }
        if (myPoints <= enemyPoints) {
            me.setDiceNumber(1);
        }
        return points;
    }

    public void endOfTurn(Boolean owner) {
        List<Field> myFields = boardService.getOwnFields(owner);

        int numberOfDices = myFields.size() / 2;

        while (numberOfDices > 0) {
            myFields = myFields.stream().filter(f -> f.getDiceNumber() < 8).collect(Collectors.toList());
            if (myFields.size() < 1) {
                break;
            } else {
                Integer r = new Random().nextInt(myFields.size());
                myFields.get(r).setDiceNumber(myFields.get(r).getDiceNumber() + 1);
                numberOfDices--;
            }
        }
    }

    public boolean winCheck() {
        List<Field> enemyFields = boardService.getOwnFields(false);
        if (enemyFields.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean looseCheck() {
        List<Field> myFields = boardService.getOwnFields(true);
        if (myFields.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

}
