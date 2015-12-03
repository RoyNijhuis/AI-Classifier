package Controller;

import Model.Category;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Roy on 3-12-2015.
 */
public class Trainer {

    public Map<String, Integer> trainFromFolder(String folderPath) throws IOException
    {
        File folder = new File(folderPath);
        if(folder.isDirectory())
        {
            File[] files = folder.listFiles();
            Map<String, Integer> allData = new HashMap<>();
            for(File f: files)
            {
                Map<String, Integer> result = trainFromFolder(f.getAbsolutePath());
                result.forEach((k, v) -> allData.merge(k, v, (v1, v2) -> v1 + v2));
            }
            return allData;
        }
        else {
            return trainFromFile(folderPath);
        }
    }

    public Map<String, Integer> trainFromFile(String path) throws IOException
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
}
