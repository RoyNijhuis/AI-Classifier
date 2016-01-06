package Controller;

import Model.Category;
import View.TUI;
import View.UI;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {

	public static void main(String[] args) {
		UI ui = new TUI();
		String categorieNames[] = ui.askClasses();
		List<Category> categories = new ArrayList<>();
		
		for(int i=0; i<categorieNames.length; i++)
		{
			Category cat = new Category(categorieNames[i]);
			String trainPath = ui.askForTrainFolder(categorieNames[i]);
			try {
				cat.setWords(Reader.readFromFolder(trainPath));
				cat.setDocs(Reader.readAmountFiles(trainPath));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			categories.add(cat);
		}
		int numberOfOccurrences = ui.askForMinimumNumberOfOccurrences();
		categories = Trainer.train(categories, numberOfOccurrences);

		
		while(true)
		{
			String filePath = ui.askForApplyFile();
			File folder = new File(filePath);
	        if(folder.isDirectory())
	        {
	            File[] files = folder.listFiles();
	            for(File f:files){
	            	filePath = f.getAbsolutePath();
	            	Map<String, Integer> words = null;
					try {
						words = Reader.readFromFile(filePath);
					} catch (IOException e) {
						e.printStackTrace();
					}

					Category result = Applier.classify(categories, words);
					
					System.out.println(f.getName()+" is probably of class: " + result.getName());
					Category update;
					for(Category c: categories){
						if(c.getName() == null){
							update = c;
						}
					}
					if(update != null){
						update.addDoc(1);
						update.mergeMaps(words);
						Trainer.train(categories, numberOfOccurrences);
					}
	            }
	        } else {
	        	Map<String, Integer> words = null;
				try {
					words = Reader.readFromFile(filePath);
				} catch (IOException e) {
					e.printStackTrace();
				}

				Category result = Applier.classify(categories, words);
				System.out.println("This file is probably of class: " + result.getName());
	        }
		}
		
	}
}
