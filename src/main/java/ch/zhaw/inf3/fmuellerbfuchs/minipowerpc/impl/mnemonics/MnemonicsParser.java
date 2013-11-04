package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.mnemonics;

import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Operation;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.OperationFactory;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.ProgramParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 */
public class MnemonicsParser implements ProgramParser {

    private OperationFactory factory;

    public MnemonicsParser(OperationFactory factory) {
        this.factory = factory;
    }

    public Map<Integer, Operation> parse(String program) {
        if (program == null) {
            throw new RuntimeException("`program` must not be null");
        }

        Map<Integer, Operation> ops = new HashMap<Integer, Operation>();

        program = program.trim();

        for (String line : program.split(System.getProperty("line.separator"))) {
            line = line.trim();
            // omit lines starting with a comment
            if (line.startsWith(";")) {
                continue;
            }
            // delete comments at the end
            String[] commentParts = line.split(";");
            line = commentParts[0];

            commentParts = line.split("'");
            line = commentParts[0];

            String[] parts = line.split("\\s+");

            if (parts.length < 2) {
                throw new RuntimeException("Invalid command %s");
            }

            int adr = Integer.parseInt(parts[0]);

            String opCode = parts[1];

            String[] arguments;

            if (parts.length > 2) {
                // additional arguments
                arguments = new String[parts.length - 2];
                for (int i = 0; i < arguments.length; i++) {
                    String arg = parts[i+2];
                    arg = arg.replace(",", "");
                    arguments[i] = arg;
                }
            } else {
                arguments = new String[]{};
            }

            ops.put(adr, factory.create(opCode, arguments));
        }

        return ops;
    }

}
