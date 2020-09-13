import java.util.Arrays;

/**
 * Card class serves as the representation for all card types in UNO.
 * Stores the Card Color and Rank and allows for comparison of
 * two Cards.
 */
public class Card {
    /**
     * Grouping of colors and ranks to make the creation of the deck
     * and various other tasks easier
     */
    public static Color[] normalColors = new Color[]{Color.RED,
            Color.YELLOW, Color.GREEN, Color.BLUE};

    public static Color[] wildColor = new Color[]{Color.WILD};

    public static Rank[] zeroRank = new Rank[]{Rank.ZERO};

    public static Rank[] normalRanks = new Rank[]{Rank.ONE, Rank.TWO,
            Rank.THREE, Rank.FOUR, Rank.FIVE, Rank.SIX, Rank.SEVEN,
            Rank.EIGHT, Rank.NINE, Rank.SKIP, Rank.REVERSE, Rank.DRAWTWO};

    public static Rank[] wildRanks = new Rank[]{Rank.WILD, Rank.DRAWFOUR};

    public static Rank[] numberedRanks = new Rank[]{Rank.ZERO, Rank.ONE,
            Rank.TWO, Rank.THREE, Rank.FOUR, Rank.FIVE, Rank.SIX,
            Rank.SEVEN, Rank.EIGHT, Rank.NINE};

    /**
     * color stores the Color of the Card
     */
    private final Color color;

    /**
     * rank stores the Rank of the Card
     */
    private final Rank rank;

    /**
     * Enum to represent the color of the card;
     * decided to make WILD it's own color which will be matched
     * as a valid color to play on any card.
     */
    enum Color {
        RED,
        YELLOW,
        GREEN,
        BLUE,
        WILD
    }

    /**
     * Enum to represent the rank of the card;
     * Used to compare cards along with color to check
     * for a valid card to be played.
     */
    enum Rank {
        ZERO,
        ONE,
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT,
        NINE,
        SKIP,
        REVERSE,
        DRAWTWO,
        WILD,
        DRAWFOUR
    }

    /**
     * Constructor for the Card class
     * @param color defines the Color of the Card being constructed
     * @param rank defines the Rank of the Card being constructed
     */
    public Card(Color color, Rank rank) {
        this.color = color;
        this.rank = rank;
    }

    /**
     * This function compares the color of the card it is called on to the card passed in as a parameter.
     * @param discardCard Card to be compared to (card on top of the discard pile)
     * @return True or False
     */
    private Boolean isValidColor(Card discardCard) {
        return (this.color == discardCard.color) || (this.color == Color.WILD);
    }

    /**
     * This function compares the rank of the card it is called on to the card passed in as a parameter.
     * @param discardCard Card to be compared to (card on top of the discard pile)
     * @return True or False
     */
    private Boolean isValidRank(Card discardCard) {
        return (this.rank == discardCard.rank);
    }

    /**
     * This function compares the color and rank of the card it is called on to the card passed in as a parameter
     * and determines if the card is a valid card to be played.
     * @param discardCard Card to be compared to (card on top of the discard pile)
     * @return true or false
     */
    public Boolean isValidCard(Card discardCard) {
        return this.isValidColor(discardCard) || this.isValidRank(discardCard);
    }

    /**
     * Checks if the Card is a numbered card.
     * @return true or false
     */
    public Boolean isNumberedCard() {
        return Arrays.asList(numberedRanks).contains(this.rank);
    }

    /**
     * Checks if the Card is a normal colored card.
     * @return true or false
     */
    public Boolean isColoredCard() {
        return Arrays.asList(normalColors).contains(this.color);
    }

    public Color getColor() {
        return color;
    }

    public Rank getRank() {
        return rank;
    }
}
