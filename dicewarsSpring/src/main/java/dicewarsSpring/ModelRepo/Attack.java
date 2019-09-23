package dicewarsSpring.ModelRepo;

public class Attack {
    private int id;
    private Field invader;
    private Field invaded;

    public Attack(int id, Field invader, Field invaded) {
        this.id = id;
        this.invader = invader;
        this.invaded = invaded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
