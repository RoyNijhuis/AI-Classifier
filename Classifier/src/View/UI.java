package View;

/**
 * Created by Roy on 9-12-2015.
 */
public interface UI {
    public String askForTrainFolder(String category);
    public String askForApplyFile();
    public int askForMinimumNumberOfOccurrences();
    public String[] askForWordsOnly();
    public String[] askClasses();
    public void outputDeterminedCategory(String document, String category);
    public String askCorrectClass(String category);
    public boolean askLearn();
}
