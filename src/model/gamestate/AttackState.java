package model.gamestate;

import model.cards.warriors.Warrior;

public class AttackState extends GameState {
    private boolean pending;
    public int ap;
    private Warrior attacker;
    private Warrior attacked;
    public boolean canceled = false;

    public boolean isPending() {
        return pending;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }

    public Warrior getAttacker() {
        return attacker;
    }

    public Warrior getAttacked() {
        return attacked;
    }

    public AttackState(Warrior attacker, Warrior attacked, int ap, boolean pending) {
        this.ap = ap;
        this.attacker = attacker;
        this.attacked = attacked;
        this.pending = pending;
    }
}
