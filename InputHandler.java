package civGame;

public class InputHandler {
	public static String lastInput;
	public static void handleInput() {
		try {
			lastInput = InputScanner.nextLine();
			System.out.println(lastInput);
		}
		catch (Exception e) {
			System.out.println("Please enter a valid command.");
		}
	}
}
