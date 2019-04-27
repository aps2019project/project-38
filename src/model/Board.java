package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Board {
    ArrayList<ArrayList<Cell>> board;

    public Board() {
        for (int i = 0; i < Constant.BoardConstants.row; i++) {
            board.add(new ArrayList<>());
            for (int j = 0; j < Constant.BoardConstants.column; j++) {
                board.get(i).add(new Cell(i, j));
            }
        }
    }
}
