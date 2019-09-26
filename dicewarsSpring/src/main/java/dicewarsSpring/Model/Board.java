package dicewarsSpring.Model;

import org.springframework.stereotype.Repository;

import java.util.Random;

@Repository
public class Board {

    private int size;
    private Field[][] board;

    public Board() {
        this.size = new Random().nextInt(10 - 5) + 5;
        this.board = new Field[size][size];
    }

    public Field getField(int id) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j].getId() == id) {
                    return board[i][j];
                }
            }
        }
        return null;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Field[][] getBoard() {
        return board;
    }

}
