import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    void isValidCard() {
        Card redFour = new Card(Card.Color.RED, Card.Rank.FOUR);
        Card blueFour = new Card(Card.Color.BLUE, Card.Rank.FOUR);
        Card redDrawTwo = new Card(Card.Color.RED, Card.Rank.DRAWTWO);
        Card wild = new Card(Card.Color.WILD, Card.Rank.WILD);
        Card wildDrawFour = new Card(Card.Color.WILD, Card.Rank.DRAWFOUR);

        // Check same color
        assertTrue(redDrawTwo.isValidCard(redFour));

        // Check same rank
        assertTrue(redFour.isValidCard(blueFour));

        // Check different color and rank
        assertFalse(blueFour.isValidCard(redDrawTwo));

        // Check wild cards
        assertTrue(wild.isValidCard(redDrawTwo));
        assertTrue(wild.isValidCard(wild));
        assertTrue(wildDrawFour.isValidCard(blueFour));
        assertTrue(wildDrawFour.isValidCard(wild));
    }
}