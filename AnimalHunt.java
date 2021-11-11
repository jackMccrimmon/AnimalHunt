/*
 * @author: John McCrimmon
 * @date: 12/5/2018
 * 
 * Description: This is a game where you can create animals and explore for them.
 * 
 * Pseudocode:
 * get length and with of world array
 * declare all arrays
 * initialize 0,0 for both world and exploreWorld for X and true
 * fill the rest of the arrays with values
 * print main menu
 * based on input either insert an animal, remove an animal, or move on to the explore part
 * if insert
 * 		get name and position of animal and set its location in animalLocations
 * if remove
 * 		get position of animal to be removed, determine if an animal is at inputed location
 * 		if so remove it, if not display message
 * if explore
 * 		print the world array with X at 0,0
 * 		print move menu
 * 			set current position to true in exploredWorld array
 * 			depending on movement selected add or subtract from x and y value
 * 			call move
 * 				call makeWorld (which will re-make the array to include where you will be after move and where you have already explored)
 * 				will determine if an animal was encountered
 * 			if i was selected
 * 				output the list of animals found while playing the game
 * exit
 * 
 */
import java.util.Scanner;

public class Assignment4 {
	
	static boolean[][] exploredWorld; //Global array

	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
		
		int choice = 0;
		
		//Prints a welcome message and rules of game
		System.out.println("Welcome to the jungle creator!\nIn this game, you will be inserting animals in certain places "
				+ "in the world\nthat you create! You will also be allowed to remove animals from certain locations before you start "
				+ "exploring!\nOnce you start exploring you will navigate around the world to observe the animals you have inserted.\nThe game "
				+ "will keep track of all the animals you have observed!");
		System.out.println("\nEnter the dimensions of your jungle !");
		System.out.print("Enter length: ");
		int length = input.nextInt();
		System.out.print("Enter width: ");
		int width = input.nextInt();
		
		exploredWorld = new boolean[length][width];
		String[][] world = new String[length][width];
		String[][] animalLocations = new String[length][width];
		String[] observedAnimals = new String[length*width];
		
		exploredWorld [0][0] = true;//true because this is where you start
		for(int rows = 1; rows < exploredWorld.length - 1; rows++) {
			for(int columns = 1; columns < exploredWorld[0].length - 1; columns++) {
				
				exploredWorld [rows][columns] = false; //fills the array with false to save unexplored areas
				
			}//end inner for loop
		}//end outer for loop
		
		
		initialize(world, animalLocations);

		do {
			printMainMenu(); //method that displays the main menu
			choice = input.nextInt(); //records the users choice
			
			switch(choice) {
			case 1:
				insertAnimalToWorld(animalLocations);
				break;
			case 2:
				removeAnimalFromWorld(animalLocations);
				break;
			case 3:
				explore(world, animalLocations, observedAnimals);
				break;
			default:
				System.out.println("\nInvaild Input");
			}
			
		}while(choice != 3);
		
		input.close();

	}//end main
	
	//This method is used to initialize the arrays world and exploredWorld
	public static void initialize(String[][] world, String[][] animalLocations) {

		for(int rows = 0; rows < world.length; rows++) {
			for(int columns = 0; columns < world[0].length; columns++) {
				
				world [rows][columns] = "* "; //fills the array with * to symbolize unexplored areas
				
			}//end inner for loop
		}//end outer for loop
		
		world [0][0] = "X "; //makes the starting point always 0,0
		
		for(int rows = 0; rows < animalLocations.length; rows++) {
			for(int columns = 0; columns < animalLocations[0].length; columns++) {
				
				animalLocations [rows][columns] = ""; //fills the array with ""
				
			}//end inner for loop
		}//end outer for loop
		
	}//end initialize
	
	public static String[][] makeWorld(String[][] world, int posX, int posY){
	
		for(int rows = 0; rows < world.length; rows++) {
			for(int columns = 0; columns < world[0].length; columns++) {
				
				if(isExplored(columns, rows)) 
					world[rows][columns] = "0 ";
				
				else
					world [rows][columns] = "* ";
				
			}//end inner for loop
		}//end outer for loop
		
		world[posY][posX] = "X ";
		
		return world;
	}//end makeWorld
	
	//Displays the main menu for the game
	public static void printMainMenu() {
		System.out.println("\n1. Insert an animal into the world\n2. Remove an animal from the world\n3. Explore the world");
		System.out.print("Enter your choice: ");
	}//end printMainMenu
	
	//Displays the menu for movement of game
	public static void printMoveMenu() {
		System.out.println("\nW. Move Foward\nA. Move Left\nS. Move Backward\nD. Move Right\nI. Display observed animals\nE. Exit");
		System.out.print("Enter your choice: ");
	}//end printMoveMenu
	
	//outputs the world array showing where the user is and where they have been
	public static void printWorld(String[][] world) {
		for (int i = 0; i < world.length; i++){
			for (String s : world[i]) {
				System.out.print(s + " ");
			}
			
		System.out.println();
		}
		
	}//end printWorld
	
	public static void insertAnimalToWorld(String[][] animalLocations) {
		
		Scanner sc = new Scanner(System.in);
		boolean exitLoop = false;
		
		do {
			System.out.print("\nEnter the name of the animal you would like to insert: ");
			String name = sc.next();
			System.out.print("Enter the x coordinate of your animal: ");
			int y = sc.nextInt();
			System.out.print("Enter the y coordinate of your animal: ");
			int x = sc.nextInt();
			
			if(animalLocations[x][y].equals("")) {
				animalLocations[x][y] = name;
				exitLoop = true;
			}
			else
				System.out.println("\nThere is already an animal at this location");
				
		}while(exitLoop != true);
		
	}//end insertAnimal
	
	public static void removeAnimalFromWorld(String[][] animalLocations) {
		
		Scanner input = new Scanner(System.in);

		System.out.print("\nEnter the x coordinate of the animal you want to remove: ");
		int y = input.nextInt();
		System.out.print("Enter the y coordinate of the animal you want to remove: ");
		int x = input.nextInt();
			
		if((x < animalLocations.length && x > 0) && (y < animalLocations.length && y > 0)) {
			boolean empty = isEmptyBlock(animalLocations, x, y);
			
			if(empty) {
				animalLocations[x][y] = "";
				System.out.println("\nThe animal was removed");
			}
			else
				System.out.println("\nSorry, you cannot remove that animal because the block is empty");
		}
		else
			System.out.println("\nOut of bounds!");
		
	}//end removeAnimalFromWorld
	
	public static boolean isEmptyBlock(String[][] animalLocations, int x, int y) {
		
		if(!(animalLocations[x][y].equals(""))) {
			return true;
		}
		
		return false;
	}
	
	public static void explore(String[][] world, String[][] animalLocations, String[] observedAnimals) {
		Scanner input = new Scanner(System.in);
		String choice = "";
		int x = 0;
		int y = 0;
		System.out.println();
		
		do{
		
			printWorld(world);
			printMoveMenu();
			choice = input.next();
			choice = choice.toUpperCase();
			switch(choice) {
			case "W":
				if(y >= 0 && y + 1 < world.length) {
					exploredWorld[x][y] = true;
					y -= 1;
					move(world, x, y, observedAnimals, animalLocations);
				}
				else {
					System.out.println("You went off of bounds! Resetting the position to 0,0...");
					exploredWorld[x][y] = true;
					y = 0; x = 0;
					move(world, x, y, observedAnimals, animalLocations);
				}
					
				break;
			case "A":
				if(x >= 0 && x + 1 < world.length) {
					exploredWorld[x][y] = true;
					x -= 1;
					move(world, x, y, observedAnimals, animalLocations);
				}
				else {
					System.out.println("You went off of bounds! Resetting the position to 0,0...");
					exploredWorld[x][y] = true;
					y = 0; x = 0;
					move(world, x, y, observedAnimals, animalLocations);
				}
				
				break;
			case "S":
				if(y >= 0 && y + 1 < world.length) {
					exploredWorld[x][y] = true;
					y += 1;
					move(world, x, y, observedAnimals, animalLocations);
				}
				else {
					System.out.println("You went off of bounds! Resetting the position to 0,0...");
					exploredWorld[x][y] = true;
					y = 0; x = 0;
					move(world, x, y, observedAnimals, animalLocations);
				}
				
				break;
			case "D":
				if(x >= 0 && x + 1 < world.length) {
					exploredWorld[x][y] = true;
					x += 1;
					move(world, x, y, observedAnimals, animalLocations);
				}
				else {
					System.out.println("You went off of bounds! Resetting the position to 0,0...");
					exploredWorld[x][y] = true;
					y = 0; x = 0;
					move(world, x, y, observedAnimals, animalLocations);
				}
				
				break;
			case "I":
				System.out.println("\nPrinting current observed Animals...");
				printObservedAnimals(observedAnimals);
				break;
			case "E":
				System.out.println("\nExiting World...");
				break;
			default:
				System.out.println("\nInvaild Input");
			}
		}while(!(choice.equals("E")));
		
		input.close();
	}//end explore
	
	public static String[][] move(String[][] world, int x, int y, String[] observedAnimals, String[][] animalLocations) {
		
		makeWorld(world, x, y);
		
		if(!(animalLocations[x][y].equals(""))) {
			System.out.println("You have encountered a/an " + animalLocations[x][y] + "!\n");
			String animal = animalLocations[x][y];
			updateObservedAnimals(observedAnimals, animal);
			
		}
		else
			System.out.println("You have not encountered any animals :(\n");
		return world;
	}
	
	public static void printObservedAnimals(String[] observedAnimals) {
		for (int i=0;i<observedAnimals.length;i++){
					
			if (observedAnimals[i] != null)
				System.out.print(observedAnimals[i] + " ");
		}
		System.out.println("");
	}//end printObservedAnimals

	public static boolean isExplored(int x, int y) {

		return exploredWorld[x][y];

	}//end isExplored
	
	static int counter = 0;
	public static String[] updateObservedAnimals(String[] observedAnimals, String animal) {

		observedAnimals[counter] = animal;
		counter++;
		
		return observedAnimals;
	}
}//end class
