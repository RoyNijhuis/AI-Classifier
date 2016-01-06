package View;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Roy on 9-12-2015.
 */
public class TUI implements UI {
	
	//Asks the user for the folder where to train category from, then returns the path
    public String askForTrainFolder(String category)
    {
        String path = null;
        boolean done = false;
        while(!done)
        {
            System.out.println("Please enter the path to the directory in which your training files are with class '" + category + "'" + ":");
            Scanner user_input = new Scanner( System.in );
            path = user_input.nextLine();

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
    
    //outputs the category calculated for document
    public void outputDeterminedCategory(String document, String category)
    {
        System.out.println(document+ " is probably of class '" + category + "'.");
    }
    
    //ask the user for the correct category
    public String askCorrectClass(String category)
    {
        System.out.println("Is this the correct class? (y/n)");
        Scanner user_input = new Scanner( System.in );
        String correct = user_input.nextLine();
        String correctClass = category;

        if(!correct.equals("y") && !correct.equals("yes"))
        {
            //Class is not correct
            System.out.println("What is the correct class?");
            correctClass = user_input.nextLine();
        }

        return correctClass;
    }
    
    //Ask the user for the path to the file that should be classified
    public String askForApplyFile()
    {
        String path = null;
        boolean done = false;
        while(!done)
        {
            System.out.println("Please enter the path to the file/directory that you would like to classify: ");
            Scanner user_input = new Scanner( System.in );
            path = user_input.nextLine();

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
    
    public int askForMaximumNumberOfOccurrences()
    {
        int number = -1;
        boolean done = false;
        while(!done) {
            System.out.println("Please enter the maximum number of times a word can occur. If you do not want a maximum, enter -1");
            Scanner user_input = new Scanner(System.in);
            String input = user_input.nextLine();

            try {
                number = Integer.valueOf(input);
                done = true;
                if(number < 0)
                {
                    number = -1;
                }
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

    public String[] askClasses()
    {
        System.out.println("Which classes are there in the documents? Seperate each class with a comma(,).");
        Scanner user_input = new Scanner(System.in);
        String input = user_input.nextLine();

        String[] result = input.split(",");

        return result;
    }

	
	public boolean askLearn() {
		boolean result = false;
        boolean done = false;
        while(!done)
        {
            System.out.println("Do you want the classifier to learn from the new documents?(y/n)");
            Scanner user_input = new Scanner( System.in );
            String answer = user_input.nextLine();

            if(answer.equals("y")){
            	result = true;
            	done = true;
            } else if(answer.equals("n")){
            	done = true;
            } else {
            	System.out.println("Please answer with \'y\' or \'n\'");
            }
        }

		return result;
	}

    @Override
    public void show() {

    }
}
