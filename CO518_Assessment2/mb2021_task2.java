import java.io.File;
import java.io.IOException;
import java.util.*;

public class mb2021_task2
{
    private static List<Stack<Integer>> allTowers;                  // Stores all the towers in an array.
    private static boolean valid;                                   // Checks whether the solution is valid or not.

    // ---------------------------------------------------------------------
    // Function: main
    // ---------------------------------------------------------------------
    public static void main(String[] args)
    {
        int n, t, s, d;
        String fileName, myID;

        // Stores the contents of the file in a list.
        myID = "mb2021";
        fileName = args[0];
        List<String> steps = readFile(fileName);

        // Check if the input filename has been provided as an argument
        if (args.length < 1) {
            System.out.printf("Usage: java %s_task2 <file_name>\n", myID);
            return;
        }

        // Assumes that the moves in the file are correct until proven incorrect.
        valid = true;

        // Gets values in first line of the file and stores them in variables.
        String[] params = steps.get(0).split("\\s+");
        n = Integer.parseInt(params[0]);
        t = Integer.parseInt(params[1]);
        s = Integer.parseInt(params[2]);
        d = Integer.parseInt(params[3]);

        // Resets the towers and puts all discs in source tower.
        createTowers(n, t, s);

        // Main output
        System.out.println("The status of all the towers at the start is as follows:");
        System.out.println(stateToString(s));

        // Output all the moves
        for (int i = 1; i < steps.size(); i++) {

            /*
            Stores the line in an array and passes the values in the
            array to their respective local variables.
             */
            String[] move = steps.get(i).split("\\s+");
            int disc = Integer.parseInt(move[0]);
            int sourceTower = Integer.parseInt(move[1]);
            int destinationTower = Integer.parseInt(move[2]);

            String err = checkIllegalMoves(n, disc, sourceTower, destinationTower);

            System.out.println("Move:\tdisc " + move[0] + " from tower " + move[1] + " to tower " + move[2]);

            // First instance checks whether the disc, source or tower were invalid.
            if (!valid) {
                System.out.print(err);
                break;
            }

            System.out.print("Before the move:\n" + moveToString(sourceTower, destinationTower));

            if (!valid) {
                System.out.print(err);
                break;
            }
            moveDisc(sourceTower, destinationTower);

            System.out.println("After the move:\n" + moveToString(sourceTower, destinationTower));
        }

        System.out.println("The status of all the towers at the end of the sequence of moves is as follows:\n" +
                stateToString(d));

        // Prints whether the series of moves were valid or not.
        if (valid) System.out.println("The sequence of moves is correct");
        else System.out.println("The sequence of moves is incorrect");
    }

    // ---------------------------------------------------------------------
    // Function: main
    // ---------------------------------------------------------------------
    private static List<String> readFile(String fileName)
    {
        List<String> steps = new ArrayList<>();
        try {
            Scanner input = new Scanner(new File(fileName));
            while (input.hasNextLine()) {
                steps.add(input.nextLine());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return steps;
    }

    // ---------------------------------------------------------------------
    // Function: Creates all the towers in the solution and creates all
    //           the discs and stores them in the source tower.
    // ---------------------------------------------------------------------
    private static void createTowers(int numberOfDiscs, int numberOfTowers, int sourceTower)
    {
        // Create the towers
        allTowers = new ArrayList<>();
        for (int i = 0; i < numberOfTowers; i++) {
            allTowers.add(new Stack<Integer>());
        }

        // All the discs are initially stored in the source tower
        for (int i = numberOfDiscs; i > 0; i--) {
            allTowers.get(sourceTower - 1).push(i);
        }
    }

    // ---------------------------------------------------------------------
    // Function: Moves the top disc from the source tower to the top of
    //           the destination tower.
    // ---------------------------------------------------------------------
    private static void moveDisc(int sourceTower, int destinationTower)
    {
        // Removes top disc from source tower and adds it to destination tower
        int disc = allTowers.get(sourceTower - 1).pop();
        allTowers.get(destinationTower - 1).push(disc);
    }

    // ---------------------------------------------------------------------
    // Function: Checks whether the move was valid or invalid.
    // ---------------------------------------------------------------------
    private static String checkIllegalMoves(int numberOfDiscs, int givenDisc, int sourceTower, int destinationTower)
    {
        // Stores the error message in a local variable to be outputted.
        String err = "Move Error: ";

        // Checks whether the source, destination or number of discs given are out of range
        if (sourceTower > allTowers.size() || sourceTower < 1) {
            valid = false;
            return err + "The source tower: " + sourceTower + " is out of range.\n\n";
        }
        else if (destinationTower > allTowers.size() || destinationTower < 1) {
            valid = false;
            return err + "The destination tower: " + destinationTower + " is out of range.\n\n";
        }
        else if (givenDisc > numberOfDiscs || givenDisc < 1) {
            valid = false;
            return err + "The disc_number: " + givenDisc + " is out of range\n\n";
        }

        Stack<Integer> s = allTowers.get(sourceTower - 1);
        Stack<Integer> d = allTowers.get(destinationTower - 1);

        // Checks whether the given source tower is empty.
        if (s.empty()) {
            valid = false;
            return err + "The source tower: " + sourceTower + " is empty.\n\n";
        }

        int actualDisc = s.peek();

        // Checks whether the disc in the file is not the top disc in the source tower.
        if (actualDisc != givenDisc) {
            valid = false;
            return err + "Source tower: " + stackToString(s, false) + " does not have disc " +
                    givenDisc + " at the top\n\n";
        }
        // Checks whether the disc in the destination tower is less then the moved disc.
        else if (!d.empty()) {
            if (d.peek() < actualDisc) {
                valid = false;
                return err + "Destination tower: " + stackToString(d, false) +
                        " has a smaller disc than " + actualDisc + " on the top\n\n";
            }
        }
        return "";
    }

    // ---------------------------------------------------------------------
    // Function: Returns a string representing the state of a tower based
    //           on the Sample I/O format.
    // ---------------------------------------------------------------------
    private static String stackToString(Stack<Integer> tower, boolean newLine)
    {
        // Use a string builder to add the discs to the string.
        StringBuilder str = new StringBuilder();
        str.append("[");

        // Checks whether tower is empty or not.
        if (!tower.empty()) {
            str.append(tower.get(0));
            for (int i = 1; i < tower.size(); i++) {
                str.append(", ").append(tower.get(i));
            }
        }
        str.append("]");

        // If newLine is true, then add a new line to the string builder.
        if (newLine) str.append("\n");
        return str.toString();
    }

    // ---------------------------------------------------------------------
    // Function: Returns a string representing the state of the source and
    //           destination tower based on the Sample I/O format.
    // ---------------------------------------------------------------------
    private static String moveToString(int sourceTower, int destinationTower)
    {
        //Returns the states of both given source and destination towers.
        return "Source tower " + sourceTower + ":\t" +
                stackToString(allTowers.get(sourceTower - 1), true) +
                "Destination tower " + destinationTower + ":\t" +
                stackToString(allTowers.get(destinationTower - 1), true);
    }

    // ---------------------------------------------------------------------
    // Function: Returns a string representing the state of all towers
    //           based on the Sample I/O format.
    // ---------------------------------------------------------------------
    private static String stateToString(int state)
    {
        StringBuilder str = new StringBuilder();
        for (int i = 1; i <= allTowers.size(); i++) {
            str.append("Tower ").append(i).append(":\t");
            str.append(stackToString(allTowers.get(i - 1), true));
            str.append(checkEmpty(i, state));
        }
        return str.toString();
    }

    // ---------------------------------------------------------------------
    // Function: Returns a string representing whether any non-destination
    //           towers are empty after all moves have been executed.
    // ---------------------------------------------------------------------
    private static String checkEmpty(int givenTower, int state)
    {
        Stack<Integer> s = allTowers.get(givenTower - 1);

        // Checks whether tower is not destination, is not empty, and all moves were valid.
        if (givenTower != state && !s.empty() && valid) {
            valid = false;
            return "Error: Incomplete solution. Tower " + givenTower + " is not empty!\n";
        }
        return "";
    }
}
