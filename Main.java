//**********LOST IN THE WOODS*********
//*****LOST******
//**** Jesse McCarville-Schueths ****
//** jtmzv8@mail.umsl.edu **
//******* CS 4500-002 ********
// ******** 02-03-2020 *******
// Runs a simulation of two people lost in the woods
// Each person starts at opposite corners of a grid and
// randomly moves till they find each other
//

import java.util.*;

public class Main {
    public static void main(String[] args) {
        experiment(); // this is the main logic
        enterToExit(); // this quits the program
    }

    private static void experiment(){
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter horizontal forest size from 2 to 50: ");
        int sizeA = getValidNum(2, 50) - 1;         //inits horizontal size of grid, sub 1 since array starts at 0
        System.out.println("Enter vertical forest size from 2 to 50: ");
        int sizeB = getValidNum(2, 50) - 1;         //inits vertical size of grid, sub 1 since array starts at 0

        ArrayList<Person> ListOfPeople = makePerson(2, sizeA,sizeB);   // creates an arraylist of people in case more than 2 need to be simulated

        displayForest(ListOfPeople, sizeA, sizeB);  // displays the grid

        System.out.println("How many times would you like to run the experiment: ");
        int numOfRuns = getValidNum(1, 10000);

        clearScreen();
        System.out.println("Please wait...\n");

        int result = 0;
        int sum = 0;
        int min = 0;
        int max = 0;

        for (int i = 0; i < numOfRuns; i++) {
            result = sim(ListOfPeople, sizeA, sizeB);
            sum += result;
            if(min == 0 && max == 0) {  // sets min and max to first result
                max = result;
                min = result;
            }
            if(result > max)
                max = result;
            if(result < min)
                min = result;


        }
        int avg = sum/numOfRuns;

        System.out.println("After " + numOfRuns + " experiments, the average time it too Sam and Chris to find \n" +
                "each other in a " + (sizeA + 1) + "x" + (sizeB + 1) + " forest was " + avg +" minutes.\n" +
                "The fastest they found each other was " + min + " minutes.\n" +
                "The slowest they found each other was " + max + " minutes.\n");
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static int sim (ArrayList<Person> person, int sizeA, int sizeB){
        for (int i = 0; i < 2; i++) {
            person.get(i).reset();     // resets each persons positions
        }
        int i = 0;
        while (i < 1000000) {
        for (int j = 0; j < 2; j++) {
            int moveX = randMove();
            int moveY = randMove();
            if (((person.get(j).getXCord() + moveX) >= sizeA) || ((person.get(j).getXCord() + moveX) <= 0)) {
                person.get(j).move(0,0);
            }
            else if (((person.get(j).getYCord() + moveY) >= sizeB) || ((person.get(j).getYCord() + moveY) <= 0)) {
                person.get(j).move(0,0);
            }
            else
                person.get(j).move(moveX, moveY);
            //System.out.println("" + person.get(j).getLetter() +" = (" + person.get(j).getXCord() + ", " + person.get(j).getYCord() + ")");
        }

        if((person.get(0).getXCord() == (person.get(1).getXCord())) && (person.get(0).getYCord() == (person.get(1).getYCord()))){
            //displayForest(person, sizeA, sizeB);
            //System.out.println("It took " + i +" minutes for Sam and Chris to find each other.");
            break;

            }
        i++;
        }
        return i;

    }

    private static int randMove(){
        Random rand = new Random();
        int randNum = rand.nextInt(3) - 1; // This will only produce -1 ,0, 1
        return randNum;
    }

    private static void displayForest(ArrayList<Person> person, int sizeA, int sizeB){
        String spot = "";
        int personFound = 0;
        //parent loop to display y axis on board
        for (int y = 0; y <= sizeB; y++) {
            //child loop to display x axis on board
            for (int x = 0; x <= sizeA; x++) {
                personFound = 0;
                for (int z = 0; z < (2); z++) {
                    if ((person.get(z).getXCord() == x) && (person.get(z).getYCord() == y)) {
                        spot = "|" + person.get(z).getLetter(); // stores the letter of person to be displayed
                        personFound += 1;
                    }
                }
                if(personFound == 0){    // displays empty space as "t"
                    System.out.print("|.");
                }
                else if(personFound == 2){    // displays meet space as "X"
                    System.out.print("|X");
                }
                else
                    System.out.print(spot);   // display a person
            }
            System.out.print("|" + "\n");
        }
        System.out.println(". = tree, P = Pat, C = Chris");
    }

    private static ArrayList<Person> makePerson(int amount, int sizeA, int sizeB){
        Random rand = new Random();
        Scanner in = new Scanner(System.in);
        ArrayList<Person> personList = new ArrayList<Person>(); // declare arraylist for People
        personList.add(new Person(0,0,"Pat", 'P'));
        personList.add(new Person(sizeA,sizeB,"Chris", 'C'));

        return personList;
    }

    private static int getValidNum(int lowerBound, int upperBound){
        Scanner in = new Scanner(System.in);
        String number = in.nextLine();
        int num = 1;  // num will be the string value if string is int
        try {
            num = Integer.parseInt(number);   // this will throw exception if non-digits are used
            if (num >= lowerBound && num <= upperBound) {       //user input is valid
                return num;
            }
            else {          // user input wasn't within bounds
                System.out.println("Input not within bounds.\n" +
                        "Please enter a number between " + lowerBound + " and " + upperBound + ".");
                num = getValidNum(lowerBound, upperBound);      // recursively ask for number til valid input is given
            }
        }
        catch (NumberFormatException nfe) {      //catches exception
            System.out.println("Input not a number.\n" +
                    "Please enter a number between " + lowerBound + " and " + upperBound + ".");
            num = getValidNum(lowerBound, upperBound); // ask for user input again till correct input is given
        }
        return (num - 1);
    }

    public static void enterToExit(){     // has the user press enter to exit
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press \"ENTER\" to exit...");
        scanner.nextLine();
    }

    private static class Person{
        public String name;
        public char letter;
        public int xCord;
        public int yCord;
        public int initial_xCord;
        public int initial_yCord;

        public Person(int xCord, int yCord, String name, char letter){  //constructor
            this.name = name;
            this.letter = letter;
            this.xCord = xCord;
            this.yCord = yCord;
            this.initial_xCord = xCord;
            this.initial_yCord = yCord;
        }

        public void move(int x, int y) {
            this.xCord = xCord + x;
            this.yCord = yCord + y;
        }

        public void reset() {
            this.xCord = initial_xCord;
            this.yCord = initial_yCord;
        }

        public int getXCord() {
            return xCord;
        }  //gets x coordinate

        public int getYCord() {
            return yCord;
        }  //gets y coordinate

        public char getLetter(){
            return letter;
        }  //gets letter of person
    }

}
