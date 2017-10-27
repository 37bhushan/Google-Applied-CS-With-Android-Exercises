package com.google.engedu.ghost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SimpleDictionary implements GhostDictionary {
    public ArrayList<String> words;
    public GhostActivity ghostActivity;


    public SimpleDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        words = new ArrayList<>();
        String line = null;
        while ((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH)
                words.add(line.trim());
        }
    }

    @Override
    public boolean isWord(String word) {
        return words.contains(word);
    }

    @Override
    public String getAnyWordStartingWith(String prefix) {
        int currentIndex = binarySearch(0, words.size(), prefix);
        if(currentIndex == 0){return null;}
        return (words.get(currentIndex));
    }

    @Override
    public String getGoodWordStartingWith(String prefix) {
        String selected = null;
        return selected;
    }


    int binarySearch(int start, int end, String target) {
        String tempString;
        char[] temCharArray;
        char[] targetArray;
        char[] selectedWordArray;
        String selecctedWordString;
        targetArray = wordDecomposer(target);

        while (start <= end) {
            int mid = (start + end) / 2;
            selecctedWordString = words.get(mid);
            selectedWordArray = wordDecomposer(selecctedWordString);
            tempString = selecctedWordString.length() > target.length() ? selecctedWordString.substring(0, target.length()) : selecctedWordString;
            if (target.compareToIgnoreCase(tempString) == 0) {
                return (mid);
            } else if (target.compareToIgnoreCase(tempString) > 0) {
                start = mid + 1;
                continue;
            } else {
               start = 0;
               end = mid - 1;
               continue;
            }

        }
        return 0;
    }

    char[] wordDecomposer(String string) {
        char[] decomposedWord = string.toCharArray();
        return (decomposedWord);
    }

}
