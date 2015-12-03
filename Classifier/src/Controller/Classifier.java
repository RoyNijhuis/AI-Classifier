package Controller;

import Model.Category;

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

        Trainer trainer = new Trainer();
        try{
            Map<String, Integer> trainedMap = trainer.trainFromFolder("C:\\Users\\Roy\\Downloads\\blogs\\blogs\\F");
            categories.get(0).mergeMaps(trainedMap);
        }
        catch(IOException e)
        {

        }
        System.out.println(categories.get(0).getWords().size());
    }
}
