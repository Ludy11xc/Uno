import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void addAndRemoveCard() { // Changed name from addCardToHand to addAndRemoveCard because it made sense to combine the tests
        // Setting up hands to be compared
        ArrayList<Card> empty = new ArrayList<Card>();
        Card c1 = new Card(Card.Color.RED, Card.Rank.ONE);
        Card c2 = new Card(Card.Color.WILD, Card.Rank.DRAWFOUR);
        ArrayList<Card> trialHand1 = new ArrayList<Card>();
        trialHand1.add(c1);
        ArrayList<Card> trialHand2 = new ArrayList<Card>();
        trialHand2.add(c1);
        trialHand2.add(c2);


        Player p = new Player();
        p.addCardToHand(c1);
        // Test result after one card added
        assertEquals(trialHand1, p.getHand());

        p.addCardToHand(c2);
        // Test result after two cards added
        assertEquals(trialHand2, p.getHand());

        p.removeCardFromHand(1);
        // Test result after on card was removed
        assertEquals(trialHand1, p.getHand());

        p.removeCardFromHand(0);
        assertEquals(empty, p.getHand());
    }

    // Combined this test with the last one because it made more sense.
    /*
    @Test
    void removeCardFromHand() {

    }
    */
}