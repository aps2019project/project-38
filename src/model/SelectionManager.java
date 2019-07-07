package model;

import model.cards.Spell;
import model.exceptions.NotEnoughConditions;
import view.Utility;
import view.fxmlControllers.ArenaController;

import java.io.Serializable;
import java.util.ArrayList;

public class SelectionManager implements Serializable {
    private ArrayList<Cell> cells = new ArrayList<>();
    public Integer cardHandIndex;
    public boolean specialPowerIsSelected;
    public String collectibleItem;
    Game game;

    public SelectionManager(Game game) {
        this.game = game;
    }

    public ArrayList<Cell> getCells() {
        return cells;
    }

    public void selectCell(Cell cell) {

        if (cardHandIndex != null) {
            try {
                game.useCard(cardHandIndex, cell);
            } catch (NotEnoughConditions notEnoughConditions) {
                Utility.showMessage(notEnoughConditions.getMessage());
            }
            deselectAll();
        } else if (specialPowerIsSelected) {
            try {
                game.useSpecialPower(cell);
            } catch (NotEnoughConditions notEnoughConditions) {
                Utility.showMessage(notEnoughConditions.getMessage());
            }
            deselectAll();
        } else if (collectibleItem != null) {
            try {
                game.useCollectible(collectibleItem, cell);
            } catch (NotEnoughConditions notEnoughConditions) {
                Utility.showMessage(notEnoughConditions.getMessage());
            }
            deselectAll();
        } else if (cell.getWarrior() != null) {
            if (game.getActivePlayer().getWarriors().contains(cell.getWarrior())) {
                cardHandIndex = null;
                specialPowerIsSelected = false;
                collectibleItem = null;
                if(cells.stream().noneMatch(cell1 -> cell1.equals(cell))) {
                    cells.add(cell);
                }
            } else {
                if (cells.size() == 1) {
                    try {
                        game.attack(cells.get(0), cell);
                    } catch (NotEnoughConditions notEnoughConditions) {
                        Utility.showMessage(notEnoughConditions.getMessage());
                    }
                    deselectAll();
                } else if (cells.size() > 1) {
                    try {
                        game.comboAttack(cells, cell);
                    } catch (NotEnoughConditions notEnoughConditions) {
                        Utility.showMessage(notEnoughConditions.getMessage());
                    }
                    deselectAll();
                }
            }
        } else {
            if (cells.size() == 1) {
                try {
                    game.move(cells.get(0), cell);
                } catch (NotEnoughConditions notEnoughConditions) {
                    Utility.showMessage(notEnoughConditions.getMessage());
                }
                deselectAll();
            }
        }

        ArenaController.ac.setSelectionEffect(this);
    }

    public void selectCard(int cardHandIndex) {
        cells = new ArrayList<>();
        specialPowerIsSelected = false;
        collectibleItem = null;
        this.cardHandIndex = cardHandIndex;

        ArenaController.ac.setSelectionEffect(this);
    }

    public void selectSpecialPower() {
        deselectAll();
        specialPowerIsSelected = true;

        ArenaController.ac.setSelectionEffect(this);
    }

    public void selectCollectibleItem(String collectableItem) {

        deselectAll();
        this.collectibleItem = collectableItem;

        ArenaController.ac.setSelectionEffect(this);
    }

    public void deselectAction() {
        deselectAll();

        ArenaController.ac.setSelectionEffect(this);
    }

    private void deselectAll() {
        cells = new ArrayList<>();
        cardHandIndex = null;
        specialPowerIsSelected = false;
        collectibleItem = null;
    }
}
