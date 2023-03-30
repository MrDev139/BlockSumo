package me.mrdev.bs.utils;

public class MathUtils {

    public boolean isInteger(String string) {
        try {
            Integer.parseInt(string);
        }catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

}
