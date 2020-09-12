import java.util.ArrayList;
import java.util.Collections;

public class Game {
    /**
     * Constants declared defining the number of sets of each type of card
     */
    public static int ZEROS = 1;
    public static int ONE_TO_NINES = 2;
    public static int WILDS = 4;
    /**
     * Defines the number of cards each Player starts with at the beginning of a Game
     */
    public static int STARTING_CARDS = 7;

    /**
     * Stores the Players present in the Game
     */
    private Player[] players;
    /**
     * Stores the current turn order as an ArrayList of Players
     */
    private ArrayList<Player> turnOrder;
    /**
     * Stores the deck as an ArrayList of Cards
     */
    private ArrayList<Card> deck;
    /**
     * Stores the discard pile as an ArrayList of Cards;
     * Will check the top card in this pile every time a card is
     * played to check if it is valid.
     */
    private ArrayList<Card> discard;


    /**
     * This function creates the array of players and deals out cards
     * @param numberOfPlayers number of players playing in the game
     */
    private void initiatePlayers(int numberOfPlayers) {
        this.players = new Player[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) {
            Player newPlayer = new Player();
            this.players[i] = newPlayer;
        }
        dealPlayerHands();
    }


    /**
     * This function creates the ArrayList to store the deck,
     * and populates it with the cards of a standard UNO deck shuffled:
     * Of each color: one zero, two of each card 1-9, two skips,
     * two reverses, and two draw twos;
     * In addition: 4 wild cards, 4 wild/draw four cards
     * For a total of 108 cards
     */
    private void initiateStandardDeck() {
        this.deck = new ArrayList<Card>();
        // add all of the zero cards
        addDeckCards(ZEROS, Card.normalColors, Card.zeroRank);
        // add all of the 1-9 AND skip, reverse, and draw two cards
        addDeckCards(ONE_TO_NINES, Card.normalColors, Card.normalRanks);
        // add all of the wild and wild draw four cards
        addDeckCards(WILDS, Card.wildColor, Card.wildRanks);
        // Shuffle Deck
        Collections.shuffle(this.deck);
    }


    /**
     * Helper function which adds cards to the deck based on the given parameters
     * @param sets Specifies the number of copies of each card in the deck
     * @param colors Specifies the colors of the cards being added
     * @param ranks Specifies the ranks of the cards being added
     */
    private void addDeckCards(int sets, Card.Color[] colors, Card.Rank[] ranks) {
        for (int i = 0; i < sets; i++) {
            for (Card.Color c : colors) {
                for (Card.Rank r : ranks) {
                    this.deck.add(new Card(c, r));
                }
            }
        }
    }


    /**
     * This function deals a starting hand to each player,
     * and must be called after the deck and players are initialized.
     */
    private void dealPlayerHands() {
        for (int i = 0; i < STARTING_CARDS; i++) {
            for (Player p : players) {
                dealOneCard(p);
            }
        }
    }

    /**
     * Deals a single card to the hand of the Player specified;
     * removes that card from the top of the deck.
     * @param p Player who is being dealt a card
     */
    private void dealOneCard(Player p) {
        p.addCardToHand(this.deck.remove(this.deck.size() - 1));
    }

    /**
     * Initiates the Discard Pile at the start of the Game
     * by removing the top card from the deck
     */
    private void initiateDiscardPile() {
        this.discard = new ArrayList<Card>();
        discard.add(this.deck.remove(this.deck.size() - 1));
    }

    /**
     * Returns the top card of the discard pile
     * (Does not remove it)
     */
    private Card topOfDiscard() {
        return this.discard.get(this.discard.size() - 1);
    }

}
