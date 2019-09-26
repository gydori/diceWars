package dicewarsSpring.Model;

import javax.persistence.*;

@Entity
@Table
public class Field {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long SqlId;

    @Column
    private int id;

    @Column
    private int row;

    @Column
    private int col;

    @Column
    private Boolean owner;

    @Column
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
