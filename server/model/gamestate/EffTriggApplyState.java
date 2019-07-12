package model.gamestate;

import model.QualityHaver;

public class EffTriggApplyState extends GameState {
    private QualityHaver target;
    private Object effOrTrigg;
    public boolean pending = true;
    public boolean canceled = false;

    public EffTriggApplyState(QualityHaver target, Object effOrTrigg) {
        this.target = target;
        this.effOrTrigg = effOrTrigg;
    }

    public QualityHaver getTarget() {
        return target;
    }

    public Object getEffOrTrigg() {
        return effOrTrigg;
    }
}
