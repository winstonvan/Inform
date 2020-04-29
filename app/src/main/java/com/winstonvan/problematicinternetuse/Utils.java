package com.winstonvan.problematicinternetuse;

import java.util.regex.Pattern;

public class Utils {

    public static boolean fieldValidation(String string) {
        return true;
    }

    public static boolean isPasswordValid(String password){
        if (password == null)
            return false;
        else if (password.length() < 6)
            return false;
        else
            return true;
    }

    public static boolean isConfirmPasswordValid(String password, String confirm){
        if (confirm == null || !password.equals(confirm))
            return false;
        else
            return true;
    }

    public static boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);

        if (email == null)
            return false;
        else if (pat.matcher(email).matches())
            return true;
        else
            return false;
    }
}
