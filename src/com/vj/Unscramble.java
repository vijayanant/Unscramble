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
    private static int NODE_COUNT = 0;
    public static int GET_NODES_VISITED = 0;

    private class Node {
        ArrayList<String> value;
        Character key;
        Node yes, no;

        public Node () {
            NODE_COUNT++;
        }
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

    public int size() {
        return NODE_COUNT;
    }


    public final Iterable<String> get(String key, char keyChar) {
        GET_NODES_VISITED = 0;
        char[] chars = key.toLowerCase().toCharArray();
        Arrays.sort(chars);
        int keyCharPos = LETTERS.indexOf(keyChar);
        ArrayList<String> result = new ArrayList<String>();
        get(root, String.copyValueOf(chars), 0, 0, keyCharPos, result);
        return result;
    }

    public final Iterable<String> get(String key) {
        GET_NODES_VISITED = 0;
        char[] chars = key.toLowerCase().toCharArray();
        Arrays.sort(chars);
        ArrayList<String> result = new ArrayList<String>();
        get(root, String.copyValueOf(chars), 0, 0, -1, result);
        return result;
    }

    private final void get(Node x, String key, int index, int depth, int keyCharPos, ArrayList<String> result) {
        if (x == null) return;
        GET_NODES_VISITED++;
//        System.out.println("GET x.key = [" + x.key + "], key = [" + key + "], index = [" + index + "], depth = [" + depth + "], keyCharPos = [" + keyCharPos + "], result = [" + result + "]");

        char c = key.charAt(index);
        if (keyCharPos != -1 &&                // there is a key char, all strings must include this char
            depth == keyCharPos &&             // this is the key char
            c != LETTERS.charAt(keyCharPos)) { //this char must be present, otherwise return
                result.clear();
                return;
        }

        //ignore repetitive chars
        while (index < key.length() - 1 && c == key.charAt(index + 1))
            c = key.charAt(++index);

        if (c == x.key) {
            if (x.value != null && depth >= keyCharPos) result.addAll(x.value);
            if (index != key.length() - 1) {   // more chars to match
                // Traverse both ways
                get(x.yes, key, index + 1, depth + 1, keyCharPos, result); // traverse the 'yes' path
                get(x.no,  key, index + 1, depth + 1, keyCharPos, result); //traverse the 'no' path as well
            }
        } else {//no path, don't progress the key index
            get(x.no, key, index, depth+1, keyCharPos, result);
        }
    }

    public final void put(String key) {
        char[] chars = key.toLowerCase().toCharArray();
        Arrays.sort(chars);
        root = put(root, key, String.copyValueOf(chars), 0, 0);
    }

    private final Node put(Node x, String key, String sortedKey, int index, int depth) {
        if (depth == NUMBER_OF_LETTERS) depth--;
//        System.out.println("PUT x.key = [" + LETTERS.charAt(depth) + "], sortedKey = [" + sortedKey + "], index = [" + index + "], depth = [" + depth + "]");
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
//            System.out.println("x.key = " + x.key + ", word = " + key);
        }
        return x;
    }

    //print the tree formatted for graphing by dot
    public void print_dot() {
        System.out.println("Digraph BST {");
        print_dot(root);
        System.out.println("}");
    }
    public void print_dot(Node x){
        if(x == null){ return; }

        System.out.println("\t" + System.identityHashCode(x) + "\t [label=\"" + x.key+ "\"]");
        if(x.yes != null){
            System.out.println("\t"+ System.identityHashCode(x) +" -> "+ System.identityHashCode(x.yes) + "\t [label=\"yes\"]");
            print_dot(x.yes);
        }
        if(x.no != null){
            System.out.println("\t"+ System.identityHashCode(x) +" -> "+System.identityHashCode(x.no) + "\t [label=\"no\"]");
            print_dot(x.no);
        }
    }



    public static void main(String[] args) {
        Unscramble ds = new Unscramble();

        ds.put("blink");
        ds.put("blinker");
        ds.put("abc");
        ds.put("ac");

        System.out.println(ds.get("blinker", 'b'));
//        System.out.println("ds.size = " + ds.size());
    }
}
