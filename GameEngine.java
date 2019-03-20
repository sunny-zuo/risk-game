package civGame;

import java.util.Arrays;

public class GameEngine {
	public InputHandler inputHandler;
	
	public static int tileRate = 2; // sets gold payment per tile controlled
	public static int buildingRate = 5; // sets gold payment per building controlled
	public static int unitCost = 2; // sets cost of each unit
	public static int unitUpkeep = 1; // sets the upkeep cost of each unit per turn
	public static int buildingCost = 14; // sets cost of a building
	
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
		// Validate that the user is moving to an adjacent tile
		if (!validateCoordinates(newPos, "PC")) {
			System.out.println("You can only move to tiles adjacent to ones you control.");
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
					System.out.println("You cannot move troops from that tile this turn.");
				}
			}
			else {
				System.out.println("The tile is controlled by the opponent, so you must battle.");
			}
		}
		else {
			System.out.println("You do not control the tile you are moving from.");
		}
	}
	
	public static void placeBuilding(String inputTile, String control) {
		Coordinate tile = new Coordinate(inputTile);
		
		// Verify the player has enough gold for a building
		if (GameBoard.playerGold < GameEngine.buildingCost) {
			System.out.println("You do not have enough gold to purchase the building.");
			return;
		}
		
		// Verify player controls the tile they are building on
		if (GameBoard.gameBoard[tile.xPos()] [tile.yPos()].control == control) {
			// Verify there is no building already present
			if (GameBoard.gameBoard[tile.xPos()][tile.yPos()].building == " ") {
				if (GameBoard.gameBoard[tile.xPos()][tile.yPos()].troops <= 0) {
					GameBoard.playerGold -= GameEngine.buildingCost;
					GameBoard.gameBoard[tile.xPos()][tile.yPos()].building = "B";
					System.out.println("A building has been placed on tile " + inputTile);
				}
				else {
					System.out.println("You can not place a building on a tile where troops are located.");
				}
			}
			else {
				System.out.println("There is already a building on that tile.");
			}
		}
		else {
			System.out.println("You do not control that tile.");
		}
			
	}
	public static Boolean validateCoordinates(Coordinate newTile, String control) {
		int x = newTile.xPos();
		int y  = newTile.yPos();
		
		// Check to see if any of the surrounding tiles are controlled by the user
		// For loops to check the 3x3 grid around the single tile
		for (int i = -1; i <= 1; i++) {
			for (int ii = -1; ii <= 1; ii++) {
				try {
					if (GameBoard.gameBoard[x + i][y + ii].control == control) {
						// Only allow movement if an adjacent tile is controlled by the player
						return true;
					}
				} catch (Exception e) {
					// do nothing
				}
			}
		}
		// Disallow movement
		return false;
	}
}
