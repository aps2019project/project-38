package model;


import model.cards.Card;
import model.cards.warriors.Warrior;
import model.gamestate.GameState;
import model.gamestate.ReplaceCard;
import model.gamestate.TurnEnd;
import model.player.Player;
import model.triggers.Trigger;

import java.util.ArrayList;


public class Game {
    private int turn;
    private long turnEndTime;
    private Player[] players = new  Player[2];
    private ArrayList<ArrayList<Cell>> board = new ArrayList<>();

    {
        turn = 0;
        fillBoard(Constant.GameConstants.boardRow, Constant.GameConstants.boardColumn);
    }

    private void fillBoard(int row, int column) {
        for (int i = 0; i < row; i++) {
            ArrayList<Cell> newRow = new ArrayList<>();
            for (int j = 0; j < column; j++) {
                newRow.add(new Cell(i, j));
            }
            board.add(newRow);
        }
    }

    public Game() {
        //todo
    }

    public int getManhatanDistance(Cell originCell, Cell destinationCell) {
        ArrayList<Cell> checkedCells = new ArrayList<>();
        ArrayList<Cell> layerCells = new ArrayList<>();
        layerCells.add(originCell);
        for (int i = 0; true; i++) {
            if (layerCells.contains(destinationCell)) {
                return i;
            }
            else if (layerCells.size() == 0) {
                return -1;
            }
            ArrayList nextLayerCells = new ArrayList();
            for (Cell cell : layerCells) {
                checkedCells.add(cell);
                updateListOneByListTwo(nextLayerCells, getAvailableNextLayerCellsFromCell(cell, checkedCells));
            }
            layerCells = nextLayerCells;
        }
    }

    private ArrayList<Cell> getAvailableNextLayerCellsFromCell(Cell cell, ArrayList<Cell> checkedCells) {
        ArrayList<Cell> availableNexLayerCellsFromCell = new ArrayList<>();
        for (Cell nextCell : getNextCells(cell)) {
            if (availableCell(nextCell) && !checkedCells.contains(nextCell)) {
                availableNexLayerCellsFromCell.add(cell);
            }
        }
        return availableNexLayerCellsFromCell;
    }

    private void updateListOneByListTwo (ArrayList<Cell> listOne, ArrayList<Cell> listTwo) {
        for (Cell cell : listTwo) {
            listOne.add(cell);
        }
    }

    private ArrayList<Cell> getNextCells (Cell cell) {
        ArrayList<Cell> nextCells = new ArrayList<>();
        if (cell.getRow() + 1 < Constant.GameConstants.boardRow) {
            nextCells.add(board.get(cell.getRow() + 1).get(cell.getColumn()));
        }
        if (cell.getColumn() + 1 < Constant.GameConstants.boardColumn) {
            nextCells.add(board.get(cell.getRow()).get(cell.getColumn() + 1));
        }
        if (cell.getRow() - 1 >= 0) {
            nextCells.add(board.get(cell.getRow() - 1).get(cell.getColumn()));
        }
        if (cell.getColumn() - 1 >= 0) {
            nextCells.add(board.get(cell.getRow()).get(cell.getColumn() - 1));
        }
        return nextCells;
    }

    private boolean availableCell (Cell cell) {
        if (cell.getWarrior() == null ||
                getActivePlayer().getWarriors().contains(cell.getWarrior())) {
            return true;
        }
        return false;
    }

    public Player getActivePlayer() {
        return players[turn % 2];
    }

    public void iterateAllTriggers (GameState gameState) {
        for (ArrayList<Cell> row : board) {
            for (Cell cell : row) {
                for (Trigger trigger : cell.getTriggers()) {
                    trigger.check(gameState);
                }
            }
        }
        iteratePlayerTriggers(players[0], gameState);
        iteratePlayerTriggers(players[1], gameState);
    }

    private void iteratePlayerTriggers (Player player, GameState gameState) {
        for (Warrior warrior : player.getWarriors()) {
            for (Trigger trigger : warrior.triggers) {
                trigger.check(gameState);
            }
        }
    }

    public void setTurnEndTime() {
        turnEndTime = Constant.GameConstants.gameDateObject.getTime() + Constant.GameConstants.turnTime;
    }

    public void checkTurnTime() {
        if (Constant.GameConstants.gameDateObject.getTime() > turnEndTime) {
            endTurn();
        }
    }

    public void move (Cell sourceCell, Cell targetCell) {
        if (getActivePlayer().getWarriors().contains(sourceCell.getWarrior()) &&
                targetCell.getWarrior() == null) {
            
        }
    }

    public void attack (Cell attackerCell, Cell defenderCell) {
        //todo
    }

    public void replaceCard (Card card) {
        ReplaceCard replaceCard = new ReplaceCard();

    }

    public void useCard (Card card) {
        //todo
    }

    public void endTurn () {
        TurnEnd turnEnd = new TurnEnd();
        iterateAllTriggers(turnEnd);
        turn ++;
    }
}