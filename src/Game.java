import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Game {
    /**
     * Constants declared defining the number of sets of each type of card
     */
    public static int ZEROS = 1;
    public static int ONE_TO_NINES = 2;
    public static int WILDS = 4;
    /**
     * Defines the number of cards each Player starts with
     * at the beginning of a Game
     */
    public static int STARTING_CARDS = 7;

    /**
     * Stores the Players present in the Game
     */
    private Player[] players;
    /**
     * Stores the current turn order as an ArrayList of Players.
     * Index 0 represents the Player who will play next
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
     * True if the game has started and nobody has won, false otherwise
     * Main game loop will run while(inProgress)
     */
    private Boolean inProgress;

    /**
     * Flag to signal that a Player's turn will be skipped
     */
    private Boolean skipFlag;

    /**
     * Flag to signal that a Player will have to draw two cards
     */
    private Boolean drawTwoFlag;

    /**
     * Flag to signal that a Player will have to draw four cards
     */
    private Boolean drawFourFlag;

    //private int numOfPlayers;

    private Card.Color currentColor;

    private Player currentPlayer;

    private Player winner;

    /**
     * Public constructor for Game objects
     * Constructs the Game and initializes the game starting state.
     * @param numOfPlayers number of Players in the game
     */
    public Game(int numOfPlayers) {
        /* Initializing variables */
        this.inProgress = false;
        this.currentPlayer = null;
        this.currentColor = null;
        this.skipFlag = false;
        this.drawTwoFlag = false;
        this.drawFourFlag = false;

        /* Calling functions to set up the initial Game state */
        this.initiateStandardDeck();
        this.initiatePlayers(numOfPlayers);
        this.initiateTurnOrder();
        this.initiateDiscardPile();
    }

    /**
     * Simply sets inProgress Boolean to true;
     * Main game loop will run while(inProgress)
     */
    public void startGame() {
        this.inProgress = true;
    }

    /**
     * Simply sets inProgress Boolean to false;
     * Main game loop will exit on check.
     */
    public void endGame() {
        this.inProgress = false;
    }

    /**
     * Starts the player's turn.
     * Removes player from turnOrder ArrayList
     * while turn is in progress.
     */
    public void startTurn() {
        this.currentPlayer = this.turnOrder.remove(0);
    }

    /**
     * Main logic of basic gameplay.
     * Executes a turn for the current player.
     */
    public void executeTurn() {
        if (this.skipFlag) {
            this.skipFlag = false;
            return;
        }
        if (this.drawTwoFlag) {
            this.drawTwoFlag = false;
            drawX(currentPlayer, 2);
            return;
        }
        if (this.drawFourFlag) {
            this.drawFourFlag = false;
            drawX(currentPlayer, 4);
            return;
        }
        if (!isAbleToPlay(currentPlayer)) {
            drawCard();
            return;
        }

        /* BELOW THIS WILL BE REPLACED WITH AN I/O FUNCTION TO GET USER INPUT */
        int cardIndex = getFirstPlayableCardIndex(currentPlayer);
        /* ABOVE THIS WILL BE REPLACED WITH AN I/O FUNCTION TO GET USER INPUT */

        /*
         * if cardIndex == -1, player elected to draw a card despite
         * having a playable card
         */
        if (cardIndex == -1) {
            drawCard();
            return;
        }
        Card playedCard = currentPlayer.removeCardFromHand(cardIndex);
        handleCard(playedCard);
    }

    /**
     * Ends the player's turn.
     * Adds player back to end of turnOrder ArrayList.
     */
    public void endTurn() {
        if (this.currentPlayer.noCards()) {
            endGame();
            winner = currentPlayer;
            return;
        }
        this.turnOrder.add(this.currentPlayer);
        this.currentPlayer = null;
    }

    /**
     * Updates the game state to reflect that the Card has been played.
     * Assumes that the Card has already been checked as a valid card to be played.
     * @param playedCard Card that played during the current turn
     */
    private void handleCard(Card playedCard) {
        this.discard.add(playedCard);
        this.currentColor = playedCard.getColor();
        if (playedCard.getColor() == Card.Color.WILD) {
            if (playedCard.getRank() == Card.Rank.DRAWFOUR) {
                this.drawFourFlag = true;
            }
            /* BELOW THIS WILL BE REPLACED WITH AN I/O FUNCTION TO GET USER INPUT */
            Card.Color newColor = Card.Color.RED;
            /* ABOVE THIS WILL BE REPLACED WITH AN I/O FUNCTION TO GET USER INPUT */
            chooseColor(newColor);
        } else {
            if (playedCard.getRank() == Card.Rank.SKIP) {
                this.skipFlag = true;
            }
            if (playedCard.getRank() == Card.Rank.DRAWTWO) {
                this.drawTwoFlag = true;
            }
            if (playedCard.getRank() == Card.Rank.REVERSE) {
                reverseTurnOrder();
            }

        }
    }

    /**
     * Function that changes the current color to the color passed in.
     * Called after a wild Card is played and a color needs to be selected.
     * @param newColor Color chosen to be the new current color
     */
    private void chooseColor(Card.Color newColor) {
        this.currentColor = newColor;
    }

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
     * This function randomly establishes the turn order for the game.
     */
    private void initiateTurnOrder() {
        this.turnOrder = new ArrayList<Player>();
        this.turnOrder.addAll(Arrays.asList(players));
        Collections.shuffle(this.turnOrder);
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
     * If the deck is empty, calls function to pull the
     * discard pile minus the top card as the new deck.
     * @param p Player who is being dealt a card
     */
    private Card dealOneCard(Player p) {
        if (deck.isEmpty()) {
            discardPileToDeck();
        }
        Card deckCard = this.deck.remove(this.deck.size() - 1);
        p.addCardToHand(deckCard);
        return deckCard;
    }

    /**
     * Takes the discard pile minus the top card and fills
     * the deck with the cards.
     * Called when the deck becomes empty.
     */
    private void discardPileToDeck() {
        Card discardTop = topOfDiscard();
        this.discard.remove(this.discard.size() - 1);
        this.deck.addAll(this.discard);
        Collections.shuffle(this.deck);
        this.discard.clear();
        this.discard.add(discardTop);
    }

    /**
     * Initiates the Discard Pile at the start of the Game
     * by removing the top card from the deck
     */
    private void initiateDiscardPile() {
        this.discard = new ArrayList<Card>();
        this.discard.add(this.deck.remove(this.deck.size() - 1));
        Card discardTop = topOfDiscard();

        /* Loops until a colored, numbered card is selected */
        while(!(discardTop.isColoredCard() && discardTop.isNumberedCard())) {
            this.deck.add(this.discard.remove(0));
            Collections.shuffle(this.deck);
            this.discard.add(this.deck.remove(this.deck.size() - 1));
            discardTop = topOfDiscard();
        }
        this.currentColor = topOfDiscard().getColor();
    }

    /**
     * Returns the top card of the discard pile
     * (Does not remove it)
     */
    private Card topOfDiscard() {
        return this.discard.get(this.discard.size() - 1);
    }

    /**
     * Reverses the Turn Order ArrayList;
     * Called when a reverse Card is played.
     */
    private void reverseTurnOrder() {
        Collections.reverse(this.turnOrder);
    }

    /**
     * Causes the Player to draw x cards
     * @param p Player drawing the cards
     * @param x number of cards drawn
     */
    private void drawX(Player p, int x) {
        for (int i = 0; i < x; i++) {
            dealOneCard(p);
        }
    }

    /**
     * Checks the Player's hand and returns a Boolean stating if the Player
     * has at least one card which can be played.
     * @param p Player who's hand the function is checking
     * @return true if Player p has a playable card, false otherwise.
     */
    private Boolean isAbleToPlay(Player p) {
        ArrayList<Card> hand = p.getHand();
        for (Card card : hand) {
            if (card.isValidCard(topOfDiscard())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if a Card can be played.
     * Different from Card.isValidCard because it checks
     * the currentColor in case a wild card was last played.
     * @param c card being checked for validity
     * @return true or false
     */
    private Boolean canBePlayed(Card c) {
        return (c.isValidCard(topOfDiscard()) || (c.getColor() == this.currentColor));
    }

    /**
     * Returns the index of the first playable card in a player's hand.
     * @param p Player to check
     * @return index of first playable card
     */
    private int getFirstPlayableCardIndex(Player p) {
        ArrayList<Card> playerHand = p.getHand();
        for (int i = 0; i < playerHand.size(); i++) {
            if (canBePlayed(playerHand.get(i))) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Current Player draws a card and plays it if
     * it is a valid card to be played.
     */
    private void drawCard() {
        Card drawnCard = dealOneCard(this.currentPlayer);
        if (canBePlayed(drawnCard)) {
            this.currentPlayer.removeCardFromHand(drawnCard);
            handleCard(drawnCard);
        }
    }

    public Boolean getInProgress() {
        return inProgress;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getWinner() {
        return winner;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public ArrayList<Card> getDiscard() {
        return discard;
    }

    public ArrayList<Player> getTurnOrder() {
        return turnOrder;
    }

}
