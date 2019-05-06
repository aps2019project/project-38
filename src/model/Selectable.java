package model;

import model.cards.Spell;

import java.util.ArrayList;

public class Selectable {
    private ArrayList<Cell> warriorsCell = new ArrayList<>();
    public Integer cardHandIndex;
    public boolean specialPowerIsSelected;
    public Spell collectableItem;

    public ArrayList<Cell> getWarriorsCell() {
        return warriorsCell;
    }

    public void seletWarrior(Cell cell) {
        Game game = cell.getBoard().getGame();
        if (game.getActivePlayer().getWarriors().contains(cell.getWarrior())) {
            cardHandIndex = null;
            specialPowerIsSelected = false;
            collectableItem = null;
            warriorsCell.add(cell);
        }
        else {
            //todo
        }
    }

    public void selectCard(Game game, int cardHandIndex) {
        if (game.getActivePlayer().getHand().get(cardHandIndex) != null) {
            warriorsCell = new ArrayList<>();
            specialPowerIsSelected = false;
            collectableItem = null;
            this.cardHandIndex = cardHandIndex;
        }
        else {
            //todo
        }
    }

    public void selectSpecialPower(Game game) {
        warriorsCell = new ArrayList<>();
        cardHandIndex = null;
        collectableItem = null;
        specialPowerIsSelected = true;
    }

    public void selectColletableItem(Spell collectableItem) {
        this.collectableItem = collectableItem;
    }

    public void deselectAll() {
        warriorsCell = new ArrayList<>();
        cardHandIndex = null;
        specialPowerIsSelected =  false;
        collectableItem = null;
    }
}