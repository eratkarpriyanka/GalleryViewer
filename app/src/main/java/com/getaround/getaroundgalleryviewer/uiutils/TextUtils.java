package com.getaround.getaroundgalleryviewer.uiutils;

import java.util.regex.Pattern;

public class TextUtils {
    /**
     * Converts a string to title casing.
     * @param str
     *      The string to convert.
     * @return
     *      The converted string.
     */
    public static String toTitleCase(String str) {
        if (str == null) {
            return null;
        }
        boolean space = true;
        StringBuilder builder = new StringBuilder(str);
        final int len = builder.length();

        for (int i=0; i < len; ++i) {
            char c = builder.charAt(i);
            if (space) {
                if (!Character.isWhitespace(c)) {
                    // Convert to title case and switch out of whitespace mode.
                    builder.setCharAt(i, Character.toTitleCase(c));
                    space = false;
                }
            } else if (Character.isWhitespace(c)) {
                space = true;
            } else {
                builder.setCharAt(i, Character.toLowerCase(c));
            }
        }

        return builder.toString();
    }

    public static boolean isEnglishWord(String string) {

        Pattern VALID_NAME_PATTERN_REGEX = Pattern.compile("[a-zA-Z_0-9]+$");
        return VALID_NAME_PATTERN_REGEX.matcher(string).find();
    }
}
