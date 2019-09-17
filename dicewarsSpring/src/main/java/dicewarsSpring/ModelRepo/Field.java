package dicewarsSpring.ModelRepo;

public class Field {

    private final int id;
    private int row;
    private int col;
    private boolean owner;
    private int diceNumber;

    public Field(int row, int col, int id, boolean owner, int diceNumber) {
        this.row = row;
        this.col = col;
        this.id = id;
        this.owner = owner;
        this.diceNumber = diceNumber;
    }

    public int getId() {
        return id;
    }

    public boolean getOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public int getDiceNumber() {
        return diceNumber;
    }

    public void setDiceNumber(int diceNumber) {
        this.diceNumber = diceNumber;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
