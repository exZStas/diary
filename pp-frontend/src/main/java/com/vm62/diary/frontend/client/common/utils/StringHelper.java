package com.vm62.diary.frontend.client.common.utils;

import com.google.common.base.Joiner;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.HTML;

import java.util.Arrays;

/**
 *
 */
public class StringHelper {
    /**
     * Complements value to a target size from the left with a given symbols.
     * @param value value
     * @param targetSize targetSize
     * @param symbol symbol
     * @return String
     */
    public static String complementLeft(String value, int targetSize, char symbol) {
        if (value == null || value.length() >= targetSize) {
            return value;
        }
        int annexSize = targetSize - value.length();
        char[] annex = new char[annexSize];
        Arrays.fill(annex, symbol);
        return String.valueOf(annex).concat(value);
    }

    public static String capitalizeFirstLetter(String input) {
        if (input != null && input.length() > 1) {

            return input.substring(0, 1).toUpperCase() + input.substring(1);
        } else {
            return input;
        }
    }

    public static String htmlEscape(String text) {
        return ValidationUtils.isNullOrEmpty(text) ? text : SafeHtmlUtils.htmlEscape(text);
    }

    public static String htmlUnescape(String text) {
        return ValidationUtils.isNullOrEmpty(text) ? text : new HTML(text).getText();
    }



    public static String joinExcludingNulls(String separator, String first, String second, String... rest) {
        return Joiner.on(separator).skipNulls().join(first, second, (Object[])rest);
    }

    private static String addZero(int val) {
        return StringHelper.complementLeft(String.valueOf(val), 2, '0');
    }


    public static String priceFormatToString(Double price) {
        return NumberFormat.getFormat("0.##").format(price).replace(",",".");
    }

    public static String cutNullableString(String str, int len) {
        if (null == str){
            return  "";
        }
        return (str.length() > len) ? str.substring(0, len - 1) : str;
    }

}
