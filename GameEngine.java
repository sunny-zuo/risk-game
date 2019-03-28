package civGame;

import java.util.Arrays;

public class GameEngine {
	public InputHandler inputHandler;
	
	public static int tileRate = 2; // sets gold payment per tile controlled
	public static int buildingRate = 6; // sets gold payment per building controlled
	public static int unitCost = 3; // sets cost of each unit
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
		Coordinate tile;
		// Verify the coordinate is valid
		try {
			tile = new Coordinate(inputTile);
		}
		catch (Exception e) {
			System.out.println("Incorrect coordinates entered.");
			return;
		}
		
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
	
	public static void attackTile(String oldTile, String newTile, String control) {
		Coordinate oldPos;
		Coordinate newPos;
		int atkTroops;
		int defTroops;
		
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
		if (!validateCoordinates(newPos, control)) {
			System.out.println("You can only attack tiles adjacent to ones you control.");
			return;
		}
		// Validate that the user is attacking a tile controlled by the opponent
		if (GameBoard.tileControl(newPos.xPos(), newPos.yPos()) == "NONE") {
			System.out.println("You cannot attack tiles that are unclaimed.");
			return;
		}
		// Validate that the user is attacking from a tile they control
		if (GameBoard.tileControl(oldPos.xPos(), oldPos.yPos()) != control) {
			System.out.println("You must attack from tiles you control.");
			return;
		}
		// Validate that the user is not attacking themselves
		if (GameBoard.tileControl(newPos.xPos(), newPos.yPos()) == control) {
			System.out.println("You cannot attack yourself.");
			return;
		}
		
		atkTroops = GameBoard.troopCount(oldPos.xPos(), oldPos.yPos());
		defTroops = GameBoard.troopCount(newPos.xPos(), newPos.yPos());
		
		if (atkTroops < 2) {
			System.out.println("You must have at least two troops to attack");
			return;
		}
		
		while (atkTroops > 1 && defTroops > 0) {
			// Roll up to 3 dice for the attacker and store it in an array
			// 1 dice is used for each attacker unit, up to a maximum of 3 per battle. The attacker must have a minimum of 1 troop leftover.
			// This implements the same system used in Risk.
			int atkRolls[] = new int[3];
			for (int i = 0; i < Math.min(atkTroops - 1, 3); i++) {
				atkRolls[i] = (int) Math.ceil(Math.random() * 6);
			}
			
			// Sort the array in descending order
			Arrays.sort(atkRolls); // sorts in ascending order
			int temp = atkRolls[2]; // switch the first and third value
			atkRolls[2] = atkRolls[0];
			atkRolls[0] = temp;
			
			// Roll up to 2 dice for the defender and store it in an array
			int defRolls[] = new int[2];
			for (int i = 0; i < Math.min(defTroops, 2); i++) {
				defRolls[i] = (int) Math.ceil(Math.random() * 6);
			}
			if (defRolls[1] > defRolls[0]) { // sort the rolls in descending order
				int temp1 = defRolls[0];
				defRolls[0] = defRolls[1];
				defRolls[1] = temp1;
			}
			
			// System.out.println("atk rolls: " + atkRolls[0] + atkRolls[1] + atkRolls[2]);
			// System.out.println("def rolls: " + defRolls[0] + defRolls[1]);
			
			if (atkRolls[0] > defRolls[0]) { // if attacker rolled higher than defending, they win
				defTroops -= 1;
			}
			else { // else defender wins
				atkTroops -= 1;
			}
			
			if (atkTroops > 1 && defTroops > 0) { // if attacker has troops to attack with and defender has troops to defend with, do second battle
				if (atkRolls[1] > defRolls[1]) {
					defTroops -= 1;
				}
				else {
					atkTroops -= 1;
				}
			}
		}
		
		if (atkTroops > 1) {
			System.out.println("You won the battle for " + newTile + " and killed " + (GameBoard.troopCount(newPos.xPos(), newPos.yPos()) - defTroops) 
					+ " troops, losing " + (GameBoard.troopCount(oldPos.xPos(), oldPos.yPos()) - atkTroops) + " troops in the process.");
			// Set new game board status and force the player to move the lesser of 3 troops or the troops they control minus 1 to the new tile
			GameBoard.gameBoard[oldPos.xPos()][oldPos.yPos()].troops = (atkTroops - Math.min(3, atkTroops - 1));
			GameBoard.gameBoard[newPos.xPos()][newPos.yPos()].troops = Math.min(3, atkTroops - 1);
			GameBoard.gameBoard[newPos.xPos()][newPos.yPos()].control = control;
		}
		else {
			System.out.println("You lost the battle for " + newTile + " and killed " + (GameBoard.troopCount(newPos.xPos(), newPos.yPos()) - defTroops) 
					+ " troops, losing " + (GameBoard.troopCount(oldPos.xPos(), oldPos.yPos()) - atkTroops) + " troops in the process.");
			// Set each tile to the number of troops remaining. No change of control as attacker was unsuccessful.
			GameBoard.gameBoard[oldPos.xPos()][oldPos.yPos()].troops = atkTroops;
			GameBoard.gameBoard[newPos.xPos()][newPos.yPos()].troops = defTroops;
		}

	}
	
	public static void recruitUnits(String inputTile, int troopCount, String control) {
		Coordinate tile;
		// Verify the coordinate is valid
		try {
			tile = new Coordinate(inputTile);
		}
		catch (Exception e) {
			System.out.println("Incorrect coordinates entered.");
			return;
		}
		// Verify the player is adding troops to tiles they control
		if (GameBoard.tileControl(tile.xPos(), tile.yPos()) != control) {
			System.out.println("You can only recruit units to tiles you control.");
			return;
		}
		// Verify the player can afford the troops
		if (GameBoard.playerGold >= troopCount * GameEngine.unitCost) {
			GameBoard.playerGold -= troopCount * GameEngine.unitCost;
			GameBoard.gameBoard[tile.xPos()][tile.yPos()].troops += troopCount;
			System.out.println(troopCount + " troops have been recruited to tile " + inputTile + ".");
		}
		else {
			System.out.println("You do not have enough gold to recruit " + troopCount + " units.");
		}
		
		
	}
	public static Boolean validateCoordinates(Coordinate newTile, String control) {
		int x = newTile.xPos();
		int y  = newTile.yPos();
		
		// Check to see if any of the surrounding tiles are controlled by the user
		// Nested loops to check the 3x3 grid around the single tile
		for (int i = -1; i <= 1; i++) {
			for (int ii = -1; ii <= 1; ii++) {
				try {
					if (GameBoard.gameBoard[x + i][y + ii].control == control) {
						// Only allow movement if an adjacent tile is controlled by the player
						return true;
					}
				} catch (Exception e) {
					// do nothing if tile is invalid (such as -1, -1)
				}
			}
		}
		// Disallow movement
		return false;
	}
}
