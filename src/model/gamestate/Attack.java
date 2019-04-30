package model.gamestate;

import model.cards.warriors.Warrior;

public class Attack extends GameState {
    private boolean pending;
    public int ap;
    private Warrior attacker;
    private Warrior attacked;
    public boolean canceled=false;

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

    public Attack(Warrior attacker, Warrior attacked, int ap) {
        this.ap = ap;
        this.attacker = attacker;
        this.attacked = attacked;
    }
}
