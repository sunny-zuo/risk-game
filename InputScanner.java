package civGame;

import java.util.Scanner;

public class InputScanner {
	public static Scanner inputScanner;
	
	public static void main(String[] args) {
		inputScanner = new Scanner(System.in); 
	}
	
	public static String nextLine() {
		return inputScanner.nextLine();
	}
	
	public static int nextInt() {
		return inputScanner.nextInt();
	}
}
