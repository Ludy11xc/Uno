/**
 * Class to handle the view component of the MVC framework.
 * Displays current game state information to the console.
 */
public class GameView {

    /**
     * Game which the view displays information for
     */
    private Game game;

    /**
     * Displays the turn order, omits the current player if in
     * the middle of a turn
     */
    private void displayTurnOrder() {
        System.out.print("Current turn order:");
        int playerCounter = 0;
        for (Player p : game.getTurnOrder()) {
            System.out.print(" -> " + p.getName());
        }
        System.out.println();
    }

    private void printPlayerHand(Player player) {
        System.out.println(player.getName() + "'s hand: ");
        for (Card card : player.getHand()) {
            if (card.getColor() == Card.Color.WILD) {
                System.out.print(card.getRank());
            } else {
                System.out.print(card.getColor().name() + " " + card.getRank().name());
            }
            System.out.print(" | ");
        }
    }

}
