package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.memory;

import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.MemoryItem;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.util.Util;

/**
 */
public class Value implements MemoryItem {
    private int value = 0;

    public Value(int value) {
        this.value = value;
    }

    @Override
    public int asBinary() {
        return value;
    }

    @Override
    public String asBinaryString() {
        return Util.formatBinary(value);
    }

    @Override
    public String asString() {
        String x = asBinaryString();
//        if (x.substring(0, 1).equals("1")) {
//            x = Util.leftPad(x, 32, "1");
//        }

        return Integer.toString(
                Integer.valueOf(x, 2).shortValue());
    }
}
