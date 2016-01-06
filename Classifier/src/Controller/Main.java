package Controller;

import Model.Category;
import View.TUI;
import View.UI;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {
	
	public static void main(String[] args) {
		//create user interface
		UI ui = new TUI();
		ui.show();
		
		//list all categories
		String categorieNames[] = ui.askClasses();
		List<Category> categories = new ArrayList<>();
		
		//for every categorie get train documents
		for(int i=0; i<categorieNames.length; i++)
		{
			Category cat = new Category(categorieNames[i]);
			
			//get the training folder
			String trainPath = ui.askForTrainFolder(categorieNames[i]);
			try {
				//read all the words from the folder and count the documents
				cat.setWords(Reader.readFromFolder(trainPath));
				cat.setDocs(Reader.readAmountFiles(trainPath));
			} catch (IOException e) {
				
				i-=1;
			}
			categories.add(cat);
		}
		
		//min and max number of occurrences for a word
		int numberOfOccurrences = ui.askForMinimumNumberOfOccurrences();
		int maxOccurrences = ui.askForMaximumNumberOfOccurrences();
		
		//train the categories and create the probabilities
		categories = Trainer.train(categories, numberOfOccurrences, maxOccurrences);

		//while loop to classify documents
		while(true)
		{
			
			String filePath = ui.askForApplyFile();
			File folder = new File(filePath);
			Boolean learn = ui.askLearn();
	        if(folder.isDirectory())
	        {
	            File[] files = folder.listFiles();
	            for(int i=0; i<files.length; i++){
	            	filePath = files[i].getAbsolutePath();
	            	Map<String, Integer> words = null;
					try {
						words = Reader.readFromFile(filePath);
					} catch (IOException e) {
						i-=1;
					}

					Category result = Applier.classify(categories, words);
					
					ui.outputDeterminedCategory(files[i].getName(), result.getName());
					if(learn){
						String cate = ui.askCorrectClass(result.getName(), categories);
						Category update= null;
						if(cate!=null){
							for(Category c: categories){
								if(c.getName().equals(cate)){
									update = c;
									
								}
							}
							if(update != null){
								update.addDoc(1);
								update.mergeMaps(words);
								Trainer.train(categories, numberOfOccurrences,maxOccurrences);
							}
						}
					}
	            }
	        } else {
	        	Map<String, Integer> words = null;
				try {
					words = Reader.readFromFile(filePath);
				} catch (IOException e) {
					
				}

				Category result = Applier.classify(categories, words);
				
				ui.outputDeterminedCategory(folder.getName(), result.getName());
				if(learn){
					String cate = ui.askCorrectClass(result.getName(), categories);
					Category update= null;
					if(cate!=null){
						for(Category c: categories){
							if(c.getName().equals(cate)){
								update = c;
								
							}
						}
						if(update != null){
							update.addDoc(1);
							update.mergeMaps(words);
							Trainer.train(categories, numberOfOccurrences,maxOccurrences);
						}
					}
				}
	        }
		}
		
	}
}
