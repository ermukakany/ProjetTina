package cloudm120152016.puy2docs.utils;

import android.util.Patterns;

import java.util.regex.Pattern;

public class PatternValidor {

    private Pattern password_pattern;
    private Pattern birthday_pattern;

    private static final String PASSWORD_PATTERN = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,40})";
    //private static final String BIRTHDAY_PATTERN = "([1-9]|0[1-9]|[1-2][0-9]|30|31)/(0[1-9]|[1-9]|1[0-2])/([1-9]{4})";
    private static final String BIRTHDAY_PATTERN = "([0][1-9]|[12][0-9]|3[01])/([0][1-9]|1[012])/((19|20)\\d\\d)";

    public PatternValidor() {
        password_pattern = Pattern.compile(PASSWORD_PATTERN);
        birthday_pattern = Pattern.compile(BIRTHDAY_PATTERN);
    }

    public static boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public boolean isPasswordValid(String password) {
        return password_pattern.matcher(password).matches();
    }

    public boolean isBirthdayValid(String birthday){
        return birthday_pattern.matcher(birthday).matches();
    }
}
