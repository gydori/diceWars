package dicewarsSpring.Service;

import dicewarsSpring.ModelRepo.Board;
import dicewarsSpring.ModelRepo.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static dicewarsSpring.ModelRepo.Player.greenFields;
import static dicewarsSpring.ModelRepo.Player.purpleFields;

@Service
public class BoardService {

    @Autowired
    public Board board;

    //lila mezők kisorsolása
    public List<Integer> getPurpleFields() {
        for (int i = 0; i < (int) Math.pow(board.getSize(), 2) / 2; i++) {
            int r = new Random().nextInt((int) Math.pow(board.getSize(), 2));
            if (purpleFields.contains(r)) {
                i--;
            } else {
                purpleFields.add(r);
            }
        }
        return purpleFields;
    }

    //zöld mezők (a maradék)
    public List<Integer> getGreenFields() {
        for (int i = 0; i < Math.pow(board.getSize(), 2); i++) {
            if (!purpleFields.contains(i)) {
                greenFields.add(i);
            }
        }
        return greenFields;
    }

    //mezők Map-be gyűjtése <id, owner>
    public Map<Integer, Boolean> whoOwnsTheFields() {
        List<Integer> purple = getPurpleFields();
        List<Integer> green = getGreenFields();
        Map<Integer, Boolean> fields = new HashMap<>();
        for (int i = 0; i < purple.size(); i++) {
            fields.put(purple.get(i), true);
        }
        for (int i = 0; i < green.size(); i++) {
            fields.put(green.get(i), false);
        }
        return fields;
    }

    //kockák szétosztásához segédfüggvény
    public Map<Integer, Integer> partiateDices(int numberOfDices, List<Integer> myFields) {
        Map<Integer, Integer> diceOfField = new HashMap<>();
        for (int i = 0; i < myFields.size(); i++) {
            diceOfField.put(myFields.get(i), 1);
            numberOfDices--;
        }
        while (numberOfDices > 0) {
            Integer r = new Random().nextInt(myFields.size());
            if (diceOfField.get(myFields.get(r)) < 8) {
                diceOfField.put(myFields.get(r), diceOfField.get(myFields.get(r)) + 1);
                numberOfDices--;
            }
/*            if (diceOfField.get(myFields.get(r)) == 8) {
                myFields.remove(r);
            }*/
        }
        return diceOfField;
    }

    //Kockák Map-be gyűjtése <id, diceNumber>
    public Map<Integer, Integer> getPartiateDices() {
        Map<Integer, Integer> diceOfPurpleField = partiateDices(purpleFields.size() * 3, purpleFields);
        Map<Integer, Integer> diceOfGreenField = partiateDices(greenFields.size() * 3, greenFields);
        Map<Integer, Integer> diceOfField = new HashMap<>();
        diceOfField.putAll(diceOfPurpleField);
        diceOfField.putAll(diceOfGreenField);
        return diceOfField;
    }

    //tábla inicializálása
    public Field[] initializeBoard() {
        int id = 0;
        Map<Integer, Boolean> owner = whoOwnsTheFields();
        Map<Integer, Integer> dice = getPartiateDices();
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                board.getBoard()[i][j] = new Field(i, j, id, owner.get(id), dice.get(id));
                id++;
            }
        }
        Field[] convertedBoard = new Field[(int) Math.pow(board.getSize(), 2)];
        int pointer = 0;
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                convertedBoard[pointer] = board.getBoard()[i][j];
                pointer++;
            }
        }
        return convertedBoard;
    }

    //pálya jelenlegi állásának visszaadása
    public Field[] getBoard() {
        Field[] convertedBoard = new Field[(int) Math.pow(board.getSize(), 2)];
        int pointer = 0;
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                convertedBoard[pointer] = board.getBoard()[i][j];
                pointer++;
            }
        }
        return convertedBoard;
    }

}
