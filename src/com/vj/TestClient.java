package com.vj;

import java.util.Scanner;

public class TestClient {
    public static void main(String[] args) {

        Unscramble ds = new Unscramble(args[0]);

        Scanner scanner = new Scanner(System.in);
        System.out.println("On each line provide <letters> <space> <key character>\n\n");
        while (scanner.hasNextLine()) {
            String letters = scanner.next();
            char keyLetter = scanner.next().trim().charAt(0);
            System.out.println("get(" + letters + ", " + keyLetter + ") = " + ds.get(letters,keyLetter));
        }
    }

}
