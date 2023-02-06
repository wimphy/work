package com.my.kb.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SiString {

    //certification_breakdown_flag
    public static String _toCap(String strWith_) {
        if (strWith_ == null || !strWith_.contains("_")) {
            return strWith_;
        }
        String res = strWith_;
        Pattern pattern = Pattern.compile("_[a-zA-Z]");
        Matcher matcher = pattern.matcher(res);
        while (matcher.find()) {
            String _c = matcher.group(0);
            String cap = _c.replace("_", "").toUpperCase();
            res = res.replaceAll(_c, cap);
        }
        return res;
    }

    public static void main(String[] args) {
        String input = "certification_breakdown_flag";
        String res = _toCap(input);
        System.out.println(input);
        System.out.println(res);
    }
}
