package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public static String center(String s, int size) {
        return center(s, size, " ");
    }

    public static String center(String s, int size, String pad) {
        if (s == null || size <= s.length())
            return s;

        StringBuilder sb = new StringBuilder(size);
        for (int i = 0; i < (size - s.length()) / 2; i++) {
            sb.append(pad);
        }
        sb.append(s);
        while (sb.length() < size) {
            sb.append(pad);
        }
        return sb.toString();
    }

    public static String getProductId(String productStr) {
        Matcher matcher = Pattern.compile("\\d+").matcher(productStr);
        matcher.find();
        return matcher.group();
    }
}