package model.actions;

import model.Game;
import model.cards.Card;
import model.gamestate.ReplaceCardState;

import java.util.Random;

public class ReplaceCard {
    public static void doIt(Game game, int handMapKey) {
        if (game.getActivePlayer().ableToReplaceCard) {
            Card card = game.getActivePlayer().getHand().get(handMapKey);
            Random random = new Random();
            Card newCard;
            while (true) {
                int randomIndex = random.nextInt(game.getActivePlayer().getMainDeck().getCardIDs().size());
                int cardID = game.getActivePlayer().getMainDeck().getCardIDs().get(randomIndex);
                if (cardID != card.getID()) {
                    newCard = Card.getAllCards().get(cardID);
                    break;
                }
            }
            game.getActivePlayer().getHand().put(handMapKey, newCard);
            game.getActivePlayer().ableToReplaceCard = false;
            ReplaceCardState replaceCardState = new ReplaceCardState();
            game.iterateAllTriggersCheck(replaceCardState);
        }
    }
}
