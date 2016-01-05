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

    public static Category classify(List<Category> cat, Map<String, Integer> toClassify)
    {
        Map<Float, Category> results = new HashMap<>();
        float biggest = 0;
        boolean first = true;
    	for(Category c: cat){
    		float catResult = (float)(Math.log(c.getPrior())/Math.log(2));
    		Map<String,Float> words = c.getProbability();
    		for(Entry<String,Integer> entry: toClassify.entrySet()){
				catResult += (float)entry.getValue()*(float)(words.get(entry.getKey())==null?0:(float)words.get(entry.getKey()));
					//System.out.println((float)entry.getValue()*(float)(words.get(entry.getKey())==null?0:(float)words.get(entry.getKey())));
    		}
    		//System.out.println(catResult + " " + c.getName() + " " + c.getPrior());
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
        return results.get(biggest);
    }
}
