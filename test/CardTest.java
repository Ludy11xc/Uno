import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    void isValidCard() {
        Card redFour = new Card(Card.Color.RED, Card.Rank.FOUR);
        Card blueFour = new Card(Card.Color.BLUE, Card.Rank.FOUR);
        Card redDrawTwo = new Card(Card.Color.RED, Card.Rank.DRAWTWO);

        // Check same color
        assertTrue(redDrawTwo.isValidCard(redFour));

        // Check same rank
        assertTrue(redFour.isValidCard(blueFour));

        // Check different color and rank
        assertFalse(blueFour.isValidCard(redDrawTwo));
    }
}