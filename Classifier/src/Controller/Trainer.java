package Controller;

import Model.Category;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by Roy on 3-12-2015.
 */
public class Trainer {
	private static final float SMOOTHING = 1; 
	public static List<Category> train(List<Category> cat)
    {
        //int totalWordsInFile = totalWordsInFile(toClassify);
		List<Category> cate = cat;
		Map<String, Integer> vocab = new HashMap<>();
        for(Category category: cate)
        {
            vocab.putAll(category.getWords());
        }
        
        int differentWords = vocab.size();
                
        // for every category
        for(Category i: cate){
        	int amountOfWords = 0;
        	Map<String,Float> resultCat = new HashMap<>();       
        	//for every word
        	Map<String,Integer> map = i.getWords();
        	for(Entry<String, Integer> entry: map.entrySet()){
        		amountOfWords += entry.getValue();
        	}
        	
        	for(Entry<String, Integer> entry: vocab.entrySet()){
        		int value = map.get(entry.getKey())==null?0:map.get(entry.getKey());
        		float top = (float)(value+SMOOTHING);
        		float bot = (float)(amountOfWords+SMOOTHING*differentWords);
                float chance = (float)(Math.log(top/bot)/Math.log(2));
               resultCat.put(entry.getKey(), chance);
        	}
        	i.setProbability(resultCat);
        	
        }
        
        return cate;
    }
}
