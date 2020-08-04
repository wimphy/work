package com.simon.tools.utils;

public class Strings {
    public static String toSQLString(String description) {
        String sqlString = description;
        //!!, [], {}, (), <>
        //skip special chars
        if (!sqlString.contains("!")) {
            sqlString = String.format("q'!%s!'", sqlString);
        } else if (!sqlString.contains("[") && !sqlString.contains("]")) {
            sqlString = String.format("q'[%s]'", sqlString);
        } else if (!sqlString.contains("{") && !sqlString.contains("}")) {
            sqlString = String.format("q'{%s}'", sqlString);
        } else if (!sqlString.contains("(") && !sqlString.contains(")")) {
            sqlString = String.format("q'(%s)'", sqlString);
        } else if (!sqlString.contains("<") && !sqlString.contains(">")) {
            sqlString = String.format("q'<%s>'", sqlString);
        } else {
            return null;
        }
        return sqlString;
    }

    public static String formatBatchName(String outFileName, String index) {
        return outFileName.substring(0, outFileName.lastIndexOf(".")) + "." + index + ".sql";
    }
}
