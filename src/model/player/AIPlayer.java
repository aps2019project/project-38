package model.player;


import model.*;
import model.cards.Card;
import model.cards.Warrior;
import view.images.LoadedImages;
import model.exceptions.NotEnoughConditions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Random;

public class AIPlayer extends Player {
    public AIPlayer(Deck deck) {
        super(deck);
        avatar = LoadedImages.avatars[new Random().nextInt(15)];
        username = "AI_Player";
    }

    public void doSomething() {
//        ArrayList<Map.Entry<Integer, Card>> es = new ArrayList<>(hand.entrySet());
//        Collections.shuffle(es);
//        for (Map.Entry<Integer, Card> cardEntry : es) {
//            if (cardEntry.getValue() != null) {
//                for (Cell cell : getBoardCells()) {
//                    try {
//                        getGame().useCard(cardEntry.getKey(), cell);
//                    } catch (NotEnoughConditions notEnoughConditions) {
//                        no problem he's stupid
                    }
//                }
//            }
//        }
//
//        Collections.shuffle(warriors);
//        ArrayList<Warrior> tempWarrior = new ArrayList<>(warriors);
//        for (Warrior warrior : tempWarrior) {
//            for (Cell cell : getBoardCells()) {
//                try {
//                    getGame().attack(warrior.getCell(), cell);
//                    getGame().move(warrior.getCell(), cell);
//                } catch (NotEnoughConditions notEnoughConditions) {
//                    no problem he's stupid
//                }
//            }
//        }
//
//        getGame().endTurn();
//    }

    private ArrayList<Cell> getBoardCells() {
        ArrayList<Cell> cells = new ArrayList<>();
        for (int i = 0; i < Constant.GameConstants.boardRow; i++) {
            for (int j = 0; j < Constant.GameConstants.boardColumn; j++) {
                cells.add(getGame().getBoard().getCell(i, j));
            }
        }
        Collections.shuffle(cells);
        return cells;
    }
}