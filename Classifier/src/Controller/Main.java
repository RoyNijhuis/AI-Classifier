package Controller;

import Model.Category;
import View.TUI;
import View.UI;

import java.io.IOException;
import java.util.*;

public class Main {

	public static void main(String[] args) {
		UI ui = new TUI();
		String categorieNames[] = ui.askClasses();
		String trainPaths[] = new String[categorieNames.length-1];
		Category categories[] = new Category[categorieNames.length];
		for(int i=0; i<categories.length; i++)
		{
			categories[i] = new Category(categorieNames[i]);
			trainPaths[i] = ui.askForTrainFolder(categorieNames[i]);
		}

		while(true)
		{
			String filePath = ui.askForApplyFile();
			Map<String, Integer> words = null;
			try {
				Reader.readFromFile(filePath);
			} catch (IOException e) {
				e.printStackTrace();
			}

			Category result = Applier.classify(new ArrayList<Category>(Arrays.asList(categories)), words);
			System.out.println("This file is probably of class: " + result.getName());
		}
	}
}
