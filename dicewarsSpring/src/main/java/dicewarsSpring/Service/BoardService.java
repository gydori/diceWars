package dicewarsSpring.Service;

import dicewarsSpring.Model.Board;
import dicewarsSpring.Model.Field;
import dicewarsSpring.Repository.FieldRepository;
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
    FieldRepository fieldRepo;

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

    public void saveBoard() {
        for (int i = 0; i < Math.pow(board.getSize(), 2); i++) {
            fieldRepo.save(board.getField(i));
        }
    }

    public void clearDB() {
        fieldRepo.deleteAll();
    }

    public List<Field> findAllField() {
        return fieldRepo.findAll();
    }

    public Field[] convertResumeBoard() {
        //listből arraybe, hogy küldhető legyen a frontendhez
        List<Field> listBoard = findAllField();
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
