package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.util;

import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.memory.Value;

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

    public static String formatBinary(String binary) {
        return formatBinary(binary, 16);
    }

    public static String formatBinary(String binary, int digits) {
        if (binary != null) {
            if (binary.length() > digits) {
                binary = binary.substring(binary.length()-digits);
            }
            return leftPadNulls(binary, digits);
        }
        return "000000000000";
    }

    public static String formatBinary(int i) {
        return formatBinary(Integer.toBinaryString(i));
    }

    public static int getDirectNumber(String num) {
        if (num.startsWith("#")) {
            return Integer.parseInt(num.substring(1));
        } else {
            return Integer.parseInt(num);
        }
    }

    public static int asInt32(Value v1, Value v2) {
        try {
            int i1 = v1 != null ? v1.asBinary() : 0;
            int i2 = v2 != null ? v2.asBinary() : 0;

            String min = "";

            if ( (i1 & (1 << 15)) >> 15 == 1 ) {
                i1 = ~i1;
                i2 = ~i2 + 1;
                min = "-";
            }

            String is1 = Util.formatBinary(i1);
            String is2 = Util.formatBinary(i2);

            return Integer.parseInt(min + is1 + is2, 2);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static String leftPadNulls(String str, int size) {
        return leftPad(str, size, "0");
    }

    public static String leftPad(String str, int size, String pad) {
        int l = str.length();
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < size - l; i++) {
            b.append(pad);
        }
        b.append(str);
        return b.toString();
    }
}
