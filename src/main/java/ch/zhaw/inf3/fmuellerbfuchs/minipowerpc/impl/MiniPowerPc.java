package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl;

import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Memory;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Pc;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Processor;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.ProgramRunner;

/**
 */
public class MiniPowerPc implements Pc {

    class ProgramRunnerImpl implements ProgramRunner {
        private final Processor processor;
        private final Memory memory;

        ProgramRunnerImpl(Memory memory, int start) {
            this.memory = memory;
            this.processor = new MiniPowerPcProcessor(memory, start);
        }

        @Override
        public void cycle() {
            processor.cycle();
        }

        Processor getProcessor() {
            return processor;
        }

        Memory getMemory() {
            return memory;
        }
    }

    /**
     * Run a program on this pc
     * @param memory the memory
     */
    public ProgramRunner run(Memory memory, int startAddress) {
        return new ProgramRunnerImpl(memory, startAddress);
    }
}
