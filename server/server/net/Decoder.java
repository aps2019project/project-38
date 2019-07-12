package server.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import javafx.util.Pair;
import model.*;
import model.cards.Card;
import model.cards.CardFactory;
import model.cards.HeroPower;
import model.cards.Warrior;
import model.effects.Melee;
import model.effects.Ranged;
import model.exceptions.NotEnoughConditions;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;

public class Decoder {
    private ServerSession ss;

    public Decoder(ServerSession ss) {
        this.ss = ss;
    }

    public void decode(Message m) throws IOException {
        switch (m) {
            case saveTheGame:
                Account.saveAccounts();
                break;
            case quitTheApp:
                ss.quit();
                break;
            case createAccount: {
                String username = ss.dis.readUTF();
                String password = ss.dis.readUTF();
                String againPassword = ss.dis.readUTF();
                String result = Account.createAccount(username, password, againPassword);
                ss.encoder.sendString(result);
                break;
            }
            case login: {
                String username = ss.dis.readUTF();
                String password = ss.dis.readUTF();
                String result = Account.login(username, password);
                ss.encoder.sendString(result);
                if (result.equals("You logged in successfully")) {
                    ss.username = username;
                    ss.authToken = randomString();
                    ss.encoder.sendString(ss.authToken);
                }
                break;
            }
            case logOut: {
                String username = ss.dis.readUTF();
                Account.getUsernameToAccount().get(username).isActiveNow = false;
                ss.authToken = "";
                ss.username = null;
                break;
            }
            case updateRanking: {
                ArrayList<Account> allAccounts = Account.getSortedAccounts();
                ArrayList<Pair<Pair<String, Boolean>, Integer>> ranking = new ArrayList<>();
                for (Account account : allAccounts) {
                    int numberOfWins = 0;
                    for (MatchHistory matchHistory : account.getHistory()) {
                        if (matchHistory.getDidWin()) numberOfWins++;
                    }
                    Pair<Pair<String, Boolean>, Integer> pair = new Pair<>(new Pair<>(ss.username, account.isActiveNow), numberOfWins);
                    ranking.add(pair);
                }
                Gson gson = new Gson();
                String result = gson.toJson(ranking);
                ss.dos.writeUTF(result);
                break;
            }
            case sendMessage: {
                String messageText = ss.dis.readUTF();
                Pair<String, String> message = new Pair<>(ss.username, messageText);
                synchronized (GlobalChat.globalChat.messages) {
                    GlobalChat.globalChat.messages.add(message);
                    for (ServerSession ss : ServerSession.serverSessions) {
                        if (ss.username == null) continue;
                        ss.encoder.sendMessage(Message.updateMessages);
                        Gson gson = new Gson();
                        String ranking = gson.toJson(GlobalChat.globalChat.messages);
                        ss.encoder.sendString(ranking);
                    }
                }
                break;
            }
            case startTheGame: {
                String username = ss.dis.readUTF();
                if (Account.getUsernameToAccount().get(username).getCollection().getMainDeck() != null) {
                    ss.encoder.sendMessage(Message.accountDeckIsValid);
                } else {
                    ss.encoder.sendMessage(Message.accountDeckIsNotValid);
                }
                break;
            }
            case HeroPowerOfPlayer: {
                Gson gson = new Gson();
                HeroPower hp = MatchMaker.PGPGetter(ss.username).getKey().getPlayerHero().getPower();
                ss.encoder.sendPackage(Message.HeroPowerOfPlayer, gson.toJson(hp));
                break;
            }
            case WarriorOfACell: {
                Gson gson = new Gson();
                Warrior warrior = MatchMaker.PGPGetter(ss.username).getValue().getBoard().getCell((int) readObject(), (int) readObject()).getWarrior();
                ss.encoder.sendPackage(Message.WarriorOfACell, gson.toJson(warrior));
                break;
            }
            case IndexofAvatar: {
                int playerIndex = (int) readObject();
                ss.encoder.sendPackage(Message.IndexofAvatar, MatchMaker.PGPGetter(ss.username).getValue().getPlayers()[playerIndex - 1]);
                break;
            }
            case PlayerUsername: {
                ss.encoder.sendPackage(Message.PlayerUsername, ss.username);
                break;
            }
            case ActivePlayerIndex: {
                ss.encoder.sendPackage(Message.ActivePlayerIndex, MatchMaker.PGPGetter(ss.username).getValue().getActivePlayerIndex());
                break;
            }
            case isMyWarrior: {
                int row = (int) readObject();
                int col = (int) readObject();
                MatchMaker.PGPGetter(ss.username).getKey().getWarriors().stream().anyMatch(warrior -> warrior.getCell().getRow() == row && warrior.getCell().getColumn() == col);
                break;
            }
            case isThereWarrior: {
                int row = (int) readObject();
                int col = (int) readObject();
                Game game = MatchMaker.PGPGetter(ss.username).getValue();
                Boolean thereIs = game.getBoard().getCell(row, col).getWarrior() != null;
                ss.encoder.sendPackage(Message.isThereWarrior, thereIs);
                break;
            }
            case useCard: {
                int handIndex = (int) readObject();
                int row = (int) readObject();
                int col = (int) readObject();
                Game game = MatchMaker.PGPGetter(ss.username).getValue();
                try {
                    checkWhoseTurn();
                    game.useCard(handIndex, game.getBoard().getCell(row, col));
                } catch (NotEnoughConditions notEnoughConditions) {
                    ss.encoder.sendPackage(Message.showPopup, notEnoughConditions.getMessage());
                }
                break;
            }
            case useSpecialPower: {
                int row = (int) readObject();
                int col = (int) readObject();
                Game game = MatchMaker.PGPGetter(ss.username).getValue();
                try {
                    checkWhoseTurn();
                    game.useSpecialPower(game.getBoard().getCell(row, col));
                } catch (NotEnoughConditions notEnoughConditions) {
                    ss.encoder.sendPackage(Message.showPopup, notEnoughConditions.getMessage());
                }
                break;
            }
            case useCollectible: {
                String itemName = (String) readObject();
                int row = (int) readObject();
                int col = (int) readObject();
                Game game = MatchMaker.PGPGetter(ss.username).getValue();
                try {
                    checkWhoseTurn();
                    game.useCollectible(itemName, game.getBoard().getCell(row, col));
                } catch (NotEnoughConditions notEnoughConditions) {
                    ss.encoder.sendPackage(Message.showPopup, notEnoughConditions.getMessage());
                }
                break;
            }
            case attack: {
                Game game = MatchMaker.PGPGetter(ss.username).getValue();
                Gson gson = new Gson();
                Cell sCellRaw = gson.fromJson((String) readObject(), Cell.class);
                Cell sCell = game.getBoard().getCell(sCellRaw.getRow(), sCellRaw.getColumn());
                Cell tCell = game.getBoard().getCell((int) readObject(), (int) readObject());
                try {
                    checkWhoseTurn();
                    game.attack(sCell, tCell);
                } catch (NotEnoughConditions notEnoughConditions) {
                    ss.encoder.sendPackage(Message.showPopup, notEnoughConditions.getMessage());
                }
                break;
            }
            case comboAttack: {
                Game game = MatchMaker.PGPGetter(ss.username).getValue();
                ArrayList<Cell> cells = getJsonCellArrayAsRealArray((String) readObject());
                Cell tCell = game.getBoard().getCell((int) readObject(), (int) readObject());
                try {
                    checkWhoseTurn();
                    game.comboAttack(cells, tCell);
                } catch (NotEnoughConditions notEnoughConditions) {
                    ss.encoder.sendPackage(Message.showPopup, notEnoughConditions.getMessage());
                }
                break;
            }
            case move:{
                Game game = MatchMaker.PGPGetter(ss.username).getValue();
                Gson gson = new Gson();
                Cell sCellRaw = gson.fromJson((String) readObject(), Cell.class);
                Cell sCell = game.getBoard().getCell(sCellRaw.getRow(),sCellRaw.getColumn());
                Cell tCell = game.getBoard().getCell((int)readObject(),(int)readObject());
                try {
                    checkWhoseTurn();
                    game.move(sCell,tCell);
                } catch (NotEnoughConditions notEnoughConditions) {
                    ss.encoder.sendPackage(Message.showPopup, notEnoughConditions.getMessage());
                }
                break;
            }
            case endTurn:{
                Game game = MatchMaker.PGPGetter(ss.username).getValue();
                try {
                    checkWhoseTurn();
                    game.endTurn();
                } catch (NotEnoughConditions notEnoughConditions) {
                    ss.encoder.sendPackage(Message.showPopup, notEnoughConditions.getMessage());
                }
                break;
            }
            case quitTheGame:{
                Game game = MatchMaker.PGPGetter(ss.username).getValue();
                try {
                    checkWhoseTurn();
                    game.endGame();
                } catch (NotEnoughConditions notEnoughConditions) {
                    ss.encoder.sendPackage(Message.showPopup, notEnoughConditions.getMessage());
                }
                break;
            }
            case manaCheat:{
                MatchMaker.PGPGetter(ss.username).getKey().addMana(1);
                break;
            }
            ///////
            case getCollection:{
                Collection collection = Account.getUsernameToAccount().get(ss.username).getCollection();
                ss.encoder.sendPackageJ(Message.getCollection,collection);
                break;
            }
            case getDerrick:{
                ss.encoder.sendPackage(Message.getDerrick,Account.getUsernameToAccount().get(ss.username).derrick);
                break;
            }
            case getAllBuiltMinions:{
                ss.encoder.sendPackageJ(Message.getAllBuiltMinions, CardFactory.getAllBuiltMinions());
                break;
            }
            case getAllBuiltHeroes:{
                ss.encoder.sendPackageJ(Message.getAllBuiltHeroes,CardFactory.getAllBuiltHeroes());
                break;
            }
            case getAllBuiltSpells:{
                ss.encoder.sendPackageJ(Message.getAllBuiltSpells,CardFactory.getAllBuiltSpells());
                break;
            }
            case getAllBuiltItems:{
                ss.encoder.sendPackageJ(Message.getAllBuiltItems,CardFactory.getAllBuiltItems());
                break;
            }
            case getWarriorType:{
                int id = (int) readObject();
                Card card = Card.getAllCards().get(id);
                boolean ranged = card.getEffects().stream().anyMatch(effect -> effect instanceof Ranged);
                boolean melee = card.getEffects().stream().anyMatch(effect -> effect instanceof Melee);

                String type;
                if(ranged && melee){
                    type = "Hybrid";
                }else if(ranged){
                    type = "Range";
                }else {
                    type = "Melee";
                }
                ss.encoder.sendPackage(Message.getWarriorType,type);
                break;
            }
            case getIDByName:{
                String name = (String) readObject();
                int id = Card.getIDByName(name);
                ss.encoder.sendPackage(Message.getIDByName,id);
                break;
            }
            case getAllCards:{
                Gson gson = new Gson();

                Message message = Message.getAllCards;
                ArrayList<Object> data = new ArrayList<>();

                data.add(Card.getAllCards().size());
                for (Map.Entry<Integer, Card> entry : Card.getAllCards().entrySet()) {
                    if(entry.getValue() instanceof Warrior){
                        data.addAll(Arrays.asList(entry.getKey(),Message.itsWarrior.ordinal(),gson.toJson(entry.getValue())));
                    }else {
                        data.addAll(Arrays.asList(entry.getKey(),Message.itsSpell.ordinal(),gson.toJson(entry.getValue())));
                    }
                }

                ss.encoder.sendPackage(message,data.toArray());
            }
            case getAllBuiltMinionsHashMapForShop:{
                Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
//                ss.encoder.sendPackage(Message.getAllBuiltMinionsHashMapForShop,gson.toJson());//todo amir
                break;
            }
            case getAllBuiltHeroesHashMapForShop:{
                Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
//                ss.encoder.sendPackage(Message.getAllBuiltHeroesHashMapForShop,gson.toJson());//todo amir
                break;
            }
            case getAllBuiltSpellsHashMapForShop:{
                Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
//                ss.encoder.sendPackage(Message.getAllBuiltSpellsHashMapForShop,gson.toJson());//todo amir
                break;
            }
            case getAllBuiltItemsHashMapForShop:{
                Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
//                ss.encoder.sendPackage(Message.getAllBuiltItemsHashMapForShop,gson.toJson());//todo amir
                break;
            }
            //////ali:
            case AuctionProposedPrice:{
                int auctionIndex = (int) readObject();
                String username = (String) readObject();
                int proposedPrice = (int) readObject();
                Auction.receiveNewProposedPrice(auctionIndex, username, proposedPrice);
            }
            case StartNewAuction:{
                String username = (String) readObject();
                String cardName = (String) readObject();
                int auctionIndex = Auction.startNewAuctionAndGetIndex(username, cardName);
                Encoder.sendPackageToAll(Message.StartNewAuction, username, cardName, auctionIndex);
            }
        }
    }

    private ArrayList<Cell> getJsonCellArrayAsRealArray(String json) {
        Gson gson = new Gson();
        Game game = MatchMaker.PGPGetter(ss.username).getValue();
        ArrayList<Cell> cells = new ArrayList<>();

        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(json);
        for (JsonElement jsonElement : je.getAsJsonArray()) {
            Cell cellRaw = gson.fromJson(jsonElement,Cell.class);
            Cell cell = game.getBoard().getCell(cellRaw.getRow(),cellRaw.getColumn());
            cells.add(cell);
        }

        return cells;
    }

    public void checkWhoseTurn() throws NotEnoughConditions {
        Game game = MatchMaker.PGPGetter(ss.username).getValue();
        if (!ss.username.equals(game.getActivePlayer().username)) {
            throw new NotEnoughConditions("Not your turn!");
        }
    }

    public String randomString() {
        Random random = new Random();
        String randomString = "";
        randomString = randomString + (char) (random.nextInt(100));
        randomString = randomString + (char) (random.nextInt(100));
        randomString = randomString + (char) (random.nextInt(100));
        return randomString;
    }

    public Object readObject() {
        try (ObjectInputStream ois = new ObjectInputStream(ss.dis)) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}