import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    public static int TOTAL_CARDS = 108;
    public static int NUMBER_OF_PLAYERS = 2;
    public static int STARTING_CARDS = 7;


    @Test
    void gameTest() {
        // Testing the constructor as a whole
        Game g = new Game(2);
        g.setExtraRulesFalse();

        // Testing deck size (- 1 comes from discard pile)
        assertEquals(TOTAL_CARDS - (NUMBER_OF_PLAYERS * STARTING_CARDS) - 1,
                g.getDeck().size());

        // Testing discard
        assertEquals(1, g.getDiscard().size());
        Card topOfDiscard = g.getDiscard().get(0);
        assertTrue(topOfDiscard.isColoredCard() && topOfDiscard.isNumberedCard());

        // Testing turnOrder
        assertEquals(2, g.getTurnOrder().size());
        assertNotEquals(g.getTurnOrder().get(0), g.getTurnOrder().get(1));

    }

    @Test
    void startGameTest() {
        Game g = new Game(2);
        g.startGame();
        // Simple test for a simple function
        assertTrue(g.getInProgress());
    }

    @Test
    void endGameTest() {
        Game g = new Game(2);
        g.startGame();
        g.endGame();
        // Another simple test for another simple function
        assertFalse(g.getInProgress());
    }

    @Test
    void startAndEndTurnTest() {
        Game g = new Game(2);
        g.setExtraRulesFalse();
        g.startGame();
        Player firstPlay = g.getTurnOrder().get(0);
        Player secondPlay = g.getTurnOrder().get(1);
        g.startTurn();

        // Test that firstPlay was removed from turn order
        assertEquals(1, g.getTurnOrder().size());

        // Test that firstPlay is set to current player
        assertEquals(firstPlay, g.getCurrentPlayer());

        // Test that secondPlay is now the first Player in turn order
        assertEquals(secondPlay, g.getTurnOrder().get(0));

        g.endTurn();

        // Test that firstPlay was added back to turn order
        assertEquals(firstPlay, g.getTurnOrder().get(1));

        // Test that inProgress is still true
        assertTrue(g.getInProgress());

        g.startTurn();

        Player currPlayer = g.getCurrentPlayer();

        int cardsToRemove = currPlayer.getHand().size();
        for (int i = 0; i < cardsToRemove; i++) {
            currPlayer.removeCardFromHand(0);
        }

        g.endTurn();

        // Test that the game ended and a winner was declared
        assertFalse(g.getInProgress());
        assertEquals(currPlayer, g.getWinner());
    }

    @Test
    void executeTurnTest() {
        ArrayList<Card> playerOneHand = new ArrayList<>();
        playerOneHand.add(new Card(Card.Color.RED, Card.Rank.FOUR));
        playerOneHand.add(new Card(Card.Color.RED, Card.Rank.SKIP));
        playerOneHand.add(new Card(Card.Color.WILD, Card.Rank.DRAWFOUR));
        playerOneHand.add(new Card(Card.Color.RED, Card.Rank.DRAWTWO));
        playerOneHand.add(new Card(Card.Color.RED, Card.Rank.REVERSE));
        playerOneHand.add(new Card(Card.Color.RED, Card.Rank.FIVE));

        ArrayList<Card> playerTwoHand = new ArrayList<>();
        playerTwoHand.add(new Card(Card.Color.BLUE, Card.Rank.NINE));


        Game g = new Game(2);
        g.setExtraRulesFalse();
        g.getTurnOrder().get(0).setHand(playerOneHand);
        g.getTurnOrder().get(1).setHand(playerTwoHand);
        g.startGame();
        g.startTurn();
        g.setTopDiscard(new Card(Card.Color.RED, Card.Rank.ONE));
        g.executeTurn();

        // Test that the card was played
        assertEquals(5, g.getCurrentPlayer().getHand().size());
        // Test that the card is on discard pile
        assertEquals(Card.Rank.FOUR,
                g.getDiscard().get(g.getDiscard().size() - 1).getRank());

        g.endTurn();

        g.startTurn();
        //g.getCurrentPlayer().setHand(playerTwoHand);
        int cardsInDeck = g.getDeck().size();
        g.executeTurn();

        // Test that the player had to draw a card
        assertEquals(cardsInDeck - 1, g.getDeck().size());

        g.endTurn();

        /*
         * Card drawn may or may not have been played, so doing this
         * for definitive testing
         */
        g.setTopDiscard(new Card(Card.Color.RED, Card.Rank.FOUR));
        g.clearFlags();

        g.startTurn();
        g.executeTurn();

        // Test that the card is in the discard pile
        assertEquals(Card.Rank.SKIP,
                g.getDiscard().get(g.getDiscard().size() - 1).getRank());
        // Test that skipFlag was set to true
        assertTrue(g.getSkipFlag());

        g.endTurn();

        g.startTurn();
        int numCardsInHand = g.getCurrentPlayer().getHand().size();
        g.executeTurn();

        // Test that the turn was skipped
        assertEquals(numCardsInHand, g.getCurrentPlayer().getHand().size());

        // Test that skipFlag was set to false
        assertFalse(g.getSkipFlag());

        g.endTurn();

        g.startTurn();

        g.executeTurn(); // Wild Draw four card should be played here

        // Test that wild draw four card was played
        assertEquals(g.getDiscard().get(g.getDiscard().size() - 1).getRank(),
                Card.Rank.DRAWFOUR);

        // Test that drawFourFlag was set to true
        assertTrue(g.getDrawFourFlag());

        g.endTurn();

        g.startTurn();
        numCardsInHand = g.getCurrentPlayer().getHand().size();
        g.executeTurn();

        // Test that the player drew 4 cards and skipped their turn
        assertEquals(numCardsInHand + 4,
                g.getCurrentPlayer().getHand().size());

        assertFalse(g.getDrawFourFlag());

        g.endTurn();

        g.startTurn();
        g.executeTurn(); // Player 1 plays draw 2 card
        g.endTurn();

        // Test drawTwoFlag
        assertTrue(g.getDrawTwoFlag());

        g.startTurn();
        g.executeTurn(); // Player 2 draws 2
        g.endTurn();

        g.startTurn();
        g.executeTurn(); // Player 1 plays reverse card
        g.endTurn();



        g.startTurn();
        g.executeTurn(); // Player 2 may play or draw
        g.endTurn();

        // For deterministic testing
        g.setTopDiscard(new Card(Card.Color.RED, Card.Rank.REVERSE));
        g.clearFlags();

        g.startTurn();
        Player shouldWin = g.getCurrentPlayer();
        // Test that the player has a single card in hand
        assertEquals(1, shouldWin.getHand().size());
        // Test that the reverse card is on top of discard
        assertEquals(Card.Rank.REVERSE,
                g.getDiscard().get(g.getDiscard().size() - 1).getRank());

        g.executeTurn();
        g.endTurn();

        // Test that the player has an empty hand
        assertEquals(0, shouldWin.getHand().size());
        // Test that the player won
        assertEquals(shouldWin, g.getWinner());
    }

    @Test
    void discardToDeckTest() {
        ArrayList<Card> newHand = new ArrayList<>();
        newHand.add(new Card(Card.Color.BLUE, Card.Rank.ONE));

        Game g = new Game(2);
        g.getDeck().clear(); // Empty the deck

        for (Card.Rank r : Card.numberedRanks) {
            g.setTopDiscard(new Card(Card.Color.RED, r));
        }
        // 11 total cards in discard at this point
        g.startTurn();

        // Make sure the player will have to draw
        g.getCurrentPlayer().setHand(newHand);

        g.executeTurn();
        g.endTurn();

        // Test if deck contains the right amount of cards
        assertEquals(9, g.getDeck().size());
    }

    @Test
    void onlyWildRule() {
        Game g = new Game(3);
        ArrayList<Card> playerOneHand = new ArrayList<>();
        playerOneHand.add(new Card(Card.Color.RED, Card.Rank.ONE));
        playerOneHand.add(new Card(Card.Color.WILD, Card.Rank.WILD));
        g.getTurnOrder().get(0).setHand(playerOneHand);
        g.setTopDiscard(new Card(Card.Color.RED, Card.Rank.FOUR));
        g.startGame();
        g.startTurn();
        Player first = g.getCurrentPlayer();
        g.executeTurn();
        g.endTurn();
        assertEquals(2, first.getHand().size());
    }

    @Test
    void sameCardRule() {
        Game g = new Game(3);
        ArrayList<Card> playerOneHand = new ArrayList<>();
        ArrayList<Card> playerTwoHand = new ArrayList<>();
        playerOneHand.add(new Card(Card.Color.RED, Card.Rank.ONE));
        playerOneHand.add(new Card(Card.Color.BLUE, Card.Rank.TWO));
        playerTwoHand.add(new Card(Card.Color.RED, Card.Rank.ONE));
        playerTwoHand.add(new Card(Card.Color.BLUE, Card.Rank.TWO));

        g.getTurnOrder().get(0).setHand(playerOneHand);
        g.getTurnOrder().get(1).setHand(playerTwoHand);
        g.setTopDiscard(new Card(Card.Color.RED, Card.Rank.FOUR));

        g.startGame();
        g.startTurn();
        g.executeTurn();
        g.endTurn();
        g.startTurn();
        g.executeTurn();
        g.endTurn();

        assertTrue(g.getDrawTwoFlag());

    }

    /* Made sense to combine this test with the start turn test.
    @Test
    void endTurnTest() {

    }
     */
}