import java.util.Scanner;

public class Assignement2 {
    public static void main(String[] args){

        if (args[0].equals("Question1")){
            A2Question1 q = new A2Question1();
            q.process();
        } else if (args[0].equals("Question2")) {
            A2Question2 q = new A2Question2();
            q.process();
        } else if (args[0].equals("Question3")) {
            A2Question3 q = new A2Question3();
            q.process();
        }
    }
}

class A2Question1 {

    public static void process(){
       String stars = new String(new char[4]).replace("\0", "*");
       String dashes = new String(new char[7]).replace("\0", "-");
       String spaces1 = new String(new char[dashes.length()]).replace("\0", " ");
       String spaces2 = new String(new char[dashes.length() + "Welcome ".length()]).replace("\0", " ");

       // Display header of console
       System.out.println(dashes + stars + dashes + stars + dashes + stars + dashes + stars + dashes + stars + dashes);
       System.out.println(spaces1 + "Welcome to the Fine and Demerit Point Evaluator!");
       System.out.println(spaces2 + "based on the Crazy Nancy's Criteria");
       System.out.println(dashes + stars + dashes + stars + dashes + stars + dashes + stars + dashes + stars + dashes);

       // Write question to user
       System.out.println("Welcom Officer - I need some information before I tell you what the fine and the demerit" +
                          "points are.\nHere are the possible locations");
       System.out.println(spaces1 + "1 - Driver was stopped on the highway");
       System.out.println(spaces1 + "2 - In the school zone");
       System.out.println(spaces1 + "3 - Car was stopped at a Stop  sign or traffic light");
       System.out.println(spaces1 + "4 - None of the above\n");

       System.out.print("Please enter th digit corresponding to your case : ");
       Scanner reader = new Scanner(System.in);
       int caseDigit = reader.nextInt();
       int demeritPoints;

       switch (caseDigit){
           case 1 : System.out.print("\nOfficer is this the driver's first offence (answer with y for yes and anything" +
                                     "else for no)?");
                    String firstOffence = reader.next();
                    System.out.print("Last question officer! How many demerit points did the driver have prior" +
                                     "to being stopped?");
                    if (firstOffence.equals("y")){
                        demeritPoints = reader.nextInt() + 1;
                    } else {
                        demeritPoints = reader.nextInt() + 2;
                    }

                    System.out.println("\n--> Write a ticket for $80 and inform the driver that they now have " +
                                       demeritPoints + " demerit points");
                    break;
           case 2 : System.out.print("Officer, how many months has the driver been driving? ");
                    int months = reader.nextInt();
                    if (months < 24){
                        System.out.println("\n--> Officer, write a ticket for $100, take away their driver's license" +
                                "and make arrangements to have the car towed right away.");
                    } else {
                        System.out.print("Last question officer! How many demerit points did the driver have prior" +
                                "to being stopped");
                        demeritPoints = reader.nextInt() + 4;
                        System.out.println("\n--> Write a ticket for $100 and inform the driver that they now have " +
                                           demeritPoints + " demerit points.");
                    }
                    break;
           case 3: System.out.print("Officer, is the cellphone in question an iPhone (answer with y for yes and " +
                                    "anything else for no)?");
                   String isIphone = reader.next();
                   System.out.print("Last question officer! How many demerit points did the driver have prior" +
                                    "to being stopped?");

                   if (isIphone.equals("y")){
                       demeritPoints = reader.nextInt() + 2;
                       System.out.println("\n -->Write a ticket for $100 and inform the driver that they now have " +
                               + demeritPoints + " demerit points.");
                   } else {
                       demeritPoints = reader.nextInt() + 1;
                       System.out.println("\n -->Write a ticket for $80 and inform the driver that they now have " +
                               + demeritPoints + " demerit points.");
                   }
                   break;
           case 4: System.out.println("Last question officer! How many demerit points did the driver have prior" +
                                      "to being stopped?");
                   demeritPoints = reader.nextInt() + 3;
                   System.out.println("\n -->Write a ticket for $80 and inform the driver that they now have " +
                       + demeritPoints + " demerit points.");
                   break;
       }

       // Write closing statement
       System.out.println("\nGood job officer! Crazy Nancy's tells you to keep up the good work!!!!");
    }
}

class A2Question2 {
    public static void process(){
        String consoleTitle = new String("Welcome to Nancy's Addition Tutorial Program!");
        String dashes = new String(new char[consoleTitle.length()]).replace("\0", "-");
        System.out.println(consoleTitle);
        System.out.println(dashes);
        String tryAnother;
        Scanner reader = new Scanner(System.in);

        do {
            System.out.print("Enter two numbers with at most 3-digits each, separated by a space and press enter: ");
            int num1 = reader.nextInt();
            int num2 = reader.nextInt();

            System.out.printf("%-10s%3d%n", " num1:", num1);
            System.out.printf("%-10s%3d%n", " num2: +", num2);
            System.out.printf("%13s%n", "------");

            // Addition of digit 1
            System.out.println("\n1st addition:");
            System.out.println("  last digit of each number");
            int digit1 = num1 % 10;
            int digit2 = num2 % 10;
            int addition = digit1 + digit2;
            int answer = addition % 10;
            int carry = addition / 10;
            System.out.println("  " + digit1 + " + " + digit2 + " = " + addition + " so answer is " + answer +
                               " with a carry of " + carry);

            // Addition of digit 2
            System.out.println("\n2nd addition:");
            System.out.println("  the carry from previous addition plus the middle digit of each number");
            digit1 = (num1 / 10) %10;
            digit2 = (num2 / 10) %10;
            addition = carry + digit1 + digit2;
            answer = addition % 10;
            carry = addition / 10;
            System.out.println("  " + carry + " + " + digit1 + " + " + digit2 + " = " + addition + " so answer is " +
                               answer + " with a carry of " + carry);

            // Addition of digit 2
            System.out.println("\n3nd addition:");
            System.out.println("  the carry from previous addition plus the first digit of each number");
            digit1 = (num1 / 100) %10;
            digit2 = (num2 / 100) %10;
            addition = carry + digit1 + digit2;
            System.out.println("  " + carry + " + " + digit1 + " + " + digit2 + " = " + addition + " so answer is " +
                               addition);

            // Print final answer
            System.out.println("\nFinal answer:");
            System.out.printf("%-10s%3d%n", " num1:", num1);
            System.out.printf("%-10s%3d%n", " num2: +", num2);
            System.out.printf("%13s%n", "------");
            System.out.printf("%-9s%4d%n", " Answer:", num1 + num2);

            // Ask if user wants another try
            System.out.print("Do you want to try another one? (y or Y to repeat) ");
            tryAnother = reader.next();
        } while (tryAnother.equals("y") || tryAnother.equals("Y"));

        System.out.println("\nHope you are more comfortable with additions now! If not, don't hesitate to come back :-)");
    }

}

class A2Question3 {
    public static void process(){
        // Page header
        String dashes = new String(new char[53]).replace("\0", "-");

        System.out.println(dashes);
        System.out.println("   Welcome to Nancy's Parkside's Triangle Producer");
        System.out.println(dashes);

        // Ask user for inputs
        Scanner reader = new Scanner(System.in);
        // Input size
        int size = 0;
        // Verify if between 5 and 10
        while(size < 5 || size > 10){
            System.out.print("Size (must be between 5 and 10 inclusive): ");
            size = reader.nextInt();
        }
        // Input seed
        int seed = 0;
        // Verify if between 1 and 9
        while(seed < 1 || seed > 9){
            System.out.print("Seed (must be between 1 and 9 inclusive): ");
            seed = reader.nextInt();
        }

        // Start Triangle
        for (int i = 0; i < size; i++){
            for (int j = 0; j < i+1; j++){
                System.out.print(seed + " ");
                seed = (seed < 9) ? seed + 1 : 1;
            }
            System.out.print("\n");
        }

        // Ending message
        System.out.println("\nAll done!");
    }
}

