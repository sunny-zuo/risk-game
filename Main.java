package civGame;

public class Main {
	public GameEngine gameEngine;
	public static void main (String [] args) {
		GameBoard.createGame();
		gameEngine = new GameEngine();
	}
}
