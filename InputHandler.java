package civGame;

import java.util.Scanner;

public class InputHandler {
	public String lastInput;
	public Scanner inputScanner = new Scanner(System.in);
	private String[] commandArray;
	
	public void handleInput() {
		try {
			parseInput(inputScanner.nextLine());
		}
		catch (Exception e) {
			System.out.println("error:" + e);
		}
	}
	
	private void parseInput(String input) {
		commandArray = input.split(" ");
		/* Divide input into an array and separate them by spaces to allow
		 * each individual command part to be handled
		 */
		System.out.println("------------------------------------------------------");
		if (commandArray[0].equalsIgnoreCase("help")) {
			runHelpCommand();
		}
		else if (commandArray[0].equalsIgnoreCase("move")) {
			runMoveCommand(commandArray);
		}
		else if (commandArray[0].equalsIgnoreCase("build")) {
			runBuildCommand(commandArray);
		}
		else if (commandArray[0].equalsIgnoreCase("attack") || commandArray[0].equalsIgnoreCase("atk")) {
			runBattleCommand(commandArray);
		}
		else if (commandArray[0].equalsIgnoreCase("recruit")) {
			runRecruitCommand(commandArray);
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
			+ "\n   recruit [tile] [troopCount] - recruits new troops to the tile. A troop costs " + GameEngine.unitCost + " gold and has an upkeep cost of " + GameEngine.unitUpkeep + " gold per turn");
	}
	private void runMoveCommand(String[] commandArray) {
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
			GameEngine.moveUnits(commandArray[1], commandArray[2], troopCount, "PC");
		}
		else {
			System.out.println("That is not a valid command.");
		}
	}
	
	private void runBuildCommand(String[] commandArray) {
		if (commandArray.length == 2) {
			GameEngine.placeBuilding(commandArray[1], "PC");
		}
		else {
			System.out.println("That is not a valid command.");
		}
	}
	private void runBattleCommand(String[] commandArray) {
		if (commandArray.length == 3) {
			GameEngine.attackTile(commandArray[1], commandArray[2], "PC");
		}
		else {
			System.out.println("That is not a valid command.");
		}
	}
	private void runRecruitCommand(String[] commandArray) {
		if (commandArray.length == 3) {
			int troopCount;
			try {
				troopCount = Integer.valueOf(commandArray[2]);
			}
			catch (Exception e) {
				System.out.println("That is not a valid number of troops.");
				return;
			}
			GameEngine.recruitUnits(commandArray[1], troopCount, "PC");
		}
		else {
			System.out.println("That is not a valid command.");
		}
	}

}

