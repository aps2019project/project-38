package model.gamestate;

import model.cards.Warrior;

public class AttackState extends GameState {
    public boolean pending = true;
    public int ap;
    private Warrior attacker;
    private Warrior attacked;
    public boolean canceled = false;

    public Warrior getAttacker() {
        return attacker;
    }

    public void setAttacker(Warrior attacker) {
        this.attacker = attacker;
    }

    public Warrior getAttacked() {
        return attacked;
    }

    public AttackState(Warrior attacker, Warrior attacked, int ap) {
        this.ap = ap;
        this.attacker = attacker;
        this.attacked = attacked;
    }
}
