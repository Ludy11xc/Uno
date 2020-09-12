/**
 * Card class serves as the superclass for all card types in UNO.
 *
 */
public class Card {
    public static Color[] normalColors = new Color[]{Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE};
    public static Color[] wildColor = new Color[]{Color.WILD};
    public static Rank[] zeroRank = new Rank[]{Rank.ZERO};
    public static Rank[] normalRanks = new Rank[]{Rank.ONE, Rank.TWO, Rank.THREE, Rank.FOUR, Rank.FIVE, Rank.SIX, Rank.SEVEN, Rank.EIGHT, Rank.NINE, Rank.SKIP, Rank.REVERSE, Rank.DRAWTWO};
    public static Rank[] wildRanks = new Rank[]{Rank.WILD, Rank.DRAWFOUR};

    private Color color;
    private Rank rank;

    enum Color {
        RED,
        YELLOW,
        GREEN,
        BLUE,
        WILD;
    }

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
        DRAWFOUR;
    }

    public Card(Color color, Rank rank) {
        this.color = color;
        this.rank = rank;
    }

    public Boolean isValidColor(Card discardCard) {
        return (this.color == discardCard.color) || (this.color == Color.WILD);
    }

    public Boolean isValidRank(Card discardCard) {
        return (this.rank == discardCard.rank);
    }

    public Boolean isValidCard(Card discardCard) {
        return this.isValidColor(discardCard) || this.isValidRank(discardCard);
    }

    public Color getColor() {
        return color;
    }

    public Rank getRank() {
        return rank;
    }
}
