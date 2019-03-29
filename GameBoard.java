package civGame;

public class GameBoard {
	
	public static GameTile[][] gameBoard = new GameTile[5][5]; // 5x5 array that represents game board
	public static int player1Gold;
	public static int player2Gold;
	public static int maxLength[] = {0, 0, 0, 0, 0}; // maximum character length in each column
	public static int gameBoardWidth = 5;
	
	public static void createGame() {
		// Generate an array of GameTile objects that store information
		
		// Set board starting position
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				gameBoard[i][j] = new GameTile();
				gameBoard[i][j].building = " ";
				gameBoard[i][j].troops = 0;
				gameBoard[i][j].control = "NONE";
				gameBoard[i][j].canMove = true;
			}
		}
		
		// Set the starting position of both players in opposite corners
		gameBoard[0][0].control = "P1";
		gameBoard[0][0].troops = 5;
		gameBoard[4][4].control = "P2";
		gameBoard[4][4].troops = 5;
		
		player1Gold = 448;
		player2Gold = 8;
	}
	
	public static void drawGameState(String player) {
		// Method that draws the game board
		String gameBoard = "   ";
		String coordY[] = {"A", "B", "C", "D", "E"};
		
		gameBoardWidth = 5;
		maxLength = new int[5];
		// Determine the longest possible tile entry to align all tiles
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (tileDisplay(i, j).length() > maxLength[j]) {
					maxLength[j] = tileDisplay(i, j).length();
				}
			}
			gameBoardWidth += maxLength[i] + 2; // add the longest length in the column to gameBoardWidth with an addition 2 due to spacing
		}
		
		
		// Line up the numerical coordinates at the top of the game board with the width of the tiles
		for (int i = 1; i <= 5; i++) {
			// add spaces based on width in each column. coordinates are 1-5 while array is 0-4 so 1 less is used
			gameBoard += addSpaces(String.valueOf(i), i - 1);
			gameBoard += " "; // add spaces at the end as this is added when building columns
		}
		gameBoard += "\n";
		
		// loop through each line and combine each tile to create the game board
		for (int i = 0; i < 5; i++) {
			gameBoard += coordY[i] + " |";
			for (int j = 0; j < 5; j++) {
				gameBoard += drawTile(i, j) + " ";
			}
			
			// Draw the lines to the right of the board and add any information needed
			switch (i) {
			case 0:
				if (player == "P1") {
					gameBoard += "| [X] = Controlled by You \n";
				}
				else if (player == "P2") {
					gameBoard += "| (X) = Controlled by You \n";
				}
				break;
			case 1:
				if (player == "P1") {
					gameBoard += "| Gold Balance: " + player1Gold + "\n";
				}
				else if (player == "P2") {
					gameBoard += "| Gold Balance: " + player2Gold + "\n";
				}
				
				break;
			case 2:
				if (player == "P1") {
					gameBoard += "| Estimated Gold Income: " + calculateGoldIncome("P1") + "\n";
				}
				else if (player == "P2") {
					gameBoard += "| Estimated Gold Income: " + calculateGoldIncome("P2") + "\n";
				}
				break;
			default:
				gameBoard += "|\n";
				break;
			}
		}
		
		// Draw the bottom line
		gameBoard += "  ";
		for (int i = 0; i < gameBoardWidth; i++) {
			gameBoard += "—";
		}
		gameBoard += "——\nEnter a move: ";
		
		System.out.print(gameBoard);
	}
	
	public static int calculateGoldIncome(String player) {
		int goldIncome = 0;
		// If gameBoard exists, increase goldIncome by the tiles and buildings controlled by the player
		if (gameBoard != null) {
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					if (tileControl(i, j) == player) {
						if (buildingInfo(i, j) == "B" ) {
							goldIncome += GameEngine.buildingRate;
						}
						else {
							goldIncome += GameEngine.tileRate;
							if (troopCount(i,j) != 0) {
								goldIncome -= GameEngine.unitUpkeep * troopCount(i,j);
							}
						}
					}
				}
			}
			
			return goldIncome;
		}
		else {
			throw new IllegalArgumentException("Game board does not exist.");
		}
	}
	
	public static String tileControl(int x, int y) {
		// Method that returns the control status on a specific tile
		if (gameBoard[x][y] != null) {
			return gameBoard[x][y].control;
		}
		// Throw error if the tile does not exist
		else {
			throw new IllegalArgumentException("Game board does not exist.");
		}
	}
	
	public static int troopCount(int x, int y) {
		// Method that returns the troop count on a specific tile
		if (gameBoard[x][y] != null) {
			return gameBoard[x][y].troops;
		}
		// Throw error if the tile does not exist
		else {
			throw new IllegalArgumentException("Game board does not exist.");
		}
	}
	
	public static String buildingInfo(int x, int y) {
		// Method that returns the building info on a specific tile
		if (gameBoard[x][y] != null) {
			return gameBoard[x][y].building;
		}
		// Throw error if the tile does not exist
		else {
			throw new IllegalArgumentException("Game board does not exist.");
		}
	}
	
	public static String tileDisplay(int x, int y) {
		// Method that returns the symbol that should be displayed on a tile
			if (gameBoard[x][y] != null) {
				if (troopCount(x, y) == 0) {
					return buildingInfo(x, y);
				}
				else if (buildingInfo(x, y) == " ") {
					return Integer.toString(troopCount(x, y));
				}
				else {
					return buildingInfo(x, y) + Integer.toString(troopCount(x, y));
				}
			}
			// Throw error if the tile does not exist
			else {
				throw new IllegalArgumentException("Game board does not exist.");
			}
	}

	public static String drawTile(int x, int y) {
		// create the String that represents the tile based on the control
		if (gameBoard[x][y] != null) {
			switch (tileControl(x, y)) {
				case "NONE":
					return addSpaces(" - ", y);
				case "P1":
					return addSpaces("[" + tileDisplay(x,y) + "]", y);
				case "P2":
					return addSpaces("(" + tileDisplay(x,y) + ")", y);
				default:
					throw new IllegalArgumentException("Game board does not exist.");
			}
		}
		else {
			throw new IllegalArgumentException("Game board does not exist.");
		}
	}

	public static String addSpaces(String text, int column) {
		// Add spaces to the input string until it reaches the size of the longest string in the column to align
		// 2 is added because the maxLength int does not include [], () or the two spaces
		while (text.length() < maxLength[column] + 2) {
			text = " " + text;
			if (text.length() < maxLength[column] + 2) {
				text += " ";
			}
		}
		return text;
	}
}
