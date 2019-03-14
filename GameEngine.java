package civGame;

public class GameEngine {
	public InputHandler inputHandler;
	protected Boolean playerTurn = true;
	
	public GameEngine() {
		inputHandler = new InputHandler();
		runPlayerTurn();
	}
	private void runPlayerTurn() {
		while (playerTurn) {
			GameBoard.drawGameState();
			inputHandler.handleInput();
		}
	}
	
}
