import java.util.Scanner;

public class Assignement1 {
    public static void main(String[] args){

        if (args[0].equals("Question1")){
            A1Question1 q = new A1Question1();
            q.process();
        } else if (args[0].equals("Question2")) {
            A1Question2 q = new A1Question2();
            q.process();
        } else if (args[0].equals("Question3")) {
            A1Question3 q = new A1Question3();
            q.process();
        }
    }
}

class A1Question1 {
    public static void process(){}
}

class A1Question2 {
    public static void process() {
        // Write header of console
        String stars1 = new String(new char[58]).replace("\0", "*");
        String stars2 = new String(new char[8]).replace("\0", "*");
        System.out.println("/" + stars1 + "/");
        System.out.println("/" + stars2 + "     All the things you cannot do...     " + stars2 + "/");
        System.out.println("/" + stars1 + "/");

        //Initiate Scanner
        Scanner reader = new Scanner(System.in);
        // Name Question
        System.out.print("Hi there! What's your name? ");
        String name = reader.next();
        // Age Question
        System.out.print("Nice to meet you " + name + ", how old are you? ");
        int age = reader.nextInt();
        //What you cant do
        System.out.print("Ok, " + name + " did you know, ");

        if(age < 16){
            System.out.println("you can't drive.");
        } else if (age < 18){
            System.out.println("you can't vote.");
        } else if (age < 25){
            System.out.println("you can't rent a car.");
        } else {
            System.out.println("you can do anything that's legal");
        }

        // Print ending words
        System.out.println("\nTalk to you next time!");

    }
}

class A1Question3 {
    public static void process(){
        // Write header of console
        String dashes = new String(new char[58]).replace("\0", "*");
        System.out.println(dashes);
        System.out.println(" The Simple Personality Test!");
        System.out.println(dashes);

        // Initiate Scanner
        Scanner reader = new Scanner(System.in);
        // Initial Question
        System.out.print("Are you ready for a personality test? (Yes/No): ");
        String ready = reader.next();
        if (ready.equals("Yes") || ready.equals("yes")){
            System.out.println("All right here we go!\n");
        } else {
            System.out.println("Alright, well we're going to do it anyways!\n");
        }

        // Start Question
        System.out.println("Answer the following questions on a scale of 1-10.");
        String spaces = new String(new char[7]).replace("\0", " ");
        //Question 1
        int energy;
        do {
            System.out.println("Q1: How do you get your energy?\n" +
                               spaces + "1 -- By spending time alone\n"+
                               spaces + "10 -- By spending time with others");
            energy = reader.nextInt();
        }while (_isValid(energy));

        //Question 2
        int worldInfo;
        do {
            System.out.println("Q2: How do you see the world & gather information?\n" +
                    spaces + "1 -- In concrete terms\n"+
                    spaces + "10 -- In abstract terms");
            worldInfo = reader.nextInt();
        }while (_isValid(worldInfo));

        //Question 3
        int decisions;
        do {
            System.out.println("Q3: How do you make your decisions?\n" +
                    spaces + "1 -- Using my head\n"+
                    spaces + "10 -- Using my heart");
            decisions = reader.nextInt();
        }while (_isValid(decisions));

        //Question 4
        int plan;
        do {
            System.out.println("Q4: How much do you like to plan?\n" +
                    spaces + "1 -- - Make plans far in advance\n"+
                    spaces + "10 -- Go with the flow");
            plan = reader.nextInt();
        }while (_isValid(plan));

        // Parse Answers
        String personality = _parseAnswers(energy, worldInfo, decisions, plan);

        // Print personality
        System.out.println("Your personality type is : *" + personality + "*");
        System.out.println("To find out more about that type of personality check out:\n" +
                           "https://www.truity.com/view/types\nHope you had fun! See you next time!");

    }

    private static boolean _isValid(int answer){
        /*Verify if answer is between 1-10*/
        if (answer > 10 || answer < 1){
            System.out.println("INVALID INPUT: please enter a number between 1 and 10");
            return true;
        }

        return false;
    }

    private static String _parseAnswers(int energy, int worldInfo, int decisions, int plan){
        StringBuilder personality = new StringBuilder("1234");
        if(energy <= 5){
            personality.setCharAt(0, 'I');
        } else {
            personality.setCharAt(0, 'E');
        }
        if(worldInfo <= 5){
            personality.setCharAt(1, 'S');
        } else {
            personality.setCharAt(1, 'N');
        }

        if(decisions <= 5){
            personality.setCharAt(2, 'T');
        } else {
            personality.setCharAt(2, 'F');
        }

        if(plan <= 5){
            personality.setCharAt(3, 'J');
        } else {
            personality.setCharAt(3, 'P');
        }

        return personality.toString();
    }
}
