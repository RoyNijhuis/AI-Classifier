package Controller;

import Model.Category;

import java.util.ArrayList;
import java.util.List;

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

    }
}
