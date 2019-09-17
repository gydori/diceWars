package dicewarsSpring.ModelRepo;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Board implements Player {

    private int size;
    private Field[][] board;

    public Board() {
        this.size = new Random().nextInt(10 - 5) + 5;
        this.board = new Field[size][size];
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
