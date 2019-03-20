package civGame;

final class Coordinate {
	private int x;
	private int y;
	
	final static String alphabet = "abcdefghijklmnopqrstuvwxyz";
	
	public Coordinate(String testCoord) {
		// Check to ensure the coordinates are in proper format (letter A-E and number 1-5)
		if (checkValidCoordinate(testCoord)) {
			// Determine the position of the alphabet the first character is located in
			x = alphabet.indexOf(Character.toLowerCase(testCoord.charAt(0)));
			y = (testCoord.charAt(1) - '0') - 1;
		}
		else {
			throw new IllegalArgumentException("Incorrect coordinate format");
		}
	}
	
	public int xPos() { // method that returns the x value in a coordinate set
		return x;
	}
	public int yPos() { // method that returns the y value in a coordinate set
		return y;
	}
	
	private Boolean checkValidCoordinate(String testCoord) {
		// Validate that the first character is A-E or a-e
		if ((testCoord.charAt(0) >= 'a' && testCoord.charAt(0) <= 'e') || (testCoord.charAt(0) >= 'A' && testCoord.charAt(0) <= 'E')) {
			// and validate that the second character is 1-5
			if (testCoord.charAt(1) >= '1' && testCoord.charAt(1) <= '5') {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
}
