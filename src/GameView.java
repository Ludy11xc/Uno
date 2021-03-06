/**
 * Class to handle the view component of the MVC framework.
 * Displays current game state information to the console.
 */
public class GameView {

    /**
     * Game which the view displays information for
     */
    private final Game game;

    /**
     * Constructor for GameView object
     * @param g Game to display information for.
     */
    public GameView(Game g) {
        game = g;
    }

    /**
     * Displays a prompt asking for the number of players.
     */
    public static void numberOfPlayersPrompt() {
        System.out.println("Please input the number of players for this game (2-10): ");
    }

    /**
     * Displays a prompt when a WILD Card is played
     * and a Color needs to be selected
     */
    public static void selectColorPrompt() {
        System.out.println("Please enter the number of the color you would like to set the game to: \n1: RED \n2: YELLOW \n3: GREEN \n4: BLUE ");
    }

    /**
     * Displays a prompt asking the user for the index of the card to be played
     */
    public static void playCardPrompt() {
        System.out.println("Please enter the number of the card you wish to play (-1 to draw): ");
    }


    public static void notPlayableCardPrompt() {
        System.out.println("The card selected is not able to be played.");
    }

    /**
     * Displays the current state of the game, including:
     * Current Player,
     * Turn order following current Player,
     * Number of cards in main deck and discard pile,
     * Current card on top of discard pile,
     * Current Player hand
     */
    public void displayGameState() {
        clearConsole();

        Player currentPlayer = game.getCurrentPlayer();
        System.out.println("Current Player: " + currentPlayer.getName());

        displayTurnOrder();

        int deckCards = game.getDeck().size();
        int discardCards = game.getDiscard().size();

        System.out.println(deckCards + " Card(s) in Deck");
        System.out.println(discardCards + " Card(s) in Discard Pile");

        Card current = game.getDiscard().get(game.getDiscard().size() - 1);
        System.out.println("Current Card: " + current.toString());

        displayPlayerHand(currentPlayer);
    }

    /**
     * Displays the turn order, omits the current player if in
     * the middle of a turn
     */
    private void displayTurnOrder() {
        System.out.print("Current turn order:");
        for (Player p : game.getTurnOrder()) {
            System.out.print(" -> " + p.getName());
        }
        System.out.println();
    }

    /**
     * Displays the hand of the player specified
     * @param player Player who's hand is displayed
     */
    private void displayPlayerHand(Player player) {
        System.out.println(player.getName() + "'s hand: ");
        int index = 0;
        for (Card card : player.getHand()) {
            System.out.print(index + ": ");
            System.out.print(card.toString());
            System.out.print(" | ");
            index++;
        }
        System.out.println();
    }

    /**
     * Displays the winner of the game.
     */
    public void displayWinner() {
        System.out.println(game.getWinner() + " won!  Congrats!");
    }


    /**
     * Used to clear the console so that the current player
     * cannot see other player's cards.
     */
    private void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
