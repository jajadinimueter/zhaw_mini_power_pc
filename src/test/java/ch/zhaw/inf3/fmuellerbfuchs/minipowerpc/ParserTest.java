package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc;

import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.mnemonics.MnemonicsParser;
import org.junit.Test;

import java.util.Arrays;
import java.util.Map;

/**
 */
public class ParserTest {

    class DummyOperation implements Operation {
        public String name;
        public String[] arguments;

        DummyOperation(String name, String[] arguments) {
            this.name = name;
            this.arguments = arguments;
        }

        @Override
        public void execute(Processor processor, Memory memory) {
            // FIXME
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public int asBinary() {
            return 0;  // FIXME
        }

        @Override
        public String asBinaryString() {
            return null;  // FIXME
        }

        @Override
        public String asString() {
            return null;  // FIXME
        }
    }

    @Test
    public void parseCommand() {
        String code = "100              foo r1, 300 ; alsdkjfsdaf ; adlkfjdsl ; lkdsjlksd";
        OperationFactory fac = new OperationFactory() {
            @Override
            public Operation create(String opName, String[] arguments) {
                assert opName.equals("foo"): "opName is " + opName;
                assert Arrays.equals(arguments, new String[]{"r1", "300"});
                return new DummyOperation(opName, arguments);
            }
        };
        ProgramParser p = new MnemonicsParser(fac);
        Map<Integer, Operation> ops = p.parse(code);
        assert ops.size() == 1;
        assert ops.get(100).toString().equals("foo");
    }

}
