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
		System.out.println(commandArray[0]);
	}
}
