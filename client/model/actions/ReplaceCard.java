package model.actions;

import model.cards.Card;
import model.exceptions.NotEnoughConditions;
import model.gamestate.ReplaceCardState;

import java.util.Random;

public class ReplaceCard {
    public static void doIt(Game game, int handMapKey) throws NotEnoughConditions {
        if (game.getActivePlayer().ableToReplaceCard) {
            Card card = game.getActivePlayer().getHand().get(handMapKey);
            Random random = new Random();
            Card newCard;
            while (true) {
                int randomIndex = random.nextInt(game.getActivePlayer().getMainDeck().getCardIDs().size());
                int cardID = game.getActivePlayer().getMainDeck().getCardIDs().get(randomIndex);
                if (cardID != card.getID()) {
                    newCard = Card.getAllCards().get(cardID).deepCopy();
                    break;
                }
            }
            game.getActivePlayer().getHand().put(handMapKey, newCard);
            game.getActivePlayer().ableToReplaceCard = false;
            ReplaceCardState replaceCardState = new ReplaceCardState();
            game.iterateAllTriggersCheck(replaceCardState);
        }else {
            throw new NotEnoughConditions("You have already replaced a card this turn");
        }
    }
}
