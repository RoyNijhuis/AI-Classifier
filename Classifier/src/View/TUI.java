package View;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import Model.Category;

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
    public String askCorrectClass(String category, List<Category> categories)
    {
        System.out.println("Is this the correct class? (y/n)");
        Scanner user_input = new Scanner( System.in );
        String correct = user_input.nextLine();
        String correctClass = category;
        
        if(!correct.equals("y") && !correct.equals("yes"))
        {
            //Class is not correct
            boolean done = false;
            while(!done){
            	System.out.println("What is the correct class?");
                correctClass = user_input.nextLine();
                boolean contains = false;
                for(Category i: categories){
                	if(i.getName().equals(correctClass)){
                		contains = true;
                		break;
                	}
                }
                if(contains){
                	done = true;
                } else {
                	System.out.println(correctClass + " is not a category");
                }
            }
        	
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

    //Asks the minimum number of times a word should occur in the training sets, before it should be added to the dicionary.
    //Returns the input of the user
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

    //Asks the maximum number of times a word should occur in the training sets.
    //Returns the input of the user
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

    //Asks the user which classes exist, the user should seperate the name of each class with a comma(,)
    public String[] askClasses()
    {
        System.out.println("Which classes are there in the documents? Seperate each class with a comma(,).");
        Scanner user_input = new Scanner(System.in);
        String input = user_input.nextLine();

        String[] result = input.split(",");

        return result;
    }

	//Asks whether the user wants an interactive learner or only a classifier that does not update its dictionary during the session.
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
}
