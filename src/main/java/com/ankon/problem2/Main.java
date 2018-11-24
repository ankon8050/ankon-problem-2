package com.ankon.problem2;

import com.ankon.problem2.domain.Frequency;
import com.ankon.problem2.domain.Result;
import com.ankon.problem2.lexer.Lexer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import org.apache.commons.codec.binary.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import static java.lang.System.in;

public class Main {
    static ArrayList<Result> results = new ArrayList<>();
    static ArrayList<Frequency> tokens = new ArrayList<>();

    public static void main(String[] args) {
        String[] contents = new String[2];

        try {
            contents[0] = fetchGitFile("https://gitlab.com/ankon/problem-1/raw/master/src/main/java/com/ankon/problem1/FetchCommit.java");
            contents[1] = fetchGitFile("https://gitlab.com/ankon/problem-1/raw/master/src/main/java/com/ankon/problem1/domain/Result.java");

            System.out.println(longestSubstring(contents[0].replaceAll("\\s+", " "), contents[1].replaceAll("\\s+", " ")));
//            content = fetchGitFile();
//            content1 = readFile("Input.java", Charset.defaultCharset());

//            String[] contents = content.split("\n");
//
//            // System.out.println(contents.length);
//            if (contents != null && contents.length > 0) {
//                for (String string: contents) {
//                    // System.out.println(string);
//                    generateTokens(string);
//                }
//
//                generateResultSet();
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Done!");
    }

     static String longestSubstring(String str1, String str2) {

        StringBuilder sb = new StringBuilder();
        if (str1 == null || str1.isEmpty() || str2 == null || str2.isEmpty())
            return "";

        // ignore case
        // str1 = str1.toLowerCase();
        // str2 = str2.toLowerCase();

        // java initializes them already with 0
        int[][] num = new int[str1.length()][str2.length()];
        int maxlen = 0;
        int lastSubsBegin = 0;

        for (int i = 0; i < str1.length(); i++) {
            for (int j = 0; j < str2.length(); j++) {
                if (str1.charAt(i) == str2.charAt(j)) {
                    if ((i == 0) || (j == 0))
                        num[i][j] = 1;
                    else
                        num[i][j] = 1 + num[i - 1][j - 1];

                    if (num[i][j] > maxlen) {
                        maxlen = num[i][j];
                        // generate substring from str1 => i
                        int thisSubsBegin = i - num[i][j] + 1;
                        if (lastSubsBegin == thisSubsBegin) {
                            //if the current LCS is the same as the last time this block ran
                            sb.append(str1.charAt(i));
                        } else {
                            //this block resets the string builder if a different LCS is found
                            lastSubsBegin = thisSubsBegin;
                            sb = new StringBuilder();
                            sb.append(str1.substring(lastSubsBegin, i + 1));
                        }
                    }
                }
            }}

        return sb.toString();
    }

    static void generateTokens(String content) {
        ArrayList<Frequency> set = new ArrayList<>();

        set = getAllUniqueSubset(content);
        // System.out.println(set.toString());

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

                    // System.out.println(count);
                }

                Frequency temp = new Frequency(frequency.getSubString(), count);

                boolean flag = false;
                int k = 0;
                for ( ; k < tokens.size(); k++) {
                    if (tokens.get(k).getSubString().equals(temp.getSubString())) {
                        flag = true;
                        break;
                    }
                }

                if (flag) {
                    temp = tokens.get(k);
                    tokens.remove(k);
                    temp.setFrequency(frequency.getFrequency() + 1);

                    // System.out.println(frequency);
                } else {
                    temp = new Frequency(temp.getSubString(), 1);

                    // System.out.println(frequency);
                }

                tokens.add(temp);
                // System.out.println(temp.getSubString() + " " + count);
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

    static void generateResultSet() {
        for (Frequency token: tokens) {
            if (token.getFrequency() <= 1)
                continue;

            Result result = countScore(token);
            if (result != null && result.getScore() != 0.0)
                results.add(result);
        }

        for (Result result: results) {
            System.out.println(result.getScore() + " " + result.getTokens() + " " + result.getCount() + " " + result.getSourceCode());
        }
    }

    static String fetchGitFile(String repo) {
        URL url;
        String username = "ankon", password = "16171726", file = "";

        try {
            url = new URL(repo);
            URLConnection uc;
            uc = url.openConnection();

            uc.setRequestProperty("X-Requested-With", "Curl");
            ArrayList<String> list = new ArrayList<String>();
            String userpass = username + ":" + password;
            String basicAuth = "Basic " + new String(new Base64().encode(userpass.getBytes())); //needs Base64 encoder, apache.commons.codec
            uc.setRequestProperty("Authorization", basicAuth);

            BufferedReader reader = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null)
                file=file+line+"\n";
            // System.out.println(file);
            return file;

        } catch (IOException e) {
            System.out.println("Wrong username and password");
            return null;

        }
    }

    static ArrayList<Frequency> getAllUniqueSubset(String str) {
        ArrayList<Frequency> set = new ArrayList<Frequency>();

        // System.out.println(str.length());

        for (int i = 0; i < str.length(); i++) {
            for (int j = 0; j < str.length() - i; j++) {
                boolean flag = false;
                String elem = str.substring(j, j + (i+1));
//                if (!set.contains(elem)) {
//                    set.add(new Frequency(elem, 1));
//                }

                if (elem.isEmpty())
                    continue;
                if (elem.replaceAll("\\s", "").length() == 0)
                    continue;
                if (elem.contains("\n"))
                    continue;

                if ((j - 1) >= 0) {
                    if (str.charAt(j - 1) != ' '
                            && str.charAt(j - 1) != '\n'
                            && str.charAt(j - 1) != '.')
                        continue;
                }

                if ((j + (i + 1)) < str.length()) {
                    if (str.charAt(j + (i + 1)) != ' '
                            && str.charAt(j + (i + 1)) != '\n'
                            && str.charAt(j + (i + 1)) != ';')
                        continue;

                    //  System.out.println(str.charAt(j - 1) + " " + elem + " " + str.charAt(j + (i + 1)));
                }

//                if ((j - 1) >= 0 && (j + (i + 1)) < str.length())
//                    System.out.println(str.charAt(j - 1) + " " + elem + " " + str.charAt(j + (i + 1)));
//                else System.out.println("false: " + elem);

                // elem = elem.trim();
//                else {
//                    continue;
//                }

                // System.out.println(elem);

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

                    // System.out.println(frequency);
                } else {
                    Frequency frequency = new Frequency(elem, 1);
                    set.add(frequency);

                    // System.out.println(frequency);
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
            // System.out.println(lexer.errorMessage());
            return -1;
        }
    }

    static Result countScore(Frequency frequency) {
        int tokens = lexString(frequency.getSubString());

        Double score = 0.0;
        if (tokens != -1)
            score = (Math.log(frequency.getFrequency()) / Math.log(2)) * (Math.log(tokens) / Math.log(2));

        // System.out.println(score + " " + tokens + " " + frequency.getFrequency() + " " + frequency.getSubString());
        DecimalFormat df = new DecimalFormat("0.00");
        if (tokens != -1 && !Double.isNaN(score))
            return new Result(Double.parseDouble(df.format(score)), tokens, frequency.getFrequency(), frequency.getSubString());

        return null;
    }

}
