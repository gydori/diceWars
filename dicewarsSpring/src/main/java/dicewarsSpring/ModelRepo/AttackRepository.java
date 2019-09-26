package dicewarsSpring.ModelRepo;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AttackRepository {
    private List<Attack> robotAttacks = new ArrayList<>();

    public void addAttack(Attack attack) {
        robotAttacks.add(attack);
    }

    public List<Attack> getAttacks() {
        return robotAttacks;
    }

    public void deleteAttacks() {
        robotAttacks.clear();
    }
}
