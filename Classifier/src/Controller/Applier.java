package Controller;

import Model.Category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by Roy on 3-12-2015.
 */
public class Applier {
	
	//classify the new document
    public static Category classify(List<Category> cat, Map<String, Integer> toClassify)
    {
    	//map containing the results for every category
        Map<Float, Category> results = new HashMap<>();
        
        //keeps track of the biggest/best result
        float biggest = 0;
        
        //calculate probability for every category
        boolean first = true;
    	for(Category c: cat){
    		//add the log 2 prior to the result
    		float catResult = (float)(Math.log(c.getPrior())/Math.log(2));
    		
    		//retrieve the map with probabilties of words
    		Map<String,Float> words = c.getProbability();
    		
    		//for every word in the new document add the probability to the result
    		for(Entry<String,Integer> entry: toClassify.entrySet()){
				//check for null pointer in case there is a new word in the new document that does not occur in the vocabulary
    			catResult += (float)entry.getValue()*(float)(words.get(entry.getKey())==null?0:(float)words.get(entry.getKey()));
			}
    		
    		//store result and chance best value if this result is higher
    		results.put(catResult,c);
        	if(first){
        		biggest = catResult;
        		first = false;
        	} else {
        		if(catResult>biggest){
        			biggest = catResult;
        		}
        	}
        }
    	//return best category
        return results.get(biggest);
    }
}
