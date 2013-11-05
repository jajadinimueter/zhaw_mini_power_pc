package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl;

import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.*;

import java.util.List;

/**
 */
public class MiniPowerPcRunnerFactory implements RunnerFactory {

    class ProgramRunnerImpl implements ProgramRunner {
        private final Processor processor;
        private final Memory memory;

        ProgramRunnerImpl(Memory memory, int start) {
            this.memory = memory;
            this.processor = new MiniPowerPcProcessor(memory, start);
        }

        @Override
        public boolean cycle() {
            return processor.cycle();
        }

        @Override
        public int getAddress() {
            return processor.getAddress();
        }

        @Override
        public void setAddress(int adr) {
            processor.setAddress(adr);
        }

        @Override
        public int getCarry() {
            return processor.getCarry();
        }

        @Override
        public void clearCarry() {
            processor.setCarry(false);
        }

        @Override
        public List<Register> getRegisters() {
            return processor.getRegisters();
        }

        @Override
        public Register getRegister(int index) {
            return processor.getRegister(index);
        }

        @Override
        public Register getAccu() {
            return processor.getAccu();
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
    public ProgramRunner create(Memory memory, int startAddress) {
        return new ProgramRunnerImpl(memory, startAddress);
    }
}
