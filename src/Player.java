import java.util.ArrayList;

/**
 *
 */
public class Player {
    /**
     * hand stores the Cards currently in the Player's hand
     */
    private ArrayList<Card> hand;

    /**
     * Constructor for Player;
     * Initializes the hand ArrayList for the Player
     */
    public Player() {
        hand = new ArrayList<Card>();
    }

    /**
     * This function adds a card to the Player's hand
     * @param card Card to be added to the Player's hand
     * @return True if the add was successful, False otherwise
     */
    public Boolean addCardToHand(Card card) {
        return hand.add(card);
    }

    /**
     * This function removes a card from the Player's hand at the given index
     * @param index Index of the card to be removed
     * @return Card that was removed from the Player's hand
     */
    public Card removeCardFromHand(int index) {
        return hand.remove(index);
    }
}
