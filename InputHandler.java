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
			System.out.println("Please enter a valid command.");
		}
	}
	
	private void parseInput(String input) {
		commandArray = input.split(" ");
		/* Divide input into an array and separate them by spaces to allow
		 * each individual command part to be handled
		 */
		if (commandArray[0].matches("help")) {
			System.out.println("List of Possible Commands:"
			+ "\n help - lists possible commands");
		}
		else {
			System.out.println("Type 'help' to get a list of commands");
		}
	}
}
