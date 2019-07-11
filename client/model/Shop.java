package model;

import java.util.ArrayList;

public class Shop { //todo MOEINI فک کنم کلاینت دِک و کالکشن و شاپ نمیخواد
    private static Shop shop = new Shop();
    private ArrayList<Integer> cardIDs = new ArrayList<>();

    private Shop() {
    }
    public ArrayList<Integer> getCardIDs() {
        return cardIDs;
    }

    public static Shop getShop() {
        return shop;
    }
}
