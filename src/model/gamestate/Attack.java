package model.gamestate;

import model.cards.warriors.Warrior;

public class Attack extends GameState {
    private boolean pending;
    private int ap;
    private Warrior attacker;
    private Warrior attecked;

    public boolean isPending() {
        return pending;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }

    public int getAp() {
        return ap;
    }

    public void setAp(int ap) {
        this.ap = ap;
    }

    public Warrior getAttacker() {
        return attacker;
    }

    public Warrior getAttecked() {
        return attecked;
    }

    public Attack(Warrior attacker, Warrior attacked, int ap) {
        this.ap = ap;
        this.attacker = attacker;
        this.attecked = attacked;
    }
}
