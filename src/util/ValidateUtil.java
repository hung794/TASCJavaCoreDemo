package util;

public class ValidateUtil {
    public static boolean checkString(String input) {
        String regex = "^[^\\d!@#$%^&*()_+={}\\[\\]|\\\\:;\"'<>,.?/~`]+(\\s+[^\\d!@#$%^&*()_+={}\\[\\]|\\\\:;\"'<>,.?/~`]+)*\\s*\\d*$";
        return input.matches(regex) || input.isEmpty();
    }

    public static boolean checkNumber(String input) {
        String regex = "^\\d+(,\\d{1,2})?$";
        return input.matches(regex) || input.isEmpty();
    }
}
