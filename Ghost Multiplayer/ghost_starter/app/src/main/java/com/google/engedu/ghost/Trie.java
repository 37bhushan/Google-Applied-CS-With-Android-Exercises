package com.google.engedu.ghost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

 class TrieNode {
    char c;
    TrieNode parent;
    HashMap<Character, TrieNode> children = new HashMap<Character, TrieNode>();
    boolean isLeaf;

    public TrieNode() {}
    public TrieNode(char c){this.c = c;}
}


public class Trie {
    private TrieNode root;
    ArrayList<String> words;
    TrieNode prefixRoot;
    String curPrefix;

    public Trie()
    {
        root = new TrieNode();
        words  = new ArrayList<String>();
    }

    // Inserts a word into the trie.
    public void insert(String word)
    {
        HashMap<Character, TrieNode> children = root.children;

        TrieNode crntparent;

        crntparent = root;

        //cur children parent = root

        for(int i=0; i<word.length(); i++)
        {
            char c = word.charAt(i);

            TrieNode t;
            if(children.containsKey(c)){ t = children.get(c);}
            else
            {
                t = new TrieNode(c);
                t.parent = crntparent;
                children.put(c, t);
            }

            children = t.children;
            crntparent = t;

            //set leaf node
            if(i==word.length()-1)
                t.isLeaf = true;
        }
    }

    // Returns if the word is in the trie.
    public boolean isWord(String word)
    {
        TrieNode t = searchNode(word);
        if(t != null && t.isLeaf){return true;}
        else{return false;}
    }

    // Returns if there is any word in the trie
    // that starts with the given prefix.
    public String getAnyWordStartingWith(String prefix)
    {
        if(searchNode(prefix) == null) {return null;}
        else{return "Word";}
    }

    public TrieNode searchNode(String str)
    { Map<Character, TrieNode> children = root.children;
        TrieNode t = null;

        for(int i=0; i<str.length(); i++)
        {
            char c = str.charAt(i);
            if(children.containsKey(c))
            {
                t = children.get(c);
                children = t.children;
            }
            else{return null;}
        }

       // prefixRoot = t;
        curPrefix = str;
        words.clear();
        return t;
    }


    public String getGoodWordStartingWith(String s) {
        return null;
    }
}
