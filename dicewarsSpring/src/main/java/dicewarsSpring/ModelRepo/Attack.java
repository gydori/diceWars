package dicewarsSpring.ModelRepo;

public class Attack {
    private Field invader;
    private Field invaded;
    private int invaderPoints;
    private int invadedPoints;
    private Field[] board;

    public Attack(Field invader, Field invaded) {
        this.invader = invader;
        this.invaded = invaded;
    }

    public Field getInvader() {
        return invader;
    }

    public void setInvader(Field invader) {
        this.invader = invader;
    }

    public Field getInvaded() {
        return invaded;
    }

    public void setInvaded(Field invaded) {
        this.invaded = invaded;
    }

    public int getInvaderPoints() {
        return invaderPoints;
    }

    public void setInvaderPoints(int invaderPoints) {
        this.invaderPoints = invaderPoints;
    }

    public int getInvadedPoints() {
        return invadedPoints;
    }

    public void setInvadedPoints(int invadedPoints) {
        this.invadedPoints = invadedPoints;
    }

    public Field[] getBoard() {
        return board;
    }

    public void setBoard(Field[] board) {
        this.board = new Field[board.length];
        for (int i = 0; i < board.length; i++) {
            this.board[i] = new Field(board[i].getRow(), board[i].getCol(), board[i].getId(), board[i].getOwner(), board[i].getDiceNumber());
        }
    }

}
