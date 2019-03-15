package civGame;

public class GameEngine {
	public InputHandler inputHandler;
	
	public static int tileRate = 1; // sets gold payment per tile controlled
	public static int buildingRate = 3; // sets gold payment per building controlled
	public static int unitCost = 2; // sets cost of each unit
	public static int buildingCost = 10; // sets cost of a building
	
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
