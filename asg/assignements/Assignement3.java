import java.util.Random;
import java.util.stream.*;
import java.util.Scanner;

public class Assignement3 {
    public static void main(String[] args){

        if (args[0].equals("Question1")){
            A3Question1 q = new A3Question1();
            q.process();
        } else if (args[0].equals("Question2")) {
            A3Question2 q = new A3Question2();
            q.process();
        } else if (args[0].equals("Question3")) {
            A3Question3 q = new A3Question3();
            q.process();
        }
    }
}


class A3Question1{
    public static void process(){
        // Print header
        String dashes = new String(new char[20]).replace("\0", "-");
        String spaces = new String(new char[6]).replace("\0", " ");
        String pointRight = new String(new char[4]).replace("\0", ">");
        String pointLeft = new String(new char[4]).replace("\0", "<");

        System.out.println("|" + dashes + pointRight + pointLeft + dashes + "|");
        System.out.println("|" + spaces + "Welcome to the Simplified Dart Game!" + spaces + "|");
        System.out.println("|" + dashes + pointRight + pointLeft + dashes + "|");

        // Initiate random object
        Random rand = new Random();
        System.out.println(Math.abs(rand.nextInt()%10));
        int points = 0;
        int regions[] = new int[10];
        // Shoot while points under 1000;
        while (listSum(regions) < 1000){
            regions[Math.abs(rand.nextInt()%10)]++;
        }
        // Print simulation results
        System.out.println("Region   Hits    Points");
        System.out.println(dashes + dashes);

        for (int i = 0; i < regions.length; i++){
            System.out.printf("%2d%10d%10d%n", i+1, regions[i], regions[i] * parsePoints(i));
        }
        System.out.println("\nIt took " + IntStream.of(regions).sum() + " tosses for a total of " + listSum(regions));
        System.out.println("\nThat was an effortless game of darts!");
    }

    private static int listSum(int list[]){
        int sum = 0;

        for (int i = 0; i < list.length; i++){
            sum += list[i] * parsePoints(i);
        }

        return sum;
    }

    private static int parsePoints(int region){
        switch (region){
            case 0 : return 7;
            case 1 : ;
            case 2 : ;
            case 3 : return 5;
            case 4 : ;
            case 5 : ;
            case 6 : return 3;
            case 7 : ;
            case 8 : ;
            case 9 : return 1;
            default: return 0;
        }
    }
}
class A3Question2{
    public static void process(){
		// Print header 
		String dashes = new String(new char[7]).replace("\0", "-");
		String stars = new String(new char[4]).replace("\0", "*");
		
		System.out.println("|" + dashes + stars + dashes + stars + dashes + stars + dashes + stars + dashes + stars + dashes + "|");
		System.out.println("|      Welcome to the Decorated Square Drawing Program!        |");
		System.out.println("|" + dashes + stars + dashes + stars + dashes + stars + dashes + stars + dashes + stars + dashes + "|");
		
		// Print menu 
		System.out.println("\nWhat type of square would you like?");
		System.out.printf("%9s%-9s%n", " ", "1  - Full square");
		System.out.printf("%9s%-9s%n", " ", "2  - Hollow square");
		System.out.printf("%9s%-9s%n", " ", "3  - An X");
		System.out.printf("%9s%-9s%n", " ", "4  - Horizontal Bars");
		System.out.printf("%9s%-9s%n", " ", "5  - Vertical Bars");
		System.out.printf("%9s%-9s%n", " ", "6  - Diagonal Bars");
		System.out.printf("%9s%-9s%n", " ", "7  - Integer Filled Square");
		System.out.printf("%9s%-9s%n", " ", "8  - Checkered (must be a multiple of 4)");
		System.out.printf("%9s%-9s%n", " ", "9  - Quit");
		
		// Read user input
		Scanner reader = new Scanner(System.in);
		int choice = 1;
		
		while(choice <= 9 && choice >= 1){
			System.out.print("\nEnter your choice (1 to 9):");
			choice = reader.nextInt();
			// Switch between choices
			switch(choice){
				case 1 : full(); break;
				case 2 : hollow(); break;
				case 3 : anX(); break;
				case 4 : horBars(); break;
				case 5 : verBars(); break;
				case 6 : diaBars(); break;
				case 7 : intFilled(); break;
				case 8 : checkered(); break;
				case 9 : choice = 10; break;
				default : System.out.println("Sorry! That is an illegal choice"); choice = 1;
 			}
		}
		System.out.println("\nHope you enjoyed your pattern!! Come back soon ...");
    }
	
	private static void full(){
		Scanner reader = new Scanner(System.in);
		System.out.print("How many rows and columns(min 4 & max 20)? "); 
		int size = reader.nextInt();
		System.out.print("Which character do you want to fill your square with?? ");
		char car = reader.next().charAt(0);
		
		System.out.print("\nHere is your pattern\n\n");
		for (int i = 0; i < size; i++){
			System.out.print(" ");
			for (int j = 0; j < size; j++){
				System.out.print(car + " ");
			}
			System.out.print("\n");
		}
	}
	
	private static void hollow(){
		Scanner reader = new Scanner(System.in);
		System.out.print("How many rows and columns(min 4 & max 20)? "); 
		int size = reader.nextInt();
		System.out.print("Which character do you want for the border? ");
		char car = reader.next().charAt(0);
		
		System.out.print("\nHere is your pattern\n\n");
		for (int i = 0; i < size; i++){
			System.out.print(" ");
			for (int j = 0; j < size; j++){
				if( i == 0 || i == size-1 || j == 0 || j == size-1){
					System.out.print(car + " ");
				}else{
					System.out.print(" ");
				}
			}
			System.out.print("\n");
		}
	}
	private static void anX(){
		Scanner reader = new Scanner(System.in);
		System.out.print("How many rows and columns(min 4 & max 20)? "); 
		int size = reader.nextInt();
		System.out.print("Which character do you want for the X? ");
		char car = reader.next().charAt(0);
		System.out.print("Which character do you want around the X? ");
		char car2 = reader.next().charAt(0);
		
		System.out.print("\nHere is your pattern\n\n");
		for (int i = 0; i < size; i++){
			System.out.print(" ");
			for (int j = 0; j < size; j++){
				if( i == j || i == size-j-1 ){
					System.out.print(car + " ");
				}else{
					System.out.print(car2 + " ");
				}
			}
			System.out.print("\n");
		}
	}
	private static void horBars(){
		Scanner reader = new Scanner(System.in);
		System.out.print("How many rows and columns(min 4 & max 20)? "); 
		int size = reader.nextInt();
		System.out.print("Which character do you want for the even rows? ");
		char car = reader.next().charAt(0);
		System.out.print("Which character do you want for the odd rows? ");
		char car2 = reader.next().charAt(0);
		
		System.out.print("\nHere is your pattern\n\n");
		for (int i = 0; i < size; i++){
			System.out.print(" ");
			for (int j = 0; j < size; j++){
				if( i%2 == 0){
					System.out.print(car2 + " ");
				}else{
					System.out.print(car + " ");
				}
			}
			System.out.print("\n");
		}
	}
	private static void verBars(){
		Scanner reader = new Scanner(System.in);
		System.out.print("How many rows and columns(min 4 & max 20)? "); 
		int size = reader.nextInt();
		System.out.print("Which character do you want for the even columns? ");
		char car = reader.next().charAt(0);
		System.out.print("Which character do you want for the odd columns? ");
		char car2 = reader.next().charAt(0);
		
		System.out.print("\nHere is your pattern\n\n");
		for (int i = 0; i < size; i++){
			System.out.print(" ");
			for (int j = 0; j < size; j++){
				if( j%2 == 0){
					System.out.print(car2 + " ");
				}else{
					System.out.print(car + " ");
				}
			}
			System.out.print("\n");
		}
	}
	private static void diaBars(){
		Scanner reader = new Scanner(System.in);
		System.out.print("How many rows and columns(min 4 & max 20)? "); 
		int size = reader.nextInt();
		System.out.print("Which character do you want for the even diagonals? ");
		char car = reader.next().charAt(0);
		System.out.print("Which character do you want for the odd diagonals? ");
		char car2 = reader.next().charAt(0);
		
		System.out.print("\nHere is your pattern\n\n");
		for (int i = 0; i < size; i++){
			System.out.print(" ");
			for (int j = 0; j < size; j++){
				if( i%2 == 0){
					if( j%2 == 0){
						System.out.print(car + " ");
					}
					else{
						System.out.print(car2 + " ");
					}
				}else{
					if( j%2 == 0){
						System.out.print(car2 + " ");
					}
					else{
						System.out.print(car + " ");	
					}					
				}
			}
			System.out.print("\n");
		}
	}
	private static void intFilled(){
		Scanner reader = new Scanner(System.in);
		System.out.print("How many rows and columns(min 4 & max 20)? "); 
		int size = reader.nextInt();
		System.out.print("What is the starting number for your integer filled square (between 0 and 50 inclusive): ");
		int start = reader.nextInt();
		
		System.out.print("\nHere is your pattern\n\n");
		for (int i = 0; i < size; i++){
			System.out.print(" ");
			for (int j = 0; j < size; j++){
				if( i >= j){
					System.out.printf("%3d%1s", (start + i - j + size * j), " ");
				}else{
					System.out.printf("%3d%1s", start, " ");
				}
			}
			System.out.print("\n");
		}
	}
	private static void checkered(){
		Scanner reader = new Scanner(System.in);
		System.out.print("How many rows and columns (max 20 and multiple of 4)? "); 
		int size = reader.nextInt();
		while(size%4 != 0){
			System.out.print("Invalid input -- How many rows and columns (max 20 and multiple of 4)? "); 
			size = reader.nextInt();
		}
		System.out.print("Which character do you want for the 1st checker? ");
		char car = reader.next().charAt(0);
		System.out.print("Which character do you want for the 2nd checker? ");
		char car2 = reader.next().charAt(0);
		
		System.out.print("\nHere is your pattern\n\n");
		for (int i = 0; i < size; i++){
			System.out.print(" ");
			for (int j = 0; j < size; j++){
				if( i%4 < 2 ){
					if( j%4 < 2 ){
						System.out.print(car2 + " ");
					}
					else{
						System.out.print(car + " ");
					}
				}else{
					if( j%4 < 2){
						System.out.print(car + " ");
					}
					else{
						System.out.print(car2 + " ");	
					}					
				}
			}
			System.out.print("\n");
		}
	}
}
class A3Question3{
    public static void process(){

    }
}