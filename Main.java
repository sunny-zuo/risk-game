package civGame;

public class Main {
	public static GameEngine gameEngine;
	public static void main (String [] args) {
		GameBoard.createGame(); // Initialize game board
		gameEngine = new GameEngine(); // create a GameEngine instance to run the game
	}
}
