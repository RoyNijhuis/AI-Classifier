package Controller;

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
    public static Map<String, Integer> readWordsFromFile(String path) throws IOException
    {
        String file = readFile(path);
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

    private static String readFile(String path) throws IOException
    {
        Charset encoding = Charset.defaultCharset();
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}
