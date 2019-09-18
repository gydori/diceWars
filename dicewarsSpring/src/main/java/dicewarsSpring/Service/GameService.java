package dicewarsSpring.Service;

import dicewarsSpring.ModelRepo.Field;
import org.springframework.stereotype.Service;

import java.util.Random;

import static dicewarsSpring.ModelRepo.Player.greenFields;
import static dicewarsSpring.ModelRepo.Player.purpleFields;

@Service
public class GameService {

    public int diceThrowing(Field field) {
        int sum = 0;
        for (int i = 0; i < field.getDiceNumber(); i++) {
            sum += new Random().nextInt(7 - 1) + 1;
        }
        return sum;
    }

    public void attack(Field me, Field enemy) {
        int myPoints = diceThrowing(me);
        int enemyPoints = diceThrowing(enemy);

        if (myPoints > enemyPoints) {
            enemy.setOwner(!enemy.getOwner());
            enemy.setDiceNumber(me.getDiceNumber() - 1);
            me.setDiceNumber(1);
            if (me.getOwner()) {
                purpleFields.add(enemy.getId());
                greenFields.remove(greenFields.indexOf(enemy.getId()));
            } else {
                greenFields.add(enemy.getId());
                purpleFields.remove(purpleFields.indexOf(enemy.getId()));
            }
        }
        if (myPoints <= enemyPoints) {
            me.setDiceNumber(1);
        }
    }


}
