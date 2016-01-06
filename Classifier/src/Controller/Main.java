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
		ui.show();
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
				i-=1;
			}
			categories.add(cat);
		}
		int numberOfOccurrences = ui.askForMinimumNumberOfOccurrences();
		int maxOccurrences = ui.askForMaximumNumberOfOccurrences();
		categories = Trainer.train(categories, numberOfOccurrences, maxOccurrences);

		
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
					e.printStackTrace();
				}

				Category result = Applier.classify(categories, words);
				System.out.println("This file is probably of class: " + result.getName());
	        }
		}
		
	}
}
