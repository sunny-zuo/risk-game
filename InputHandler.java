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
		else {
			System.out.println("Invalid command. Type 'help' to get a list of commands");
		}

		System.out.println("------------------------------------------------------");
	}
	
	private void runHelpCommand() {
		// List all possible commands
		System.out.println("> List of Possible Moves:"
			+ "\n   help - lists possible moves"
			+ "\n   move [oldPosition] [newPosition] [troopCount] - moves troops from one tile to another");
	}
	private void runMoveCommand(String[] commandArray) {
		// Move troops from one tile to another
		GameEngine.moveUnits(commandArray[1], commandArray[2], Integer.valueOf(commandArray[3]), "PC");
	}
}
