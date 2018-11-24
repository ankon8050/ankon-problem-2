package com.ankon.problem2;

import com.ankon.problem2.domain.Frequency;
import com.ankon.problem2.domain.Result;
import com.ankon.problem2.domain.ResultSet;
import com.ankon.problem2.lexer.Lexer;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.*;

import com.google.common.collect.Sets;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import java.util.regex.Pattern;

import static java.lang.System.exit;
import static java.lang.System.in;
import static java.lang.System.setOut;

public class Main {
    static ArrayList<Result> results = new ArrayList<>();
    static ArrayList<Frequency> tokens = new ArrayList<>();

    static List<ResultSet> resultSets = new ArrayList<>();

    static String username, password, inputFile, outputFile;

    public static void main(String[] args) {
        try {
            authenticate();
        } catch (Exception e) {
            e.printStackTrace();
            exit(0);
        }

        try {
            String[] links = prepareContent();
            String[] contents;
            if (links != null && links.length > 0) {
                contents = new String[links.length];

                for (int i = 0; i < links.length; i++) {
                    System.out.println("Importing file: " + (i + 1));
                    contents[i] = fetchGitFile(links[i]);
                }

                System.out.println("\n");
                System.out.println("--------------------Substring Tokenize---------------------");
                for (String content: contents) {
                    results = new ArrayList<>();
                    tokens = new ArrayList<>();

                    String[] strings = content.split("\n");

                    if (strings.length > 0) {
                        for (String string : strings) {
                            generateTokens(string.replaceAll("\\s+", " "));
                        }

                        generateResultSet();

                        resultSets.add(new ResultSet(results));
                    }

                    // System.out.println("\n\n");
                }

                listCommonSequences();

                System.out.println("--------------------Substring Tokenize---------------------");
                System.out.println("\n\n");

                System.out.println("--------------------LCS string---------------------");
//                String lcs = longestSubstring(contents[0].replaceAll("\\s+", " "), contents[1].replaceAll("\\s+", " "));
//
//                int count = 0;
//
//                if (lcs != null && !lcs.isEmpty()) {
//                    count += StringUtils.countMatches(contents[0], lcs);
//                    count += StringUtils.countMatches(contents[1], lcs);
//                }
//
//                Frequency frequency = new Frequency(lcs, count);
//                System.out.println(countScore(frequency));
//                System.out.println();
//                System.out.println();
                System.out.println("--------------------LCS string---------------------");
                System.out.println("\n\n");

                System.out.println("--------------------LCS set---------------------");
//                Set<String> cs = longestCommonSubstrings(contents[0].replaceAll("\\s+", " "), contents[1].replaceAll("\\s+", " "));
//
//                if (cs != null && cs.size() > 0) {
//                    for (String st: cs) {
//                        System.out.println(st);
//                    }
//                }
                System.out.println("--------------------LCS set---------------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Done!");
    }

    /**
     * Authenticate GitHub credentials
     * @throws IOException
     * @throws Exception
     */
    static void authenticate() throws IOException, Exception {
        String credentials = readFile("Credentials.rtf", Charset.defaultCharset());

        if (credentials.isEmpty())
            throw new Exception("Credentials not found!");

        String[] split = credentials.split("\n");
        username = split[0];
        password = split[1];
        inputFile = split[2];
        outputFile = split[3];
    }

    /**
     * Initialize data set from set of URLs
     *
     * @return
     * @throws IOException
     */
    static String[] prepareContent() throws IOException {
        String listOfURL = readFile(inputFile, Charset.defaultCharset());

        if (listOfURL.isEmpty())
            return null;

        String[] content = listOfURL.split("\n");

        return content;
    }

    /**
     * LCS to return single string
     *
     * @param str1
     * @param str2
     * @return
     */
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

                        // System.out.println(sb.toString());
                    }
                }
            }
        }

        return sb.toString();
    }

    /**
     * LCS to return multiple strings
     *
     * @param s
     * @param t
     * @return
     */
    static Set<String> longestCommonSubstrings(String s, String t) {
        int[][] table = new int[s.length()][t.length()];
        int longest = 0;
        Set<String> result = new HashSet<>();

        for (int i = 0; i < s.length(); i++) {
            for (int j = 0; j < t.length(); j++) {
                if (s.charAt(i) != t.charAt(j)) {
                    continue;
                }

                table[i][j] = (i == 0 || j == 0) ? 1
                        : 1 + table[i - 1][j - 1];
                if (table[i][j] > longest) {
                    longest = table[i][j];
                    result.clear();
                }
                if (table[i][j] == longest) {
                    result.add(s.substring(i - longest + 1, i + 1));
                }
            }
        }
        return result;
    }

    /**
     * Find out all tokens in "content"
     * @param content
     */
    static void generateTokens(String content) {
        ArrayList<Frequency> set = new ArrayList<>();

        set = getAllUniqueSubset(content.replaceAll("\\s+", " "));

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

                Frequency temp = new Frequency(frequency.getSubString(), count);
                String subString = temp.getSubString();
                temp.setSubString(temp.getSubString().trim());

                boolean flag = false, skip = false;
                int k = 0;
                for ( ; k < tokens.size(); k++) {
                    if (!tokens.get(k).getSubString().equals(subString)
                            && tokens.get(k).getSubString().equals(temp.getSubString())) {
                        skip = true;
                        break;
                    }

                    if (tokens.get(k).getSubString().equals(temp.getSubString())) {
                        flag = true;
                        break;
                    }
                }

                if (flag) {
                    temp = tokens.get(k);
                    tokens.remove(k);
                    temp.setFrequency(temp.getFrequency() + 1);
                    tokens.add(temp);
                } else if (!flag && !skip) {
                    temp = new Frequency(temp.getSubString(), 1);
                    tokens.add(temp);
                }
            }
        }
    }

    /**
     * Generate the result set for each .java file
     */
    static void generateResultSet() {
        for (Frequency token: tokens) {
            if (token.getFrequency() <= 1) {
                // System.out.println(token);
                continue;
            }

            Result result = countScore(token);
            if (result != null && result.getCount() > 1)
                results.add(result);
        }

        for (Result result: results) {
            /**
             * Uncomment to check the console trace of result set generated for each file
             */
            // System.out.println(result.getScore() + " " + result.getTokens() + " " + result.getCount() + " " + result.getSourceCode());
        }
    }

    /**
     * Read file from URL
     *
     * @param repo
     * @return
     */
    static String fetchGitFile(String repo) {
        URL url;
        String file = "";

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

    /**
     * List down all the sequences in "str"
     *
     * @param str
     * @return
     */
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

//                if (elem.contains("for"))
//                    System.out.println(elem);

                if ((j - 1) >= 0) {
                    if (str.charAt(j - 1) != ' '
                            && str.charAt(j - 1) != '\n'
                            && str.charAt(j - 1) != '.')
                        continue;
                }

//                if (elem.contains("for"))
//                    System.out.println(elem);

                if ((j + (i + 1)) < str.length()) {
                    if (str.charAt(j + (i + 1)) != ' '
                            && str.charAt(j + (i + 1)) != '\n'
                            && str.charAt(j + (i + 1)) != ';')
                        continue;

                    //  System.out.println(str.charAt(j - 1) + " " + elem + " " + str.charAt(j + (i + 1)));
                }

//                if (elem.contains("for"))
//                    System.out.println(elem + "\n");

//                if ((j - 1) >= 0 && (j + (i + 1)) < str.length())
//                    System.out.println(str.charAt(j - 1) + " " + elem + " " + str.charAt(j + (i + 1)));
//                else System.out.println("false: " + elem);

                // elem = elem.trim();
//                else {
//                    continue;
//                }

                // System.out.println(elem);

                Frequency frequency = new Frequency(elem, 1);
                set.add(frequency);
//                int k = 0;
//                for ( ; k < set.size(); k++) {
//                    if (set.get(k).getSubString().equals(elem)) {
//                        flag = true;
//                        break;
//                    }
//                }
//
//                if (flag) {
//                    Frequency frequency = set.get(k);
//                    set.remove(k);
//                    frequency.setFrequency(frequency.getFrequency() + 1);
//                    set.add(frequency);
//
//                    // System.out.println(frequency);
//                } else {
//                    Frequency frequency = new Frequency(elem, 1);
//                    set.add(frequency);
//
//                    // System.out.println(frequency);
//                }
                // set.add(elem);
            }
        }

        return set;
    }

    /**
     * List down all substrings in "str"
     *
     * @param str
     * @return
     */
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

    /**
     * Read files from local directory
     *
     * @param path
     * @param encoding
     * @return
     * @throws IOException
     */
    static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    /**
     * Print the tokens and their categories in console
     *
     * @param path
     */
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

    /**
     * Count the tokens in sequence "content"
     * @param content
     * @return
     */
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

    /**
     * Count the score for each token sequence
     *
     * @param frequency
     * @return
     */
    static Result countScore(Frequency frequency) {
        int tokens = lexString(frequency.getSubString());

        Double score = 0.0;
        if (tokens != -1)
            score = (Math.log(frequency.getFrequency()) / Math.log(2)) * (Math.log(tokens) / Math.log(2));

        DecimalFormat df = new DecimalFormat("0.00");
        if (tokens != -1 && !Double.isNaN(score))
            return new Result(Double.parseDouble(df.format(score)), tokens, frequency.getFrequency(), frequency.getSubString());

        return null;
    }

    /**
     * List down the common sequence in all the files and generate output file
     */
    static void listCommonSequences() {
        if (resultSets == null || resultSets.size() <= 0)
            return;

        List<Frequency> frequencies = new ArrayList<>();

        List<List<String>> sets = new ArrayList<>();

        for (int i = 0; i < resultSets.size(); i++) {
            List<String> temp = new ArrayList<>();
            for (Result result: resultSets.get(i).getResults()) {
                temp.add(result.getSourceCode());
            }
            sets.add(temp);
        }

        Set<String> common = intersection(sets);
        if (common != null && common.size() > 0) {
            for (String st: common) {
                Frequency frequency = new Frequency(st, 0);
                for (ResultSet resultSet: resultSets) {
                    for (Result result: resultSet.getResults()) {
                        if (result.getSourceCode().equals(st)) {
                            frequency.setFrequency(frequency.getFrequency() + result.getCount());
                        }
                    }
                }
                frequencies.add(frequency);
            }
        }

        List<Result> commonSequences = new ArrayList<>();
        if (frequencies != null && frequencies.size() > 0) {
            for (Frequency frequency: frequencies) {
                commonSequences.add(countScore(frequency));
            }
        }

        for (Result result: commonSequences) {
            System.out.println(result);
        }

        try {
            PrintWriter pw = new PrintWriter(new File(outputFile));
            StringBuilder sb = new StringBuilder();

            sb.append("Score");
            sb.append(",");
            sb.append("Tokens");
            sb.append(",");
            sb.append("Count");
            sb.append(",");
            sb.append("Source Code");
            sb.append("\n");

            for (Result result: commonSequences) {
                sb.append(result.getScore());
                sb.append(",");
                sb.append(result.getTokens());
                sb.append(",");
                sb.append(result.getCount());
                sb.append(",");
                sb.append(result.getSourceCode().replaceAll(",", " "));
                sb.append("\n");
            }

            pw.write(sb.toString());
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set Intersection Operation
     *
     * @param lists
     * @return
     */
    static Set<String> intersection(List<List<String>> lists) {
        Set<String> intersection = new HashSet<>(lists.get(0));
        for(List<String> list : lists) {
            Set<String> newIntersection = new HashSet<>();
            for(String i : list) {
                if(intersection.contains(i)) {
                    newIntersection.add(i);
                }
            }
            intersection = newIntersection;
        }

        return intersection;
    }

}
