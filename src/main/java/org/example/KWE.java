package org.example;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KWE {

    private final static Integer BITS_PER_TOKEN = 16;
    private final static Integer BITS_PER_CHAR = 8;
    public static void createKWE(String text) {
        //base case: if user does not provides string
        if (text == null || text.length() == 0)
        {
            return;
        }
        //count the frequency of appearance of each character and store it in a map
        //creating an instance of the Map
        Map<String, Integer> freq = wordsFreq(text, List.of(' '));

        Map<String, String> keywords = freq.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> generateBinaryString(16)));

        System.out.println("The initial string is: " + text);
        System.out.println("Tokens: " + keywords);
        String encodeData = encodeData(text, keywords);
        System.out.println("The encoded string is: " + encodeData);
        System.out.printf("Compression rate: %f%%\n", (double) encodeData.length() / (double)(text.length() * BITS_PER_CHAR) * 100);
        String decodeData = decodeData(encodeData, keywords);
        System.out.print("The decoded string is: " + decodeData);
    }

    public static String encodeData(String input, Map<String, String> keywords) {
        String result = input;
        for (String key : keywords.keySet()) {
            result = result.replaceAll(key, keywords.get(key));
        }
        return result;
    }

    public static String decodeData(String kwe, Map<String, String> keywords) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < kwe.length(); i += BITS_PER_TOKEN) {
            String bits = kwe.substring(i, Math.min(kwe.length(), i + BITS_PER_TOKEN));
            result.append(keywords.entrySet().stream()
                    .filter(entry -> entry.getValue().equals(bits))
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .get());
//            result.append( kwe, i, Math.min(kwe.length(), i + BITS_PER_TOKEN));
        }
        return result.toString();
    }

    public static List<String> usingSplitMethod(String text, int n) {
        String[] results = text.split("(?<=\\G.{" + n + "})");
        return Arrays.asList(results);
    }

    public static Map<String, Integer> wordsFreq(String text, List<Character> separators) {
        Map<String, Integer> freq = new HashMap<>();
        StringBuilder word = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            if (separators.contains(text.charAt(i))) {
                if (!word.isEmpty()) {
                    freq.put(word.toString(), freq.getOrDefault(word.toString(), 0) + 1);
                    word.setLength(0);
                }
                freq.put(String.valueOf(text.charAt(i)), freq.getOrDefault(String.valueOf(text.charAt(i)), 0) + 1);
            } else {
                word.append(text.charAt(i));
            }
        }
        if (!word.isEmpty()) {
            freq.put(word.toString(), freq.getOrDefault(word.toString(), 0) + 1);
        }
        return freq;
    }

    public static int findRandom() {
        int num = (1 + (int)(Math.random() * 100)) % 2;
        return num;
    }
    public static String generateBinaryString(int N) {
        String S = "";
        for(int i = 0; i < N; i++)
        {
            int x = findRandom();
            S = S + String.valueOf(x);
        }
        return S;
    }

}
