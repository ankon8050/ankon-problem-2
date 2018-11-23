package com.ankon.problem2;

import com.ankon.problem2.domain.Frequency;
import com.ankon.problem2.domain.Result;
import com.ankon.problem2.lexer.Lexer;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.System.in;

public class Main {

    public static void main(String[] args) {

        ArrayList<Frequency> set = new ArrayList<>();
        ArrayList<Frequency> tokens = new ArrayList<>();
        ArrayList<Result> results = new ArrayList<>();

        String content = "";
        try {
            content = readFile("Input.java", Charset.defaultCharset());
            set = getAllUniqueSubset(content);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int lastIndex = 0;
        int count = 0;

        if (set != null && set.size() > 0) {
            for (Frequency frequency: set) {
                lastIndex = 0;
                count = 0;
                while(lastIndex != -1){

                    lastIndex = content.indexOf(frequency.getSubString(), lastIndex);

                    if(lastIndex != -1){
                        count++;
                        lastIndex += frequency.getSubString().length();
                    }
                }

                tokens.add(new Frequency(frequency.getSubString(), count));
                // System.out.println(frequency.getSubString() + " " + count);
            }

            for (Frequency token: tokens) {
                if (token.getFrequency() <= 1)
                    continue;

                Result result = countScore(token);
                if (result != null)
                    results.add(result);
            }

            for (Result result: results) {
                System.out.println(result.getScore() + " " + result.getTokens() + " " + result.getCount() + " " + result.getSourceCode());
            }

//            ArrayList<String> substrings = new ArrayList<>();
//            for (int i = 0; i < set.size(); i++) {
//                substrings.add(set.get(i).trim());
//            }
//
//            HashMap<String, Integer> frequencyMap = new HashMap<String, Integer>();
//            for (String string: set) {
//                if (string.replaceAll("\\s", "").length() == 0)
//                    continue;
//                if (string.contains("\n"))
//                    continue;
//
//                if(frequencyMap.containsKey(string)) {
//                    frequencyMap.put(string, frequencyMap.get(string) + 1);
//                    System.out.println(string + " " + (frequencyMap.get(string)));
//                }
//                else {
//                    frequencyMap.put(string, 1);
//                    // System.out.println(string + " 1");
//                }
//            }
        }
    }

    static ArrayList<Frequency> getAllUniqueSubset(String str) {
        ArrayList<Frequency> set = new ArrayList<Frequency>();
        for (int i = 0; i < str.length(); i++) {
            for (int j = 0; j < str.length() - i; j++) {
                boolean flag = false;
                String elem = str.substring(j, j + (i+1)).trim();
                // System.out.println(elem);
//                if (!set.contains(elem)) {
//                    set.add(new Frequency(elem, 1));
//                }

                if (elem.isEmpty())
                    continue;
                if (elem.replaceAll("\\s", "").length() == 0)
                    continue;
                if (elem.contains("\n"))
                    continue;

                int k = 0;
                for ( ; k < set.size(); k++) {
                    if (set.get(k).getSubString().equals(elem)) {
                        flag = true;
                        break;
                    }
                }

                if (flag) {
                    Frequency frequency = set.get(k);
                    set.remove(k);
                    frequency.setFrequency(frequency.getFrequency() + 1);
                    set.add(frequency);
                } else {
                    set.add(new Frequency(elem, 1));
                }
                // set.add(elem);
            }
        }
        return set;
    }

    static ArrayList<String> getUniqueSubstrings(String str) {
        ArrayList<String> set = new ArrayList<>();

        for (int i = 0; i < str.length(); i++) {
            for (int j = i + 1; j < str.length() + 1; j++) {
                String substring = str.substring(i, j);

                if (str.indexOf(substring, i + substring.length()) == -1) {
                    if (substring.replaceAll("\\s", "").length() == 0)
                        continue;
                    if (substring.contains("\n"))
                        continue;

                    // System.out.println("Unique Substring: " + substring);
                    set.add(substring);
                }
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

    static Integer lexString(String content) {
        Lexer lexer = new Lexer(content, true);

        int count = 0;

        // System.out.println("Lexical Analysis");
        // System.out.println("-----------------");
        while (!lexer.isExhausted()) {
            // System.out.printf("%-18s :  %s \n",lexer.currentLexema() , lexer.currentToken());
            count++;
            lexer.moveAhead();
        }

        if (lexer.isSuccessful()) {
            // System.out.println("Ok! :D");
            return count;
        } else {
            System.out.println(lexer.errorMessage());
            return -1;
        }
    }

    static Result countScore(Frequency frequency) {
        int tokens = lexString(frequency.getSubString());

        Double score = 0.0;
        if (tokens != -1)
            score = Math.log(frequency.getFrequency()) / Math.log(2);

        // System.out.println(score + " " + tokens + " " + frequency.getFrequency() + " " + frequency.getSubString());
        DecimalFormat df = new DecimalFormat("0.00");
        if (tokens != -1 && !Double.isNaN(score))
            return new Result(Double.parseDouble(df.format(score)), tokens, frequency.getFrequency(), frequency.getSubString());

        return null;
    }

}
