package com.ankon.problem2;

import com.ankon.problem2.lexer.Lexer;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.System.in;

public class Main {

    public static void main(String[] args) {

        ArrayList<String> set = new ArrayList<>();

        try {
            String content = readFile("Input.java", Charset.defaultCharset());
            set = getAllUniqueSubset(content);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (set != null && set.size() > 0) {
            for (String string: set) {
                // System.out.println(string);
            }

            HashMap<String, Integer> frequencyMap = new HashMap<String, Integer>();
            for (String string: set) {
                if (string.replaceAll("\\s", "").length() == 0)
                    continue;
                if (string.contains("\n"))
                    continue;

                if(frequencyMap.containsKey(string)) {
                    frequencyMap.put(string, frequencyMap.get(string) + 1);
                    System.out.println(string + " " + (frequencyMap.get(string)));
                }
                else {
                    frequencyMap.put(string, 1);
                    // System.out.println(string + " 1");
                }
            }
        }
    }

    public static ArrayList<String> getAllUniqueSubset(String str) {
        ArrayList<String> set = new ArrayList<String>();
        for (int i = 0; i < str.length(); i++) {
            for (int j = 0; j < str.length() - i; j++) {
                String elem = str.substring(j, j + (i+1));
//                if (!set.contains(elem)) {
//                    set.add(elem);
//                }
                set.add(elem);
            }
        }
        return set;
    }

    static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public static void lexFile(String path) {
        Lexer lexer = new Lexer(path);

        System.out.println("Lexical Analysis");
        System.out.println("-----------------");
        while (!lexer.isExhausted()) {
            System.out.printf("%-18s :  %s \n",lexer.currentLexema() , lexer.currentToken());
            lexer.moveAhead();
        }

        if (lexer.isSuccessful()) {
            System.out.println("Ok! :D");
        } else {
            System.out.println(lexer.errorMessage());
        }
    }

}
