package View;

/**
 * Created by Roy on 9-12-2015.
 */
public interface UI {
    public String askForTrainFolder(String category);
    public String askForApplyFolder();
    public int askForMinimumNumberOfOccurrences();
    public String[] askForWordsOnly();
    public String[] askClasses();
}
