import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    public static int TOTAL_CARDS = 108;
    public static int NUMBER_OF_PLAYERS = 2;
    public static int STARTING_CARDS = 7;

    @Test
    void gameTest() {
        // Testing the constructor as a whole
        Game g = new Game(2);

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

    }

    /* Made sense to combine this test with the start turn test.
    @Test
    void endTurnTest() {

    }
     */
}