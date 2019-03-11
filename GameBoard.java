package civGame;

public class GameBoard {
	
	public static GameTile[][] gameBoard = new GameTile[5][5];
	
	public static void createGame() {
		// Generate an array of GameTile objects that store information
		
		// Set board starting position
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				gameBoard[i][j] = new GameTile();
				gameBoard[i][j].building = " ";
				gameBoard[i][j].troops = 0;
				gameBoard[i][j].control = 0;
				gameBoard[i][j].canMove = true;
			}
		}
		
		// Set the player and AI's starting location (two corners)
		// 0 = no control, 1 = player control, 2 = AI control

	}
	
	public static void drawGameState() {
		
	}
}
