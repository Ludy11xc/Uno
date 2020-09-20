import java.util.ArrayList;

/**
 * Player class to represent each player in game and store
 * information relative to that player i.e. hand
 */
public class Player {

    /**
     * Keeps track of number of players created.
     */
    private static int numberOfPlayers = 0;

    /**
     * hand stores the Cards currently in the Player's hand
     */
    private ArrayList<Card> hand;


    /**
     * Player name used when displaying turn order
     */
    private final String name;

    /**
     * Constructor for Player;
     * Initializes the hand ArrayList for the Player,
     * and sets the name since none were provided.
     */
    public Player() {
        numberOfPlayers++;
        int id = numberOfPlayers;
        hand = new ArrayList<>();
        name = "Player " + id;
    }

    /**
     * Alternate constructor used when a name is provided
     * @param name Player name
     */
    public Player(String name) {
        hand = new ArrayList<>();
        this.name = name;
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

    /**
     * This function removes the specified card from the Player's hand
     * @param cardToRemove Card to be removed
     * @return True if the Card was removed, false otherwise
     */
    public Boolean removeCardFromHand(Card cardToRemove) {
        return hand.remove(cardToRemove);
    }


    public ArrayList<Card> getHand() {
        return hand;
    }


    /**
     * Sets hand to object passed in.  Used only for easier testing.
     * @param newHand ArrayList of Cards to replace hand
     */
    public void setHand(ArrayList<Card> newHand) {
        hand = newHand;
    }


    public String getName() {
        return name;
    }


    /* May use for final implementation
    public int getId() {
        return id;
    }
    */


    /**
     * Checks if player hand is empty
     * @return True if player has no cards, false otherwise
     */
    public Boolean noCards() {
        return hand.isEmpty();
    }
}
