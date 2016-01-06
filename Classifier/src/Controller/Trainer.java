package Controller;

import Model.Category;

import java.util.ArrayList;
import java.util.Arrays;
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
	public static List<Category> train(List<Category> cat, int minnumber, int maxnumber)
    {
        //int totalWordsInFile = totalWordsInFile(toClassify);
		List<Category> cate = cat;
		
		ArrayList<String> stopwords = new ArrayList<String>(
			    Arrays.asList("a", "about", "above", "above", "across", "after", "afterwards", "again", "against", "all", "almost", "alone", "along", "already", "also","although","always","am","among", "amongst", "amoungst", "amount",  "an", "and", "another", "any","anyhow","anyone","anything","anyway", "anywhere", "are", "around", "as",  "at", "back","be","became", "because","become","becomes", "becoming", "been", "before", "beforehand", "behind", "being", "below", "beside", "besides", "between", "beyond", "bill", "both", "bottom","but", "by", "call", "can", "cannot", "cant", "co", "con", "could", "couldnt", "cry", "de", "describe", "detail", "do", "done", "down", "due", "during", "each", "eg", "eight", "either", "eleven","else", "elsewhere", "empty", "enough", "etc", "even", "ever", "every", "everyone", "everything", "everywhere", "except", "few", "fifteen", "fify", "fill", "find", "fire", "first", "five", "for", "former", "formerly", "forty", "found", "four", "from", "front", "full", "further", "get", "give", "go", "had", "has", "hasnt", "have", "he", "hence", "her", "here", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "him", "himself", "his", "how", "however", "hundred", "ie", "if", "in", "inc", "indeed", "interest", "into", "is", "it", "its", "itself", "keep", "last", "latter", "latterly", "least", "less", "ltd", "made", "many", "may", "me", "meanwhile", "might", "mill", "mine", "more", "moreover", "most", "mostly", "move", "much", "must", "my", "myself", "name", "namely", "neither", "never", "nevertheless", "next", "nine", "no", "nobody", "none", "noone", "nor", "not", "nothing", "now", "nowhere", "of", "off", "often", "on", "once", "one", "only", "onto", "or", "other", "others", "otherwise", "our", "ours", "ourselves", "out", "over", "own","part", "per", "perhaps", "please", "put", "rather", "re", "same", "see", "seem", "seemed", "seeming", "seems", "serious", "several", "she", "should", "show", "side", "since", "sincere", "six", "sixty", "so", "some", "somehow", "someone", "something", "sometime", "sometimes", "somewhere", "still", "such", "system", "take", "ten", "than", "that", "the", "their", "them", "themselves", "then", "thence", "there", "thereafter", "thereby", "therefore", "therein", "thereupon", "these", "they", "thickv", "thin", "third", "this", "those", "though", "three", "through", "throughout", "thru", "thus", "to", "together", "too", "top", "toward", "towards", "twelve", "twenty", "two", "un", "under", "until", "up", "upon", "us", "very", "via", "was", "we", "well", "were", "what", "whatever", "when", "whence", "whenever", "where", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", "whither", "who", "whoever", "whole", "whom", "whose", "why", "will", "with", "within", "without", "would", "yet", "you", "your", "yours", "yourself", "yourselves", "the"));
				
		//Feature selection filtering out words that are used less as the minnumber
		for(Category category: cate){
			//the old word list
			Map<String,Integer> words = category.getWords();
			
			//the new word list
			Map<String,Integer> newWords = new HashMap<String, Integer>();
			maxnumber = (maxnumber==-1?Integer.MAX_VALUE:maxnumber);
			//select words
			for(Entry<String,Integer> i: words.entrySet()){
				//filter
				
				if(i.getValue()> minnumber && i.getValue()<= maxnumber && !stopwords.contains(i.getValue())){
					System.out.println(maxnumber);
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
