package dicewarsSpring.Service;

import dicewarsSpring.Model.Board;
import dicewarsSpring.Model.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class BoardService {

    @Autowired
    public Board board;

    @Autowired
    DBService dbService;

    //méret kisorsolása
    public void getSize() {
        int size = new Random().nextInt(6) + 5;
        board.setSize(size);
    }

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
        }
    }

    //tábla inicializálása
    public Field[] initializeBoard() {
        dbService.clearDB();
        getSize();
        board.setBoard(new Field[board.getSize()][board.getSize()]);
        int id = 0;
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                board.getBoard()[i][j] = new Field(i, j, id);
                id++;
            }
        }
        partiateFields(); //mezők kiosztása
        partiateDices(true); //lila mezőkre kockák kiosztása
        partiateDices(false);  //zöld mezőkre kockák kiosztása
        dbService.saveBoard();
        Field[] convertedBoard = getBoard();
        return convertedBoard;
    }

    //pálya jelenlegi állásának visszaadása
    public Field[] getBoard() {
/*        List<Field> listBoard = dbService.findAllField();
        Field[] convertedBoard = new Field[listBoard.size()];
        convertedBoard = listBoard.toArray(convertedBoard);*/
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

    public boolean winCheck() {
        List<Field> enemyFields = getOwnFields(false);
        if (enemyFields.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean looseCheck() {
        List<Field> myFields = getOwnFields(true);
        if (myFields.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public Field[] convertResumeBoard() {
        //listből arraybe, hogy küldhető legyen a frontendhez
        List<Field> listBoard = dbService.findAllField();
        Field[] arrayBoard = new Field[listBoard.size()];
        arrayBoard = listBoard.toArray(arrayBoard);

        //backendbeli board-ba beletölteni
        int size = (int) Math.sqrt(arrayBoard.length);
        int k = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board.getBoard()[i][j] = arrayBoard[k];
                k++;
            }
        }
        return arrayBoard;
    }

}
