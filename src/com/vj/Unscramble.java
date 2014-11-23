package com.vj;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public final class Unscramble {
    private Node root;
    private static String letters = "abcdefghijklmnopqrstuvwxyz";


    private class Node {
        ArrayList<String> value;
        char key;
        Node yes, no;
    }

    public Unscramble(String dictFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(dictFilePath)))
        {

            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                put(sCurrentLine);
            }

        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }


    public boolean isEmpty() {
        return root == null;
    }


    public final Iterable<String> get(String key, char keyChar) {
        char[] chars = key.toLowerCase().toCharArray();
        Arrays.sort(chars);
        int keyCharPos = letters.indexOf(keyChar);
        ArrayList<String> result = new ArrayList<String>();
        get(root, String.copyValueOf(chars), 0, 0, keyCharPos, result);
        return result;
    }

    public final Iterable<String> get(String key) {
        char[] chars = key.toLowerCase().toCharArray();
        Arrays.sort(chars);
        ArrayList<String> result = new ArrayList<String>();
        get(root, String.copyValueOf(chars), 0, 0, -1, result);
        return result;
    }

    private final void get(Node x, String key, int index, int depth, int keyCharPos, ArrayList<String> result) {
//        System.out.println("x.key = [" + x.key + "], key = [" + key + "], index = [" + index + "], depth = [" + depth + "], keyCharPos = [" + keyCharPos + "], result = [" + result + "]");
        if (x == null) return;

        char c = key.charAt(index);

//        if (keyCharPos != -1 &&  // there is a key char, all strings must include this char
//            depth == keyCharPos &&  // this is the key char
//            c != letters.charAt(keyCharPos)) { //this char must be present, otherwise return
//                result.clear();
//                return;
//        }

        //ignore repetitive chars
        while (depth!=0 && index < key.length()-1 && c == letters.charAt(depth)-1)
            c = key.charAt(++index);



        if (c==x.key) {  //yes path
            if (x.value != null && depth >=keyCharPos) result.addAll(x.value);
            if (index != key.length() - 1)    // No more chars to match
                get(x.yes, key, ++index, ++depth, keyCharPos, result); // traverse the yes path
        }
        else {//no path, don't progress the key index
            get(x.no, key, index, ++depth, keyCharPos, result);
        }
    }

    public final void put(String key) {
        char[] chars = key.toLowerCase().toCharArray();
        Arrays.sort(chars);

        root = put(root, key, String.copyValueOf(chars), 0, 0);
    }

    private final Node put(Node x, String key,  String sortedKey, int index, int depth) {
        if (depth==26) depth--;
//        System.out.println("PUT x.key = [" + letters.charAt(depth) + "], sortedKey = [" + sortedKey + "], index = [" + index + "], depth = [" + depth + "]");
        if (x == null) {
            x = new Node();
            x.key = letters.charAt(depth);
        }

        char c = sortedKey.charAt(index);
        //ignore repetitive chars
        while (depth!=0 && index < sortedKey.length()-1 && c == letters.charAt(depth)-1)
            c = sortedKey.charAt(++index);


        if (c > letters.charAt(depth))  //No path
            x.no  = put(x.no, key, sortedKey, index, ++depth);

        else if(index != key.length()-1)
            x.yes = put(x.yes, key, sortedKey, ++index, ++depth);
        else {
            if (x.value == null) x.value = new ArrayList<String>();
            x.value.add(key);
        }

        return x;
    }
}
