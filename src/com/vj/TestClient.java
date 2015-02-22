package com.vj;

import java.util.Collection;
import java.util.Scanner;

public class TestClient {
    public static void main(String[] args) {

        Unscramble ds = new Unscramble(args[0]);
        //ds.print_dot();

        System.out.println("ds.size = " + ds.size());
        System.out.println("NODE_COUNT = " + ds.size());

        Scanner scanner = new Scanner(System.in);
        System.out.println("On each line provide <letters> <space> <key character>\n\n");
        while (scanner.hasNextLine()) {
            Iterable<String> words;
            String letters;
            char keyLetter;

            String[] l = scanner.nextLine().split(" ");
            letters = l[0];
            if (l.length == 2) {
                keyLetter = l[1].charAt(0);
                words = ds.get(letters, keyLetter);
                System.out.print("get(" + letters + ", '" + keyLetter + "') = [ ");
            } else {
                words = ds.get(letters);
                System.out.print("get(" + letters + "') = [ ");
            }
            for (String w : words) {
                if (w.length()>=4)
                    System.out.print(w + ", ");
            }
            System.out.println("]");

            System.out.println("Get call count = " + ds.GET_NODES_VISITED);
            System.out.println("Matching words = " + ((Collection<?>)words).size());
        }
    }

}
