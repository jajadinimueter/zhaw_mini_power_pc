package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc;

import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.util.Util;
import org.junit.Test;

/**
 */
public class UtilTest {
    @Test
    public void formatArguments() {
        assert Util.formatArguments(new String[0]).equals("");
        assert Util.formatArguments(new String[]{"foo"}).equals("foo");
        assert Util.formatArguments(new String[]{"foo", "bar"}).equals("foo, bar");
    }

    @Test
    public void mk32() {
        // 0000000000000000_0111_1111_1111_1111

        int i1 = 0b1111111111111111;
        int i2 = 0b1000000000000000;

        i1 = ~i1;
        i2 = ~i2 + 1;

        String is1 = Util.formatBinary(i1);
        String is2 = Util.formatBinary(i2);

        assert Integer.parseInt("-" + is1 + is2, 2) == -32768;
    }

    // Takes a decimal int and returns a two's complement i bit string
    public String convertToBin(int value, int i) {
        String a = Integer.toBinaryString(value & 0xFFFF);

        return String.format("%" + i + "s", a).replace(' ', '0');
    }

    @Test
    public void testBinConv() {
        convertToBin(4 * 16384, 16);
    }
}
