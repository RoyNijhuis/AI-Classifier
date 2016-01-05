package Model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Roy on 3-12-2015.
 */
public class Category {
    private String name;
    private Map<String, Integer> words;
    private Map<String, Float> probability;
    private float prior;
    private int docs;

    public Category(String name)
    {
        this.name = name;
        this.words = new HashMap<>();
    }

    public void setWords(Map<String, Integer> words)
    {
        this.words = words;
    }

    public void setPrior(float prior)
    {
        this.prior = prior;
    }

    public float getPrior()
    {
        return prior;
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

	public Map<String, Float> getProbability() {
		return probability;
	}

	public void setProbability(Map<String, Float> probability) {
		this.probability = probability;
	}

	public int getDocs() {
		return docs;
	}

	public void setDocs(int docs) {
		this.docs = docs;
	}
}
