package com.vj;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public final class Unscramble {
    private Node root;
    private static final String LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final int NUMBER_OF_LETTERS = LETTERS.length();

    private class Node {
        ArrayList<String> value;
        Character key;
        Node yes, no;
    }

    public Unscramble() {
        //empty constructor
    }

    public Unscramble(String dictFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(dictFilePath))) {
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
        int keyCharPos = LETTERS.indexOf(keyChar);
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
        //System.out.println("GET x.key = [" + x.key + "], key = [" + key + "], index = [" + index + "], depth = [" + depth + "], keyCharPos = [" + keyCharPos + "], result = [" + result + "]");
        if (x == null) return;

        char c = key.charAt(index);


        if (keyCharPos != -1 &&  // there is a key char, all strings must include this char
            depth == keyCharPos &&  // this is the key char
            c != LETTERS.charAt(keyCharPos)) { //this char must be present, otherwise return
                result.clear();
                return;
        }


        //ignore repetitive chars
        while (index < key.length() - 1 && c == key.charAt(index + 1))
            c = key.charAt(++index);

        if (c == x.key) {  //yes path
            if (x.value != null && depth >= keyCharPos) result.addAll(x.value);
            if (index != key.length() - 1)    // No more chars to match
                get(x.yes, key, ++index, ++depth, keyCharPos, result); // traverse the yes path
        } else {//no path, don't progress the key index
            get(x.no, key, index, ++depth, keyCharPos, result);
        }
    }

    public final void put(String key) {
        char[] chars = key.toLowerCase().toCharArray();
        Arrays.sort(chars);

        root = put(root, key, String.copyValueOf(chars), 0, 0);
    }

    private final Node put(Node x, String key, String sortedKey, int index, int depth) {
        if (depth == NUMBER_OF_LETTERS) depth--;
        //System.out.println("PUT x.key = [" + LETTERS.charAt(depth) + "], sortedKey = [" + sortedKey + "], index = [" + index + "], depth = [" + depth + "]");
        if (x == null) {
            x = new Node();
            x.key = LETTERS.charAt(depth);
        }

        char c = sortedKey.charAt(index);
        //ignore repetitive chars
        while (index < sortedKey.length() - 1 && c == sortedKey.charAt(index + 1))
            c = sortedKey.charAt(++index);


        if (c > LETTERS.charAt(depth))  //No path
            x.no = put(x.no, key, sortedKey, index, ++depth);

        else if (index != key.length() - 1)
            x.yes = put(x.yes, key, sortedKey, ++index, ++depth);
        else {
            if (x.value == null) x.value = new ArrayList<String>();
            x.value.add(key);
            //System.out.println("x.key = " + x.key + ", word = " + key);
        }

        return x;
    }

    public static void main(String[] args) {
        Unscramble ds = new Unscramble();
        final String s = "emerald";
        ds.put(s);
        System.out.println(ds.get("merald", 'e'));
    }
}
