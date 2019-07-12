package model;

import model.cards.Card;
import server.net.Encoder;
import server.net.Message;
import server.net.ServerSession;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class Auction extends Timer {
    private static HashMap<Integer, Auction> indexToAuctionHashMap = new HashMap<>();
    String usernameOfMaxProposedPrice;
    int maxProposedPrice;
    String usernameOfOwner;
    String cardName;

    private Auction(String username, String cardName) {
        usernameOfOwner = username;
        this.cardName = cardName;
        maxProposedPrice = Card.getCardByItsName(cardName).getPrice() / 2;
    }

    public static int startNewAuctionAndGetIndex(String username, String cardName) {
        int index;
        for (index = 0; true; index++) {
            if (!indexToAuctionHashMap.containsKey(index)) break;
        }
        final int finalIndex = index;
        Auction newAuction = new Auction(username, cardName);
        indexToAuctionHashMap.put(finalIndex, newAuction);
        final long startTime = System.currentTimeMillis() + 100;
        newAuction.schedule(new TimerTask() {
            @Override
            public void run() {
                int time = (int) ((System.currentTimeMillis() - startTime) / 1000);
                if (time >= 3 * 60) finishAuction(finalIndex);
            }
        }, 500, 3 * 60 * 1000 + 1000);
        return finalIndex;
    }

    private static synchronized void finishAuction(int index) {
        Auction auction = indexToAuctionHashMap.get(index);
        indexToAuctionHashMap.remove(index);
        ServerSession serverSession = ServerSession.getSession(auction.usernameOfOwner);
        if (auction.usernameOfMaxProposedPrice != null) {
            serverSession.encoder.sendPackage(Message.AuctionResult, String.format
                    ("Your card (%s) soled to %s with price %d",
                            auction.cardName, auction.usernameOfMaxProposedPrice, auction.maxProposedPrice));
            Shop.getShop().sell(Account.getUsernameToAccount().get(auction.usernameOfOwner),
                    Account.getUsernameToAccount().get(auction.usernameOfMaxProposedPrice),
                    Card.getCardByItsName(auction.cardName), auction.maxProposedPrice);
        } else {
            serverSession.encoder.sendPackage(Message.AuctionResult, String.format
                    ("Your card (%s) didn't sell", auction.cardName));
        }
    }

    public static void receiveNewProposedPrice(int auctionIndex, String username, int proposedPrice) {
        Auction auction = indexToAuctionHashMap.get(auctionIndex);
        ServerSession serverSession = ServerSession.getSession(username);
        synchronized (auction) {
            if (proposedPrice > auction.maxProposedPrice) {
                auction.usernameOfMaxProposedPrice = username;
                auction.maxProposedPrice = proposedPrice;
                serverSession.encoder.sendObject(true);
                Encoder.sendPackageToAll(Message.AuctionMaxProposedPriceUpdated, auctionIndex, username, proposedPrice);
            }
            else {
                serverSession.encoder.sendObject(false);
            }
        }
    }
}
