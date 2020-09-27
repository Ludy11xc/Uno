public class GameMain {



    public static void main(String[] args) {
        int numberOfPlayers = GameController.scanNumberOfPlayers();
        Game g = new Game(numberOfPlayers);
        GameView view = new GameView(g);
        GameController controller = new GameController(g);

        g.startGame();
        while (g.getInProgress()) {
            g.startTurn();
            view.displayGameState();
            int index = controller.scanCardIndex();
            g.executeTurn(index);
            g.endTurn();
        }
        view.displayWinner();
    }
}
