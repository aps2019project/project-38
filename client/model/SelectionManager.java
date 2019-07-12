package model;

import client.net.Digikala;
import client.net.Encoder;
import client.net.Message;
import view.fxmlControllers.ArenaController;

import java.io.Serializable;
import java.util.ArrayList;

public class SelectionManager implements Serializable {
    private ArrayList<Cell> cells = new ArrayList<>();
    public Integer cardHandIndex;
    public boolean specialPowerIsSelected;
    public String collectibleItem;

    public ArrayList<Cell> getCells() {
        return cells;
    }

    public void selectCell(int row, int col) {
        if (cardHandIndex != null) {
            //                game.useCard(cardHandIndex, cell);
            Encoder.sendPackage(Message.useCard, cardHandIndex, row, col);
            deselectAll();
        } else if (specialPowerIsSelected) {
            //                game.useSpecialPower(cell);
            Encoder.sendPackage(Message.useSpecialPower, row, col);
            deselectAll();
        } else if (collectibleItem != null) {
            //                game.useCollectible(collectibleItem, cell);
            Encoder.sendPackage(Message.useCollectible, collectibleItem, row, col);
            deselectAll();
        } else if (Digikala.getIsThereWarrior(row, col)) {
            if (Digikala.getIsMyWarrior(row, col)) {
                cardHandIndex = null;
                specialPowerIsSelected = false;
                collectibleItem = null;
                if (cells.stream().noneMatch(cell -> cell.row == row && cell.column == col)) {
                    cells.add(new Cell(row, col));
                }
            } else {
                if (cells.size() == 1) {
                    synchronized (Encoder.class){
                        Encoder.sendMessage(Message.attack);
                        Encoder.sendObjectJ(cells.get(0));
                        Encoder.sendObject(row);
                        Encoder.sendObject(col);
                    }
//                        game.attack(cells.get(0), row, col);
                    deselectAll();
                } else if (cells.size() > 1) {
                    synchronized (Encoder.class){
                        Encoder.sendMessage(Message.comboAttack);
                        Encoder.sendObjectJ(cells);
                        Encoder.sendObject(row);
                        Encoder.sendObject(col);
                    }
//                        game.comboAttack(cells, row, col);
                    deselectAll();
                }
            }
        } else {
            if (cells.size() == 1) {
                synchronized (Encoder.class){
                    Encoder.sendMessage(Message.move);
                    Encoder.sendObjectJ(cells.get(0));
                    Encoder.sendObject(row);
                    Encoder.sendObject(col);
                }
//                    game.move(cells.get(0), row, col);
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
