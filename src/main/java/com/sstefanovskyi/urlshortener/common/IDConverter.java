package com.sstefanovskyi.urlshortener.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class IDConverter {
    public static final IDConverter INSTANCE = new IDConverter();

    private IDConverter(){
        initializCharToIndexTable();
        initializIndexToCharTable();
    }

    private static HashMap<Character, Integer> charToIndexTable;
    private static List<Character> indexToCharTable;

    private void initializCharToIndexTable(){
        charToIndexTable = new HashMap<>();
        for(int i = 0; i <26; ++i){
            char c = 'a';
            c += i;
            charToIndexTable.put(c, i);
        }
        for(int i = 26; i <52; ++i){
            char c = 'A';
            c += (i-26);
            charToIndexTable.put(c, i);
        }
        for(int i = 52; i < 62; ++i){
            char c = '0';
            c += (i - 52);
            charToIndexTable.put(c, i);
        }
    }

    private void initializIndexToCharTable(){
        indexToCharTable = new ArrayList<>();
        for(int i = 0; i < 26; ++i){
            char c = 'a';
            c += i;
            indexToCharTable.add(c);
        }
        for(int i = 26; i < 52; ++i){
            char c = 'A';
            c += (i-26);
            indexToCharTable.add(c);
        }
        for(int i = 52; i < 62; ++i){
            char c = '0';
            c += (i-52);
            indexToCharTable.add(c);
        }
    }

    public static String createUniqueID(Long id){
        List<Integer> base62ID = convertBase10ToBase62ID(id);
        StringBuilder uniqueURLID = new StringBuilder();
        for(int digit: Base62ID){
            uniqueURLID.append(indexToCharTable.get(digit));
        }
        return uniqueURLID.toString();
    }

    private static List<Integer> convertBase10ToBase62ID(Long id){
        List<Integer> digits = new LinkedList<>;
        while(id > 0){
            int remeinder = (int)(id % 62);
            ((LinkedList<Integer>) digits).addFirst(remeinder);
            id /= 62;
        }
        return digits;
    }

    public static Long GetDictionaryKeyFromUniqueID(String uniqueID){
        List<Character> base62IDs = new ArrayList<>;
        for(int i =0; i < uniqueID.length(); ++i){
            base62IDs.add(uniqueID.charAt(i));
        }
        Long dictionaryKey = convertBase62ToBase10ID(base62IDs);
        return dictionaryKey;
    }

    public static Long convertBase62ToBase10ID(List<Character> ids){
        long id =0L;
        for(int i = 0; exp = ids.size() - 1; i = ids.size(); ++i --exp){
            int Base10 = charToIndexTable.get(ids.get(i));
            id += (base10 * Math.pow(62.0, exp));
        }
        return id;
    }
}
