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
	public static List<Category> train(List<Category> cat, int minnumber)
    {
        //int totalWordsInFile = totalWordsInFile(toClassify);
		List<Category> cate = cat;
		
		
		//Feature selection filtering out words that are used less as the minnumber
		for(Category category: cate){
			Map<String,Integer> words = category.getWords();
			Map<String,Integer> newWords = new HashMap<String, Integer>();
			for(Entry<String,Integer> i: words.entrySet()){
				if(i.getValue()>minnumber){
					newWords.put(i.getKey(), i.getValue());
				}
			}
			category.setWords(newWords);
		}
		
		
		Map<String, Integer> vocab = new HashMap<>();
        int N = 0;
		for(Category category: cate)
        {
            vocab.putAll(category.getWords());
            N += category.getDocs();
        }
        
        int differentWords = vocab.size();
                
        // for every category
        for(Category i: cate){
        	int amountOfWords = 0;
        	Map<String,Float> resultCat = new HashMap<>();       
        	
        	i.setPrior((float)i.getDocs()/(float)N);
        	
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
                System.out.println(chance + " " + value + " " + entry.getKey());
               resultCat.put(entry.getKey(), chance);
        	}
        	i.setProbability(resultCat);
        	
        }
        
        return cate;
    }
}
