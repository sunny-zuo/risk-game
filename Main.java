package civGame;

public class Main {
	public static GameEngine gameEngine;
	public static void main (String [] args) {
		GameBoard.createGame();
		gameEngine = new GameEngine();
	}
}
