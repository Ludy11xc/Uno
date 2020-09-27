import java.util.Scanner;

/**
 * Class to handle the controller part of the MVC framework.
 * Allows the user to interact with the terminal and play
 * the Game.
 */
public class GameController {

    public static Scanner scanner = new Scanner(System.in);

    private Game game;

    public GameController(Game g) {
        this.game = g;
    }

    /**
     * Function used to let user select number of players
     * @return number of players
     */
    public static int scanNumberOfPlayers() {
        while (true) {
            try {
                GameView.numberOfPlayersPrompt();
                int players = scanner.nextInt();
                if (players < 2 || players > 10) {
                    continue;
                }
                return players;
            } catch(java.util.InputMismatchException e) {
                scanner.nextLine();
            }
        }
    }

    /**
     * Function lets user input color to select;
     * Called after a user plays a wild card
     * @return Color to change to
     */
    public static Card.Color scanColorChoice() {
        while(true) {
            try {
                GameView.selectColorPrompt();
                int color = scanner.nextInt();
                switch (color) {
                    case 1:
                        return Card.Color.RED;
                    case 2:
                        return Card.Color.YELLOW;
                    case 3:
                        return Card.Color.GREEN;
                    case 4:
                        return Card.Color.BLUE;
                    default:
                        continue;
                }
            }   catch(java.util.InputMismatchException e) {
                scanner.nextLine();
                continue;
            }
        }
    }

    /**
     * Function to allow user to input what card to be played
     * Automatically returns -1 if the player cannot play any cards
     * @return index of card to be played
     */
    public int scanCardIndex() {
        if (!game.isAbleToPlay(game.getCurrentPlayer())) {
            return -1;
        }
        if (game.getDrawFourFlag() || game.getDrawTwoFlag() || game.getSkipFlag()) {
            return -1;
        }
        while(true) {
            try {
                GameView.playCardPrompt();
                int index = scanner.nextInt();
                if (index == -1) return index;
                if (index < -1 || index > game.getCurrentPlayer().getHand().size()) {
                    continue;
                }
                if (game.canBePlayed(game.getCurrentPlayer().getHand().get(index))) {
                    return index;
                }
                GameView.notPlayableCardPrompt();
            } catch (java.util.InputMismatchException e) {
                scanner.nextLine();
                continue;
            }
        }
    }

}
