package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.util;

/**
 */
public class Util {
    public static String formatArguments(String[] arguments) {
        StringBuilder b = new StringBuilder();
        for (int i=0; i<arguments.length; i++) {
            String arg = arguments[i];
            b.append(arg);
            if (i < arguments.length - 1) {
                b.append(", ");
            }
        }
        return b.toString();
    }

    public static int getRegister(String name) {
        if (name.startsWith("r")) {
            return Integer.parseInt(name.substring(1));
        } else {
            return Integer.parseInt(name);
        }
    }

    public static int getDirectNumber(String num) {
        if (num.startsWith("#")) {
            return Integer.parseInt(num.substring(1));
        } else {
            return Integer.parseInt(num);
        }
    }

    public static String leftPadNulls(String str, int size) {
        int l = str.length();
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < size - l; i++) {
            b.append("0");
        }
        b.append(str);
        return b.toString();
    }
}
