package model;

import model.cards.Warrior;
import model.effects.Effect;
import model.triggers.Trigger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;

public abstract class QualityHaver implements Serializable {
    transient protected ArrayList<Effect> effects = new ArrayList<>();
    transient protected ArrayList<Trigger> triggers = new ArrayList<>();

    public ArrayList<Effect> getEffects() {
        return effects;
    }

    public ArrayList<Trigger> getTriggers() {
        return triggers;
    }

    public static Game getGameFromQualityHaver(QualityHaver qualityHaver) {
        assert ((qualityHaver instanceof Warrior) | (qualityHaver instanceof Cell));

        if (qualityHaver instanceof Warrior) {
            return ((Warrior) qualityHaver).getCell().getBoard().getGame();
        } else {
            return ((Cell) qualityHaver).getBoard().getGame();
        }
    }


    private boolean lock = false;
    private String key = null;
    private ArrayList<Effect> effectsBuffer = new ArrayList<>();
    private ArrayList<Trigger> triggerBuffer = new ArrayList<>();
    private ArrayList<Effect> effectsRMBuffer = new ArrayList<>();
    private ArrayList<Trigger> triggerRMBuffer = new ArrayList<>();
    public Effect addEffect(Effect effect) {
        Effect clone = effect.deepCopy();
        if (lock) {
            effectsBuffer.add(clone);
        } else {
            effects.add(clone);
        }
        return clone;
    }
    public Trigger addTrigger(Trigger trigger) {
        Trigger clone = trigger.deepCopy();
        if (lock) {
            triggerBuffer.add(clone);
        } else {
            triggers.add(clone);
        }
        return clone;
    }
    public ArrayList<Effect> addEffect(ArrayList<Effect> effect) {
        ArrayList<Effect> clone = effect.stream().map(Effect::deepCopy).collect(Collectors.toCollection(ArrayList::new));
        if (lock) {
            effectsBuffer.addAll(clone);
        } else {
            effects.addAll(clone);
        }
        return clone;
    }
    public ArrayList<Trigger> addTrigger(ArrayList<Trigger> trigger) {
        ArrayList<Trigger> clone = trigger.stream().map(Trigger::deepCopy).collect(Collectors.toCollection(ArrayList::new));
        if (lock) {
            triggerBuffer.addAll(clone);
        } else {
            triggers.addAll(clone);
        }
        return clone;
    }
    public void removeEffect(Effect effect) {
        if (lock) {
            effectsRMBuffer.add(effect);
            effectsBuffer.remove(effect);
        } else {
            effects.remove(effect);
        }
    }
    public void removeEffect(ArrayList<Effect> effect) {
        if (lock) {
            effectsRMBuffer.addAll(effect);
            effectsBuffer.removeAll(effect);
        } else {
            effects.removeAll(effect);
        }
    }
    public void removeTrigger(Trigger trigger) {
        if (lock) {
            triggerRMBuffer.add(trigger);
            triggerBuffer.remove(trigger);
        } else {
            triggers.remove(trigger);
        }
    }
    public void removeTrigger(ArrayList<Trigger> trigger) {
        if (lock) {
            triggerRMBuffer.addAll(trigger);
            triggerBuffer.removeAll(trigger);
        } else {
            triggers.removeAll(trigger);
        }
    }
    public void setLock(String key,boolean lock) {
        if (!this.lock && lock) {
            this.lock = true;
            this.key = key;
        } else if (this.lock && !lock && this.key.equals(key)) {
            this.lock = false;

            effects.addAll(effectsBuffer);
            triggers.addAll(triggerBuffer);
            effects.removeAll(effectsRMBuffer);
            triggers.removeAll(triggerRMBuffer);

            effectsBuffer.clear();
            triggerBuffer.clear();
            effectsRMBuffer.clear();
            triggerRMBuffer.clear();
        }
    }

    public ArrayList<Effect> getEffectsBuffer() {
        return effectsBuffer;
    }
}