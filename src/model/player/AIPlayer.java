package model.player;


import model.*;
import model.cards.Card;
import model.cards.Warrior;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class AIPlayer extends Player {
    public AIPlayer(Deck deck) {
        super(deck);
    }

    public void doSomething() {
        Collections.shuffle(warriors);
        for (Warrior warrior : warriors) {
            for (Cell cell : getBoardCells()) {
                getGame().move(warrior.getCell(),cell);
            }
        }

        ArrayList<Map.Entry<Integer,Card>> es= new ArrayList<>(hand.entrySet());
        Collections.shuffle(es);
        for (Map.Entry<Integer, Card> cardEntry : es) {
            if(cardEntry.getValue()!=null){
                for (Cell cell : getBoardCells()) {
                    getGame().useCard(cardEntry.getKey(),cell);
                }
            }
        }

        getGame().endTurn();
    }

    private ArrayList<Cell> getBoardCells(){
        ArrayList<Cell> cells = new ArrayList<>();
        for (int i = 0; i < Constant.GameConstants.boardRow; i++) {
            for (int j = 0; j < Constant.GameConstants.boardColumn; j++) {
                cells.add(getGame().getBoard().getCell(i,j));
            }
        }
        Collections.shuffle(cells);
        return cells;
    }

    private Game getGame(){
        return warriors.get(0).getCell().getBoard().getGame();
    }
}
