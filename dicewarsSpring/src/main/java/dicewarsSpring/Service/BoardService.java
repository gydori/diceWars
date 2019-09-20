package dicewarsSpring.Service;

import dicewarsSpring.ModelRepo.Board;
import dicewarsSpring.ModelRepo.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class BoardService {

    @Autowired
    public Board board;

    //mezők kisorsolása
    public void partiateFields() {
        //purple fields - true
        for (int i = 0; i < (int) Math.pow(board.getSize(), 2) / 2; i++) {
            int r = new Random().nextInt((int) Math.pow(board.getSize(), 2));
            if (board.getField(r).getOwner() != null) {
                i--;
            } else {
                board.getField(r).setOwner(true);
            }
        }
        //green fields - false
        for (int i = 0; i < Math.pow(board.getSize(), 2); i++) {
            if (board.getField(i).getOwner() == null) {
                board.getField(i).setOwner(false);
            }
        }
    }

    //zöld mezők (a maradék)
    /*public List<Integer> getGreenFields() {
        for (int i = 0; i < Math.pow(board.getSize(), 2); i++) {
            if (!purpleFields.contains(i)) {
                greenFields.add(i);
            }
        }
        return greenFields;
    }*/

    //mezők Map-be gyűjtése <id, owner>
    /*public Map<Integer, Boolean> whoOwnsTheFields() {
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
    }*/

    public List<Field> getOwnFields(Boolean owner) {
        List<Field> myFields = new ArrayList<>();
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (board.getBoard()[i][j].getOwner() == owner) {
                    myFields.add(board.getBoard()[i][j]);
                }
            }
        }
        return myFields;
    }

    //kockák szétosztása
    public void partiateDices(Boolean owner) {
        List<Field> myFields = getOwnFields(owner);
        int numberOfDices = myFields.size() * 3;
        //Map<Integer, Integer> diceOfField = new HashMap<>();
        for (int i = 0; i < myFields.size(); i++) {
            myFields.get(i).setDiceNumber(1);
            numberOfDices--;
        }
        while (numberOfDices > 0) {
            Integer r = new Random().nextInt(myFields.size());
            if (myFields.get(r).getDiceNumber() < 8) {
                myFields.get(r).setDiceNumber(myFields.get(r).getDiceNumber() + 1);
                numberOfDices--;
            }
/*            if (diceOfField.get(myFields.get(r)) == 8) {
                myFields.remove(r);
            }*/
        }
    }

    //Kockák Map-be gyűjtése <id, diceNumber>
/*    public Map<Integer, Integer> getPartiateDices() {
        Map<Integer, Integer> diceOfPurpleField = partiateDices(purpleFields.size() * 3, purpleFields);
        Map<Integer, Integer> diceOfGreenField = partiateDices(greenFields.size() * 3, greenFields);
        Map<Integer, Integer> diceOfField = new HashMap<>();
        diceOfField.putAll(diceOfPurpleField);
        diceOfField.putAll(diceOfGreenField);
        return diceOfField;
    }*/

    //tábla inicializálása
    public Field[] initializeBoard() {
        int id = 0;
        //Map<Integer, Boolean> owner = whoOwnsTheFields();
        //Map<Integer, Integer> dice = getPartiateDices();
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                board.getBoard()[i][j] = new Field(i, j, id);
                id++;
            }
        }
        partiateFields(); //mezők kiosztása
        partiateDices(true); //lila mezőkre kockák kiosztása
        partiateDices(false);  //zöld mezőkre kockák kiosztása
        Field[] convertedBoard = getBoard();
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
