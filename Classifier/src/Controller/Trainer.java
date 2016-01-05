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
	
	//Train using the known documents and categories
	public static List<Category> train(List<Category> cat, int minnumber)
    {
        //int totalWordsInFile = totalWordsInFile(toClassify);
		List<Category> cate = cat;
		
		
		//Feature selection filtering out words that are used less as the minnumber
		for(Category category: cate){
			//the old word list
			Map<String,Integer> words = category.getWords();
			
			//the new word list
			Map<String,Integer> newWords = new HashMap<String, Integer>();
			
			//select words
			for(Entry<String,Integer> i: words.entrySet()){
				//filter
				if(i.getValue()>minnumber){
					newWords.put(i.getKey(), i.getValue());
				}
			}
			category.setWords(newWords);
		}
		
		//Map with all words in the vocabulary
		Map<String, Integer> vocab = new HashMap<>();
        //Total amount of documents
		int N = 0;
        //create the vocabulary
		for(Category category: cate)
        {
            vocab.putAll(category.getWords());
            N += category.getDocs();
        }
        //Amount of different words in the vocabulary
        int differentWords = vocab.size();
                
        // for every category train
        for(Category i: cate){
        	//result containing a map from word to probability
        	Map<String,Float> resultCat = new HashMap<>();       
        	
        	//calculate the prior for this category based on the amount of documents
        	i.setPrior((float)i.getDocs()/(float)N);
        	
        	//total amount of words in this category
        	int amountOfWords = 0;
        	
        	//calculate total amount of words in this category	
        	Map<String,Integer> map = i.getWords();
        	for(Entry<String, Integer> entry: map.entrySet()){
        		amountOfWords += entry.getValue();
        	}
        	
        	//For every word in the vocabulary calculate the probability
        	for(Entry<String, Integer> entry: vocab.entrySet()){
        		//check for null pointer
        		int value = map.get(entry.getKey())==null?0:map.get(entry.getKey());
        		
        		//add the smoothing in case this category does not have a word from the vocabulary
        		float top = (float)(value+SMOOTHING);
        		
        		//preparing the second part of the equation
        		float bot = (float)(amountOfWords+SMOOTHING*differentWords);
                
        		//divide and store as log 2
        		float chance = (float)(Math.log(top/bot)/Math.log(2));	
        		resultCat.put(entry.getKey(), chance);
        	}
        	
        	//add the probabilities to the category
        	i.setProbability(resultCat);
        	
        }
        
        return cate;
    }
}
