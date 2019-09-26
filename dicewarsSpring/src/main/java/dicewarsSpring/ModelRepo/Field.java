package dicewarsSpring.ModelRepo;

public class Field {

    private int id;
    private int row;
    private int col;
    private Boolean owner;
    private Integer diceNumber;

    public Field() {
    }

    public Field(int row, int col, int id) {
        this.row = row;
        this.col = col;
        this.id = id;
        this.owner = null;
        this.diceNumber = null;
    }

    public Field(int row, int col, int id, boolean owner, Integer dNum) {
        this.row = row;
        this.col = col;
        this.id = id;
        this.owner = owner;
        this.diceNumber = dNum;
    }

    public int getId() {
        return id;
    }

    public Boolean getOwner() {
        return owner;
    }

    public void setOwner(Boolean owner) {
        this.owner = owner;
    }

    public Integer getDiceNumber() {
        return diceNumber;
    }

    public void setDiceNumber(Integer diceNumber) {
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
