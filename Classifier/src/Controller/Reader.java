package Controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Roy on 3-12-2015.
 */
public class Reader {
	
	//Read a file and return one big string
    public static String readFile(String path) throws IOException
    {
        Charset encoding = Charset.defaultCharset();
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
    
    //recursive function checking if path is a directory or a file
    //if it is a directory call itself for every file/directory in this directory and return a map containing all the obtained maps 
    //if it is a file return the map obtained from readFromFile(Path)
    public static Map<String, Integer> readFromFolder(String folderPath) throws IOException
    {
        File folder = new File(folderPath);
        if(folder.isDirectory())
        {
            File[] files = folder.listFiles();
            Map<String, Integer> allData = new HashMap<>();
            for(File f: files)
            {
                Map<String, Integer> result = readFromFolder(f.getAbsolutePath());
                result.forEach((k, v) -> allData.merge(k, v, (v1, v2) -> v1 + v2));
            }
            return allData;
        }
        else {
            return readFromFile(folderPath);
        }
    }
    
    //get the string of a file with readFile(path)
    //split the string, cast to lowercase and remove anything that is not a letter
    //return a map of all remaining words and how often each word occurs
    public static Map<String, Integer> readFromFile(String path) throws IOException
    {
        String file = Reader.readFile(path);
        file = file.replaceAll("[^a-zA-Z]", " ");
        file = file.toLowerCase();
        String[] array = file.split(" ");

        //Remove empty entries and create map
        Map<String, Integer> result = new HashMap<>();
        for(String s: array)
        {
            if(!s.isEmpty())
            {
                if(result.containsKey(s))
                {
                    result.put(s, result.get(s) + 1);
                }
                else
                {
                    result.put(s, 1);
                }
            }
        }

        return result;
    }
    
    //recursive function counting how many files there are in total 
    //if it is a directory call itself for every file/directory in this directory and return the result of all added together
    //if it is a file return 1
    public static int readAmountFiles(String folderPath) throws IOException
    {
    	int result = 0;
        File folder = new File(folderPath);
        if(folder.isDirectory())
        {
            File[] files = folder.listFiles();
            for(File f: files)
            {
            	result += readAmountFiles(f.getAbsolutePath());
            }
            return result;
        }
        else {
            return 1;
        }
    }
}
