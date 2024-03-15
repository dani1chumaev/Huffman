package org.example;

import java.util.Collections;

public class RLE {

    public static final Integer BYTE_PER_CHAR = 8;

    public static void createRLE(String text) {
        System.out.println("RLE");
        String encodeData = encodeData(text);
        System.out.println("The encoded string is: " + encodeData);
        System.out.printf("Compression rate: %f%%\n", (double) encodeData.length() * BYTE_PER_CHAR / (double) text.length() * BYTE_PER_CHAR);
        String decodeData = decodeData(encodeData);
        System.out.print("The decoded string is: " + decodeData);
    }

    public static String encodeData(String input) {
        StringBuilder result = new StringBuilder();
        int count = 1;
        char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (i + 1 < chars.length && c == chars[i + 1]) {
                count++;
            } else {
                result.append(count).append(c);
                count = 1;
            }
        }
        return result.toString();
    }

    public static String decodeData(String rle) {
        StringBuilder result = new StringBuilder();
        char[] chars = rle.toCharArray();

        int count = 0;
        for (char c : chars) {
            if (Character.isDigit(c)) {
                count = 10 * count + Character.getNumericValue(c);
            } else {
                result.append(String.join("", Collections.nCopies(count, String.valueOf(c))));
                count = 0;
            }
        }
        return result.toString();
    }

}
