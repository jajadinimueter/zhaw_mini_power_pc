package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.mnemonics;

import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Operation;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.OperationFactory;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.ProgramParser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 */
public class MnemonicsParser implements ProgramParser {

    private OperationFactory factory;
    private String[] comments = new String[]{";", "'", "//"};

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

            if (line.equals("")) {
                continue;
            }

            boolean skip = false;
            for (String c : comments) {
                if (line.startsWith(c)) {
                    skip = true;
                    break;
                }
            }

            if (skip) {
                continue;
            }

            for (String c : comments) {
                String[] parts = line.split(";");
                if (parts.length == 0) {
                    skip = true;
                    break;
                }
                line = parts[0];
            }

            if (skip) {
                continue;
            }

            if (line.equals("")) {
                continue;
            }

            String[] parts = line.split("\\s+");

            if (parts.length < 2) {
                throw new RuntimeException("Invalid command "
                        + Arrays.toString(parts));
            }

            int adr = Integer.parseInt(parts[0]);

            String opCode = parts[1];

            // maybe its a number
            try {
                Integer.parseInt(opCode);
                ops.put(adr, factory.create(opCode));
            } catch (NumberFormatException nf) {
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

                try {
                    ops.put(adr, factory.create(opCode, arguments));
                } catch (Exception e) {
                    System.out.println("Exception while creating "
                            + adr + " " + opCode + " " + Arrays.toString(arguments));
                    e.printStackTrace();
                }
            }

        }

        return ops;
    }

}
