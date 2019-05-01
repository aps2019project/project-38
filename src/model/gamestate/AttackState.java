package model.gamestate;

import model.cards.warriors.Warrior;

public class AttackState extends GameState {
    public boolean pending = true;
    public int ap;
    private Warrior attacker;
    private Warrior attacked;
    public boolean canceled = false;

    public Warrior getAttacker() {
        return attacker;
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
