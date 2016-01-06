package Controller;

import Model.Category;
import View.GUI;
import View.TUI;
import View.UI;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main extends Application{

	private static Stage window;

	public void startInteractiveLearner() {
		UI ui = new GUI();
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
			Boolean learn = ui.askLearn();
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
					
					ui.outputDeterminedCategory(f.getName(), result.getName());
					if(learn){
						String cate = ui.askCorrectClass(result.getName());
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
								Trainer.train(categories, numberOfOccurrences);
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

	public static void showGUI()
	{
		window.show();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setTitle("thenewboston - JavaFX");
		Button button = new Button("Click me");

		StackPane layout = new StackPane();
		layout.getChildren().add(button);
		Scene scene = new Scene(layout, 300, 250);

		window.setScene(scene);
		window.hide();

		startInteractiveLearner();
	}
}
