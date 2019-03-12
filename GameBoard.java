package civGame;

public class GameBoard {
	
	public static GameTile[][] gameBoard = new GameTile[5][5];
	public static int playerGold;
	
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
		
		// Set the player and AI's starting location (two corners)
		// 0 = no control, 1 = player control, 2 = AI control
		gameBoard[0][4].control = "PC";
		gameBoard[0][4].troops = 5;
		gameBoard[4][0].control = "NPC";
		gameBoard[4][0].troops = 5;
		
		System.out.println(drawGameState());

	}
	
	public static String drawGameState() {
		// Method that draws the game board
		String gameBoard = "    1   2   3   4   5  \n";
		String coordY[] = {"A", "B", "C", "D", "E"};
		
		for (int i = 0; i < 5; i++) {
			gameBoard += coordY[i] + " |";
			for (int j = 0; j < 5; j++) {
				gameBoard += drawTile(i, j) + " ";
			}
			gameBoard += "\n";
		}
		
		return gameBoard;
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
				else {
					return Integer.toString(troopCount(x, y));
				}
			}
			// Throw error if the tile does not exist
			else {
				throw new IllegalArgumentException("Game board does not exist.");
			}
	}

	public static String drawTile(int x, int y) {
		if (gameBoard[x][y] != null) {
			switch (tileControl(x, y)) {
				case "NONE":
					return " - ";
				case "PC":
					return "[" + tileDisplay(x,y) + "]";
				case "NPC":
					return "(" + tileDisplay(x,y) + ")";
				default:
					throw new IllegalArgumentException("Game board does not exist.");
			}
		}
		else {
			throw new IllegalArgumentException("Game board does not exist.");
		}
	}
}
