package model.cards;

import model.Cell;
import model.effects.AP;
import model.effects.HP;
import model.effects.Melee;
import model.effects.Ranged;
import view.fxmlControllers.ArenaController;

import java.io.*;

public class Warrior extends Card {
    transient private Cell cell;
    public int hp;
    public int ap;
}