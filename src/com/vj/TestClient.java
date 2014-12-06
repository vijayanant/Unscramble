package com.vj;

import java.util.Scanner;

public class TestClient {
    public static void main(String[] args) {

        Unscramble ds = new Unscramble(args[0]);
        System.out.println("ds.size = " + ds.size());
        System.out.println("NODE_COUNT = " + ds.size());

        Scanner scanner = new Scanner(System.in);
        System.out.println("On each line provide <letters> <space> <key character>\n\n");
        while (scanner.hasNextLine()) {
            String letters = scanner.next();
            char keyLetter = scanner.next().trim().charAt(0);
            Iterable<String> words = ds.get(letters, keyLetter);
            System.out.println("get(" + letters + ", " + keyLetter + ") = " + words);
//            System.out.println("Get call count = " + ds.GET_CALL_COUNT);
        }
    }

}
