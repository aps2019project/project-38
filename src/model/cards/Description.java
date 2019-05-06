package model.cards;

import java.io.Serializable;

public class Description implements Serializable {
    public String descriptionOfCardSpecialAbility;
    public String targetType;

    public String getDescriptionOfCardSpecialAbility() {
        return descriptionOfCardSpecialAbility;
    }

    public String getTargetType() {
        return targetType;
    }
}
