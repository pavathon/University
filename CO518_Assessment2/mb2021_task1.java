import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class mb2021_task1
{
    private static List<Stack<Integer>> allTowers;  // Stores all the towers in an array

    // ---------------------------------------------------------------------
    // Function: main
    // ---------------------------------------------------------------------
    public static void main(String[] args) {
        // Get parameters
        int n, t, s, d;
        String myID = "mb2021";

        if (args.length < 4) {
            System.out.printf("Usage: java %s_task1 <n> <t> <s> <d>\n", myID);
            return;
        }

        n = Integer.parseInt(args[0]);  // Read user input n
        t = Integer.parseInt(args[1]);  // Read user input t
        s = Integer.parseInt(args[2]);  // Read user input s
        d = Integer.parseInt(args[3]);  // Read user input d

        // Check the inputs for sanity
        if (n<1 || t<3 || s<1 || s>t || d<1 || d>t) {
            System.out.print("Please enter proper parameters. (n>=1; t>=3; 1<=s<=t; 1<=d<=t)\n");
            return;
        }

        // Instantiate the towers and discs
        createTowers(n, t, s);

        // Create the output file name
        String fileName = "mb2021_ToH_n" + n + "_t" + t + "_s" + s + "_d" + d + ".txt";
        try {
            // Create the Writer object for writing to "filename"
            FileWriter writer = new FileWriter(fileName);

            // Write the first line: n, t, s, d
            writer.write(n + "\t" + t + "\t" + s + "\t" + d);

            // Call the recursive function that solves the ToH problem
            towerOfHanoi(n, s, d, writer);

            // Close the file
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        System.out.print("\n");
    }

    // ---------------------------------------------------------------------
    // Function: The recursive function for moving n discs
    //           from s to d with t-2 buffer towers.
    //           It prints all the moves with disc numbers.
    // ---------------------------------------------------------------------
    private static void towerOfHanoi(int numberOfDiscs, int sourceTower, int destinationTower, FileWriter writer)
    {
        // If number of discs is 0, return.
        if (numberOfDiscs <= 0) return;
        // If number of discs is 1, move disc to destination tower, return.
        else if (numberOfDiscs == 1) {
            moveDisc(sourceTower, destinationTower, writer);
            return;
        }

        // Get all the buffer towers for this recursive call.
        int[] buffers = getBuffers(sourceTower, destinationTower);

         //Recursively move the first chunk of discs from the source tower to the last buffer tower.
        towerOfHanoi(numberOfDiscs - buffers.length, sourceTower, buffers[buffers.length - 1], writer);

        // Iteratively move remaining discs to separate buffer towers.
        int leftOff = moveOtherDiscs(sourceTower, buffers, writer);

        // Move largest disc to destination tower.
        moveDisc(sourceTower, destinationTower, writer);

        // Iteratively move back remaining discs to destination tower.
        moveBackOtherDiscs(buffers, destinationTower, leftOff, writer);

        // Recursively move the discs in the last buffer tower to the destination tower.
        towerOfHanoi(numberOfDiscs - buffers.length, buffers[buffers.length - 1], destinationTower, writer);
    }

    // ---------------------------------------------------------------------
    // Function: Stores all the buffer towers (towers which are neither the
    //           source nor the destination towers) in an array.
    // ---------------------------------------------------------------------
    private static int[] getBuffers(int sourceTower, int destinationTower)
    {
        // Keeps track of the buffers index.
        int index = 0;
        int[] buffers = new int[allTowers.size() - 2];

        // Loops and checks if tower is a buffer tower.
        for (int i = 1; i <= allTowers.size(); i++) {

            // Buffer towers must not be source or destination towers.
            if (i != sourceTower && i != destinationTower) {
                buffers[index] = i;
                index++;
            }
        }
        return buffers;
    }

    // ---------------------------------------------------------------------
    // Function: Move all the remaining discs in the source tower (except
    //           the largest one) to the remaining empty buffer towers.
    // ---------------------------------------------------------------------
    private static int moveOtherDiscs(int sourceTower, int[] buffers, FileWriter writer)
    {
        if (buffers.length == 2) {
            moveDisc(sourceTower, buffers[0], writer);
            return 0;
        }

        // Move remaining discs from source tower to separate buffer towers.
        for (int i = 0; i < buffers.length - 1; i++) {
            // If at largest disk, stop iterating.
            if (allTowers.get(sourceTower - 1).size() == 1) {
                return buffers.length - 2 - i + 1;
            }
            moveDisc(sourceTower, buffers[buffers.length - 2 - i], writer);
        }
        return 0;
    }

    // ---------------------------------------------------------------------
    // Function: Moves the discs that were iteratively moved to the buffer
    //           towers back to the destination tower.
    // ---------------------------------------------------------------------
    private static void moveBackOtherDiscs(int[] buffers, int destinationTower, int leftOff, FileWriter writer)
    {
        // If amount of buffer towers is 2, move disc from first buffer to destination
        if (buffers.length == 2) {
            moveDisc(buffers[0], destinationTower, writer);
            return;
        }

        // Gets all remaining discs and moves them to the destination tower.
        for (int i = leftOff; i < buffers.length - 1; i++) {
            moveDisc(buffers[i], destinationTower, writer);
        }
    }

    // ---------------------------------------------------------------------
    // Function: Moves the top disc from the source tower to the top of the
    //           destination tower.
    //           Prints every move on screen and also writes it to a file.
    // ---------------------------------------------------------------------
    private static void moveDisc(int sourceTower, int destinationTower, FileWriter writer)
    {
        // Removes top disc from source tower and puts it in top of destination tower.
        int disc = allTowers.get(sourceTower - 1).pop();
        allTowers.get(destinationTower - 1).push(disc);

        try {
            System.out.printf("\nMove disk %d from T%d to T%d", disc, sourceTower, destinationTower);
            writer.write("\n" + disc + "\t" +  sourceTower + "\t" + destinationTower);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
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
}
