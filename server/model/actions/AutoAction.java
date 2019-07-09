package model.actions;

import model.QualityHaver;

import java.io.Serializable;

public interface AutoAction extends Serializable {
    boolean execute(QualityHaver source, QualityHaver target);  // todo MOEINI
}
