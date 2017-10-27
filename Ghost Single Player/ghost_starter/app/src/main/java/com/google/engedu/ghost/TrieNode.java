package com.google.engedu.ghost;

import java.util.LinkedList;


public class TrieNode {
    char content;
         boolean isEnd;
         int count;
         LinkedList<TrieNode> childList;
   // private HashMap<String, TrieNode> children;
    //private boolean isWord;

    public TrieNode(char c) {
//        children = new HashMap<>();
//        isWord = false;
        childList = new LinkedList<TrieNode>();
                isEnd = false;
                content = c;
                count = 0;
    }
    public TrieNode subNode(char c)
         {
//             if (childList != null)
//                for (TrieNode eachChild : childList)
//                    if (eachChild.content == c)
//                       return eachChild;
               return null;
         }
    public void add(String s) {
//        HashMap<String,TrieNode>child = this.children;
//        for (int i= 0 ; i<s.length();i++){
//            char chr  = s.charAt(i);
//            TrieNode t;
//            if (children.containsKey(chr)){
//                t = children.get(String.valueOf(chr));
//            }else{
//                t = new TrieNode();
//                children.put(String.valueOf(chr),t);
//            }
//            child = t.children;
//        }
    }

    public String Search(String string){
//        HashMap<String,TrieNode>child = this.children;
//        for (int i=0 ;i<string.length();i++){
//            char chr = string.charAt(i);
//            TrieNode t;
//
//            }
    return null;
    }



    public boolean isWord(String s) {
      return false;
    }

    public String getAnyWordStartingWith(String s) {
        return null;
    }

    public String getGoodWordStartingWith(String s) {
        return null;
    }
}
