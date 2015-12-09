package Controller;

import Model.Category;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by Roy on 3-12-2015.
 */
public class Classifier {
    private List<Category> categories;

    public Classifier()
    {
        categories = new ArrayList<>();
    }

    public void test()
    {
        categories.add(new Category("M"));
        categories.add(new Category("F"));
        
        //read file and sort words
        try {
			Map<String, Integer> p1 = Reader.readFromFolder("C:\\Users\\Roy\\Downloads\\blogs\\blogs\\M");
			categories.get(0).mergeMaps(p1);
			Map<String, Integer> p2 = Reader.readFromFolder("C:\\Users\\Roy\\Downloads\\blogs\\blogs\\F");
			categories.get(1).mergeMaps(p2);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        //train with words
        Map<Category, Map<String,Double>> trained = Trainer.train(categories);
        //classify new documents
        try {
			Map<String, Integer> toTestt = Reader.readFromFile("C:\\Users\\Roy\\Downloads\\blogs\\blogs\\test.txt");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        //Classify now
        
        
        
        
        /*Trainer trainer = new Trainer();
        try{
            Map<String, Integer> trainedMap = trainer.trainFromFolder("C:\\Users\\Roy\\Downloads\\blogs\\blogs\\M");
            categories.get(0).mergeMaps(trainedMap);
            Map<String, Integer> trainedMap2 = trainer.trainFromFolder("C:\\Users\\Roy\\Downloads\\blogs\\blogs\\F");
            categories.get(1).mergeMaps(trainedMap2);

            Map<String, Integer> toTest = trainer.trainFromFile("C:\\Users\\Roy\\Downloads\\blogs\\blogs\\test.txt");
            Applier applier = new Applier();
            Category winner = applier.classify(categories, toTest);
            System.out.println("This data is most likely: " + winner.getName());
/*
            File folder = new File("C:\\Users\\Roy\\Downloads\\blogs\\blogs\\F");
            if(folder.isDirectory())
            {
                File[] files = folder.listFiles();
                for(File f: files)
                {
                    Category winner = applier.classify(categories, trainer.trainFromFile(f.getAbsolutePath()));
                    System.out.println("This data is most likely: " + winner.getName());
                }
            }
            /
        }
        catch(IOException e)
        {
        	System.out.println("OOPS");	
        }*/
    }
}
