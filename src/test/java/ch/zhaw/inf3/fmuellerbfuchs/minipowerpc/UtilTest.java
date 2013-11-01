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
}
