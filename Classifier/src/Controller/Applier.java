package Controller;

import Model.Category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Roy on 3-12-2015.
 */
public class Applier {

    public Category classify(List<Category> cat, Map<String, Integer> toClassify)
    {
        int totalWordsInFile = totalWordsInFile(toClassify);

        Map<String, Integer> vocab = new HashMap<>();
        for(Category category: cat)
        {
            vocab.putAll(category.getWords());
        }
        int totalWordsInVocabulary = vocab.size();

        List<Float> chances = new ArrayList<>();
        for(int i=0; i<cat.size();i++)
        {
            Category c = cat.get(i);
            float chance = 0;
            for(Map.Entry<String, Integer> entry: toClassify.entrySet())
            {
                int value = c.getWords().get(entry.getKey())==null?0:c.getWords().get(entry.getKey());
                chance+=Math.log((((float)value+1f)/((float)totalWordsInFile+(float)totalWordsInVocabulary)))/Math.log(2);
            }
            chances.add(chance);
        }

        System.out.println("Chance for M: " + chances.get(0));
        System.out.println("Chance for F: " + chances.get(1));

        int indexCat = 0;
        for(int i=0; i<chances.size();i++)
        {
            if(chances.get(i)> chances.get(indexCat))
            {
                indexCat = i;
            }
        }

        return cat.get(indexCat);
    }

    private int totalWordsInFile(Map<String, Integer> words)
    {
        int result = 0;
        for(Map.Entry<String, Integer> entry: words.entrySet())
        {
            result+=entry.getValue();
        }

        return result;
    }
}
