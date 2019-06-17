package model;

import model.cards.Spell;
import model.exceptions.NotEnoughConditions;
import view.fxmlControllers.ArenaController;

import java.io.Serializable;
import java.util.ArrayList;

public class SelectionManager implements Serializable {
    private ArrayList<Cell> warriorsCells = new ArrayList<>();
    public Integer cardHandIndex;
    public boolean specialPowerIsSelected;
    public Spell collectibleItem;
    Game game;

    public SelectionManager(Game game) {
        this.game = game;
    }

    public ArrayList<Cell> getWarriorsCells() {
        return warriorsCells;
    }

    public void selectCell(Cell cell) {
        if (warriorsCells.size() == 0) {
            if (cardHandIndex != null) {
                try {
                    game.useCard(cardHandIndex, cell);
                } catch (NotEnoughConditions notEnoughConditions) {
                    ArenaController.showMessege(notEnoughConditions.getMessage());
                }
            } else if (specialPowerIsSelected) {
                try {
                    game.useSpecialPower(cell);
                } catch (NotEnoughConditions notEnoughConditions) {
                    ArenaController.showMessege(notEnoughConditions.getMessage());
                }
            } else if (collectibleItem != null) {
                try {
                    game.useCollectible(collectibleItem, cell);
                } catch (NotEnoughConditions notEnoughConditions) {
                    ArenaController.showMessege(notEnoughConditions.getMessage());
                }
            }
            deselectAll();
        }

        if (cell.getWarrior() != null) {
            if (game.getActivePlayer().getWarriors().contains(cell.getWarrior())) {
                cardHandIndex = null;
                specialPowerIsSelected = false;
                collectibleItem = null;
                warriorsCells.add(cell);
            } else {
                if (warriorsCells.size() == 1) {
                    try {
                        game.attack(warriorsCells.get(0), cell);
                    } catch (NotEnoughConditions notEnoughConditions) {
                        ArenaController.showMessege(notEnoughConditions.getMessage());
                    }
                } else if (warriorsCells.size() > 1) {
                    try {
                        game.comboAttack(warriorsCells, cell);
                    } catch (NotEnoughConditions notEnoughConditions) {
                        ArenaController.showMessege(notEnoughConditions.getMessage());
                    }
                }
                deselectAll();
            }
        } else {
            if (warriorsCells.size() == 1) {
                try {
                    game.move(warriorsCells.get(0), cell);
                } catch (NotEnoughConditions notEnoughConditions) {
                    ArenaController.showMessege(notEnoughConditions.getMessage());
                }
            }
            deselectAll();
        }
    }

    public void selectCard(int cardHandIndex) {
        warriorsCells = new ArrayList<>();
        specialPowerIsSelected = false;
        collectibleItem = null;
        this.cardHandIndex = cardHandIndex;
    }

    public void selectSpecialPower() {
        deselectAll();
        specialPowerIsSelected = true;
    }

    public void selectCollectibleItem(Spell collectableItem) {
        this.collectibleItem = collectableItem;
    }

    public void deselectAll() {
        warriorsCells = new ArrayList<>();
        cardHandIndex = null;
        specialPowerIsSelected = false;
        collectibleItem = null;
    }
}
