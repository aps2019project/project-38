package model;


import java.util.Date;


public interface Constant {
    interface GameConstants {
        int boardRow = 3;
        int boardColumn = 5;
        long turnTime = 20000;
        Date gameDateObject = new Date();
    }
}
