package model;
import model.player.Player;


public class Game {
    private int turn;
    private int turnEndTime;
    private Player[] players = new  Player[2];
    private Cell[][] board = new Cell[5][9];
}
