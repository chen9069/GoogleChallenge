import java.util.*;
public class spySnippets {   
    public static String answer(String document, String[] searchTerms) { 

        // Your code goes here.
        Map<String, Integer> map = new HashMap<String, Integer>();
        Set<String> set = new HashSet<String>();
        Set<String> items = new HashSet<String>();
        int minLen = Integer.MAX_VALUE, minStart = 0;
        for (String s : searchTerms) {
            set.add(s);
            items.add(s);
        }
        String[] documentWords = document.split(" ");
        int start = 0;
        for (int end = 0; end < documentWords.length; end ++) {
            String s = documentWords[end];
            if (!map.containsKey(s))
              	map.put(s, 0);
            int value = map.get(s) + 1;
            map.put(s, value);
            if (!set.isEmpty()) {
                if (set.contains(s)) {
                    set.remove(s);
                }
                if (set.isEmpty()) {
                    minLen = end - start;
                    String key = documentWords[start];
                    while (!items.contains(key) || map.get(key) > 1) {
                        map.put(key, map.get(key) - 1);
                        key = documentWords[++start];
                    }
                    if (end - start < minLen) {
                        minLen = end - start;
                        minStart = start;
                    }
                }
                continue;
            } 
            String key = documentWords[start];
            while (!items.contains(key) || map.get(key) > 1) {
                map.put(key, map.get(key) - 1);
                key = documentWords[++start];
            }
            if (end - start < minLen) {
                minLen = end - start;
                minStart = start;
            }
        }
        StringBuilder sb = new StringBuilder(documentWords[minStart]);
        for (int i = minStart + 1; i <= minStart + minLen; i ++) {
            sb.append(" ");
            sb.append(documentWords[i]);
        }
        return sb.toString();
    } 
}