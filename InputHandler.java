package civGame;

import java.util.Scanner;

public class InputHandler {
	public String lastInput;
	public Scanner inputScanner = new Scanner(System.in);
	private String[] commandArray;
	
	public void handleInput(String player) {
		try {
			parseInput(inputScanner.nextLine(), player);
		}
		catch (Exception e) {
			System.out.println("error:" + e);
		}
	}
	
	public void parseInput(String input, String control) {
		commandArray = input.split(" ");
		/* Divide input into an array and separate them by spaces to allow
		 * each individual command part to be handled
		 */
		System.out.println("------------------------------------------------------");
		if (commandArray[0].equalsIgnoreCase("help")) {
			runHelpCommand();
		}
		else if (commandArray[0].equalsIgnoreCase("move") || commandArray[0].equalsIgnoreCase("mv")) {
			runMoveCommand(commandArray, control);
		}
		else if (commandArray[0].equalsIgnoreCase("build") || commandArray[0].equalsIgnoreCase("bld")) {
			runBuildCommand(commandArray, control);
		}
		else if (commandArray[0].equalsIgnoreCase("attack") || commandArray[0].equalsIgnoreCase("atk") || commandArray[0].equalsIgnoreCase("battle")) {
			runBattleCommand(commandArray, control);
		}
		else if (commandArray[0].equalsIgnoreCase("recruit") || commandArray[0].equalsIgnoreCase("hire")) {
			runRecruitCommand(commandArray, control);
		}
		else if (commandArray[0].equalsIgnoreCase("end")) {
			endTurn(control);
			GameEngine.resetBoard();
		}
		else {
			System.out.println("Invalid command. Type 'help' to get a list of commands");
		}

		System.out.println("------------------------------------------------------");
	}
	
	private void runHelpCommand() {
		// List all possible commands
		System.out.println("> List of Possible Moves:"
			+ "\n   help - lists possible moves"
			+ "\n   move [oldPosition] [newPosition] [troopCount] - moves troops from one tile to another"
			+ "\n   build [tile] - builds a building on the specified tile. A building costs " + GameEngine.buildingCost + " gold and gives " + (GameEngine.buildingRate - GameEngine.tileRate) + " more gold per turn."
			+ "\n   attack [attackingTile] [attackedTile] - attack a tile the opponent controls"
			+ "\n   recruit [tile] [troopCount] - recruits new troops to the tile. A troop costs " + GameEngine.unitCost + " gold and has an upkeep cost of " + GameEngine.unitUpkeep + " gold per turn"
			+ "\n   end - end your turn");
	}
	private void runMoveCommand(String[] commandArray, String control) {
		if (commandArray.length == 4) {
			int troopCount;
			try {
				troopCount = Integer.valueOf(commandArray[3]);
			}
			catch (Exception e) {
				System.out.println("That is not a valid number of troops.");
				return;
			}
			// Move troops from one tile to another
			GameEngine.moveUnits(commandArray[1], commandArray[2], troopCount, control);
		}
		else {
			System.out.println("That is not a valid command.");
		}
	}
	
	private void runBuildCommand(String[] commandArray, String control) {
		if (commandArray.length == 2) {
			GameEngine.placeBuilding(commandArray[1], control);
		}
		else {
			System.out.println("That is not a valid command.");
		}
	}
	private void runBattleCommand(String[] commandArray, String control) {
		if (commandArray.length == 3) {
			GameEngine.attackTile(commandArray[1], commandArray[2], control);
		}
		else {
			System.out.println("That is not a valid command.");
		}
	}
	private void runRecruitCommand(String[] commandArray, String control) {
		if (commandArray.length == 3) {
			int troopCount;
			try {
				troopCount = Integer.valueOf(commandArray[2]);
			}
			catch (Exception e) {
				System.out.println("That is not a valid number of troops.");
				return;
			}
			GameEngine.recruitUnits(commandArray[1], troopCount, control);
		}
		else {
			System.out.println("That is not a valid command.");
		}
	}
	private void endTurn(String control) {
		if (control == "P1") {
			System.out.println("Your move, Player 2.");
			if (GameEngine.checkBalance("P2", GameBoard.calculateGoldIncome("P2") * -1, true)) {
				if (GameBoard.calculateGoldIncome("P2") > 0) {
					System.out.println("You have generated " + GameBoard.calculateGoldIncome("P2") + " gold this turn." );
				}
				else {
					System.out.println("You have been charged " + GameBoard.calculateGoldIncome("P2") + " gold this turn from upkeep costs." );
				}
			}
			else {
				int troopsKilled = 0;
				while (!GameEngine.checkBalance("P2", GameBoard.calculateGoldIncome("P2") * -1, true)) {
					GameEngine.killRandom("P2");
					troopsKilled++;
				}
				System.out.println(troopsKilled + " troops have died as their upkeep cost could not be paid.");
				System.out.println("You have been charged " + GameBoard.calculateGoldIncome("P2") + " gold this turn in upkeep costs." );
			}
			GameEngine.playerTurn = "P2";
		}
		else if (control == "P2") {
			System.out.println("Your move, Player 1.");
			if (GameEngine.checkBalance("P1", GameBoard.calculateGoldIncome("P1") * -1, true)) {
				if (GameBoard.calculateGoldIncome("P1") > 0) {
					System.out.println("You have generated " + GameBoard.calculateGoldIncome("P1") + " gold this turn." );
				}
				else {
					System.out.println("You have been charged " + GameBoard.calculateGoldIncome("P1") + " gold this turn in upkeep costs." );
				}
			}
			else {
				int troopsKilled = 0;
				while (!GameEngine.checkBalance("P1", GameBoard.calculateGoldIncome("P1") * -1, true)) {
					GameEngine.killRandom("P1");
					troopsKilled++;
				}
				System.out.println(troopsKilled + " troops have died as their upkeep cost could not be paid.");
				System.out.println("You have been charged " + GameBoard.calculateGoldIncome("P1") + " gold this turn in upkeep costs." );
			}
			
			GameEngine.playerTurn = "P1";
		}
	}

}

