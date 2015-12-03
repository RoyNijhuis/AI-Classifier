package Model;

import java.util.Map;

/**
 * Created by Roy on 3-12-2015.
 */
public class Category {
    private Map<String, Integer> words;

    public void setWords(Map<String, Integer> words)
    {
        this.words = words;
    }

    public void addWord(String name, int amount)
    {
        this.words.put(name, amount);
    }

    public Map<String, Integer> getWords()
    {
        return words;
    }
}
