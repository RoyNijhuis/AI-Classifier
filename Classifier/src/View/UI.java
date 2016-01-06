package View;

import java.util.List;

import Model.Category;

/**
 * Created by Roy on 9-12-2015.
 */
public interface UI {
    public String askForTrainFolder(String category);
    public String askForApplyFile();
    public int askForMinimumNumberOfOccurrences();
    public int askForMaximumNumberOfOccurrences();
    public String[] askClasses();
    public void outputDeterminedCategory(String document, String category);
    public String askCorrectClass(String category, List<Category> categories);
    public boolean askLearn();
}
