import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameViewTest {

    @Test
    void testTerminalDisplay() {
        GameView.numberOfPlayersPrompt();
        Game g = new Game(3);
        GameView view = new GameView(g);
        g.startGame();
        g.startTurn();
        view.displayGameState();
        g.executeTurn();
        GameView.selectColorPrompt();
        g.endTurn();
        g.endGame();
        view.displayWinner();
    }

}