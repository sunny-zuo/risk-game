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
			// During the player's turn, draw the game board and wait for them to enter a command
			GameBoard.drawGameState();
			inputHandler.handleInput();
		}
	}
	public static void moveUnits(String oldTile, String newTile, int troopCount, String control) {
		Coordinate oldPos;
		Coordinate newPos;
		
		try {
			// Create array coordinates based on command input
			oldPos = new Coordinate(oldTile);
			newPos = new Coordinate(newTile);
		} catch (Exception e) {
			// If invalid coordinates were entered, exit the method
			System.out.println("Invalid coordinates entered.");
			return;
		}
		// Check to see if existing tile is controlled by the correct player
		if (GameBoard.gameBoard[oldPos.xPos()][oldPos.yPos()].control == control) {
			// Check to see if new tile is controlled by correct player or uncontrolled
			if (Arrays.asList(control, "NONE").contains(GameBoard.gameBoard[newPos.xPos()][newPos.yPos()].control)) {
				// Check to see if units on that tile are able to move
				if (GameBoard.gameBoard[oldPos.xPos()][oldPos.yPos()].canMove == true) {
					// Check to see if enough troops are available to be moved
					if (GameBoard.gameBoard[oldPos.xPos()][oldPos.yPos()].troops >= troopCount) {
						GameBoard.gameBoard[oldPos.xPos()][oldPos.yPos()].troops -= troopCount;
						if (GameBoard.gameBoard[newPos.xPos()][newPos.yPos()].troops <= 0) {
							// Disallow troops from moving if colonizing a new tile
							GameBoard.gameBoard[newPos.xPos()][newPos.yPos()].canMove = false;
						}
						GameBoard.gameBoard[newPos.xPos()][newPos.yPos()].troops += troopCount;
						GameBoard.gameBoard[newPos.xPos()][newPos.yPos()].control = "PC";
						System.out.println(troopCount + " troops have been moved from " + oldTile + " to " + newTile + ".");
					}
					else {
						System.out.println("You cannot move more troops than you control.");
					}
				}
				else {
					System.out.println("You have already moved troops from that tile.");
				}
			}
			else {
				System.out.println("The tile is controlled by the opponent, so you must battle.");
			}
		}
		else {
			System.out.println("You do not control those tiles.");
		}
	}
	
	public static Boolean validateCoordinates(Coordinate newTile, String control) {
		int x = newTile.xPos();
		int y  = newTile.yPos();
		
		for (int i = -1; i < 1; i++) {
			for (int ii = -1; ii < 1; ii++) {
				if (GameBoard.gameBoard[x + i][y + i].control == control) {
					return true;
				}
			}
		}
		
		return false;
	}
}
