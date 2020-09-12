import java.util.ArrayList;


public class Player {
    private ArrayList<Card> hand;

    public Player() {
        hand = new ArrayList<Card>();
    }

    public Boolean addCardToHand(Card card) {
        return hand.add(card);
    }

    public Card removeCardFromHand(int index) {
        return hand.remove(index);
    }
}
