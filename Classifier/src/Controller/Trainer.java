package Controller;

import Model.Category;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by Roy on 3-12-2015.
 */
public class Trainer {

	public static Map<Category, Map<String,Double>> train(List<Category> cat)
    {
        //int totalWordsInFile = totalWordsInFile(toClassify);
		Map<Category, Map<String,Double>> result = new HashMap<>();
        Map<String, Integer> vocab = new HashMap<>();
        for(Category category: cat)
        {
            vocab.putAll(category.getWords());
        }
        
        int differentWords = vocab.size();
                
        // for every category
        for(Category i: cat){
        	int amountOfWords = 0;
        	Map<String,Double> resultCat = new HashMap<>();       
        	//for every word
        	Map<String,Integer> map = i.getWords();
        	for(Entry<String, Integer> entry: map.entrySet()){
        		amountOfWords += entry.getValue();
        	}
        	
        	for(Entry<String, Integer> entry: map.entrySet()){
        		int value = map.get(entry.getKey())==null?0:map.get(entry.getKey());
                double chance = Math.log((((float)value+1f)/((float)amountOfWords+(float)differentWords)))/Math.log(2);
                System.out.println("c" + chance);
                resultCat.put(entry.getKey(), chance);
        	}
        	result.put(i, resultCat);
        	
        }
        
        return result;
    }
}
