package Model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Roy on 3-12-2015.
 */
public class Category {
    private String name;
    private Map<String, Integer> words;

    public Category(String name)
    {
        this.name = name;
        this.words = new HashMap<>();
    }

    public void setWords(Map<String, Integer> words)
    {
        this.words = words;
    }

    public String getName()
    {
        return name;
    }

    public void updateWord(String name, int amount)
    {
        if(words.containsKey(name))
        {
            this.words.put(name, amount+words.get(name));
        }
        else
        {
            this.words.put(name, amount);
        }
    }

    public void mergeMaps(Map<String, Integer> map)
    {
        for(Map.Entry<String, Integer> e: map.entrySet())
        {
            updateWord(e.getKey(), e.getValue());
        }
    }

    public Map<String, Integer> getWords()
    {
        return words;
    }
}
