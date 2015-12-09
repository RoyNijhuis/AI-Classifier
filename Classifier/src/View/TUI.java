package View;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Roy on 9-12-2015.
 */
public class TUI implements UI {

    public String askForTrainFolder()
    {
        String path = null;
        boolean done = false;
        while(!done)
        {
            System.out.println("Please enter the path to the directory in which your training files are:");
            Scanner user_input = new Scanner( System.in );
            path = user_input.nextLine();
            System.out.println(path);

            File f = new File(path);
            try {
                f.getCanonicalPath();
                done = true;
            }
            catch (IOException e) {
                System.out.println("Path is not valid!");
            }
        }

        return path;
    }

    public String askForApplyFolder()
    {
        String path = null;
        boolean done = false;
        while(!done)
        {
            System.out.println("Please enter the path to the directory in which the files are that you would like to classify: ");
            Scanner user_input = new Scanner( System.in );
            path = user_input.nextLine();
            System.out.println(path);

            File f = new File(path);
            try {
                f.getCanonicalPath();
                done = true;
            }
            catch (IOException e) {
                System.out.println("Path is not valid!");
            }
        }

        return path;
    }

    public int askForMinimumNumberOfOccurrences()
    {
        int number = 1;
        boolean done = false;
        while(!done) {
            System.out.println("Please enter the minimum number of times a word should occur, before adding it to the vocabulary: ");
            Scanner user_input = new Scanner(System.in);
            String input = user_input.nextLine();

            try {
                number = Integer.valueOf(input);
                done = true;
            } catch (Exception e) {
                System.out.println("Not a number...");
            }
        }

        return number;
    }

    public String[] askForWordsOnly()
    {
        System.out.println("If you only want to use certain words for classification, please enter them seperated by a comma(,).");
        System.out.println("If you want to use all words in the training set leave the line empty.");
        Scanner user_input = new Scanner(System.in);
        String input = user_input.nextLine();

        String[] result = input.split(",");

        return result;
    }
}
