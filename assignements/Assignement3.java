import java.util.Random;
import java.util.stream.*;

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

    }
}
class A3Question3{
    public static void process(){

    }
}