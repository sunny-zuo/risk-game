package civGame;

import java.util.Arrays;

public class GameEngine {
	public InputHandler inputHandler;
	
	public static int tileRate = 3; // sets gold payment per tile controlled
	public static int buildingRate = 5; // sets gold payment per building controlled
	public static int unitCost = 2; // sets cost of each unit
	public static int unitUpkeep = 1; // sets the upkeep cost of each unit per turn
	public static int buildingCost = 12; // sets cost of a building
	
	protected Boolean playerTurn = true;
	
	public GameEngine() {
		inputHandler = new InputHandler();
		runPlayerTurn();
	}
	private void runPlayerTurn() {
		GameBoard.playerGold += GameBoard.calculateGoldIncome("PC");
		while (playerTurn) {
			GameBoard.drawGameState();
			inputHandler.handleInput();
		}
	}
	private void moveUnits(String oldTile, String newTile, int troopCount, String control) {
		Coordinate oldPos = new Coordinate(oldTile);
		Coordinate newPos = new Coordinate(newTile);
		
		// Check to see if existing tile is controlled by the correct player
		if (GameBoard.gameBoard[oldPos.xPos()][oldPos.yPos()].control == control) {
			// Check to see if new tile is controlled by correct player or uncontrolled
			if (Arrays.asList(control, "NONE").contains(GameBoard.gameBoard[newPos.xPos()][newPos.yPos()].control)) {
				// Check to see if enough troops are available to be moved
				if (GameBoard.gameBoard[oldPos.xPos()][oldPos.yPos()].troops >= troopCount) {
					GameBoard.gameBoard[oldPos.xPos()][oldPos.yPos()].troops -= troopCount;
					GameBoard.gameBoard[newPos.xPos()][newPos.yPos()].troops += troopCount;
				}
				else {
					throw new IllegalArgumentException("notEnoughTroops");
				}
			}
			else {
				throw new IllegalArgumentException("mustBattle");
			}
		}
		else {
			throw new IllegalArgumentException("notControlled");
		}
	}
	
}
