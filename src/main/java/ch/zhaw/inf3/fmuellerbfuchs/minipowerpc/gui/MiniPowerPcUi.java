package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.gui;

import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.*;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.DefaultOperationFactory;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.HashMapMemory;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.MiniPowerPcRunnerFactory;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.memory.Value;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.mnemonics.MnemonicsParser;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.util.Util;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Map;

/**
 */
public class MiniPowerPcUi {
    private final Memory memory;
    private final RunnerFactory runnerFactory;
    private final ProgramParser parser;
    private final ProgramRunner runner;
    private int delay = 0;
    private boolean stopped = false;
    private boolean updateDisplay = true;
    private boolean running = false;
    private int stepCount = 0;

    private final JTextArea programText;

    private final JEditorPane programMemory;
    private final JEditorPane dataMemory;

    private final JEditorPane commandReg;
    private final JEditorPane accu;
    private final JEditorPane reg2;
    private final JEditorPane reg3;
    private final JEditorPane reg4;
    private final JEditorPane carryFlag;
    private final JEditorPane result;
    private final JEditorPane stepCountPanel;

    private final JTextField setAddress = new JTextField();
    private final JTextField setValue = new JTextField();
    private final JTextField startAddress = new JTextField();

    private final JButton buttonSetData = new JButton("Set");
    private final JButton buttonRunSlow = new JButton("Slow");
    private final JButton buttonStop = new JButton("Stop");
    private final JButton buttonSetStart = new JButton("Set start");
    private final JButton buttonStep = new JButton("Step");
    private final JButton buttonRunMedium = new JButton("Medium");
    private final JButton buttonRunFast = new JButton("Fast");
    private final JButton buttonClear = new JButton("Clear memory");


    public MiniPowerPcUi() {
        this.memory = new HashMapMemory();
        this.runnerFactory = new MiniPowerPcRunnerFactory();
        this.parser = new MnemonicsParser(new DefaultOperationFactory());
        this.runner = runnerFactory.create(memory, 100);

        programText = createProgramTextArea();
        programMemory = createMemoryLabel();
        dataMemory = createMemoryLabel();

        commandReg = createCenterLabel();
        accu = createCenterLabel();
        reg2 = createCenterLabel();
        reg3 = createCenterLabel();
        reg4 = createCenterLabel();
        carryFlag = createCenterLabel();
        result = createCenterLabel();
        stepCountPanel = createCenterLabel();

        startAddress.setText("100");
        setAddress.setText("500");

        buttonStop.setEnabled(false);
    }

    private BorderLayout createBorderLayout() {
        return new BorderLayout(10, 10);
    }

    private JScrollPane withScroll(JComponent component) {
        JScrollPane jScrollPane = new JScrollPane(component);
        jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        return jScrollPane;
    }

    private JEditorPane createMemoryLabel() {
        JEditorPane l = new JEditorPane();
        l.setContentType("text/html");
        l.setEnabled(false);
        l.setDisabledTextColor(Color.black);
        l.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createBevelBorder(BevelBorder.RAISED),
                new EmptyBorder(20,20,20,20)));
        return l;
    }

    private JTextArea createProgramTextArea() {
        JTextArea l = new JTextArea();
        l.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createBevelBorder(BevelBorder.RAISED),
                new EmptyBorder(20,20,20,20)));
        return l;
    }

    private JPanel createBorderPanel() {
        JPanel panel= new JPanel();
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createBevelBorder(BevelBorder.RAISED),
                new EmptyBorder(20, 20, 20, 20)));
        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel= new JPanel();
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createBevelBorder(BevelBorder.LOWERED),
                new EmptyBorder(5,5,5,5)));
        return panel;
    }

    private JEditorPane createCenterLabel() {
        JEditorPane l = new JEditorPane();
        l.setContentType("text/html");
        l.setAlignmentX(0.5f);
        l.setEnabled(false);
        l.setDisabledTextColor(Color.black);
        l.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createBevelBorder(BevelBorder.RAISED),
                new EmptyBorder(5, 5, 5, 5)));
        return l;
    }

    private void repaintMemoryPanes() {
        repaintProgramMemory();
        repaintMemory(dataMemory, 500, 528);
    }

    private void repaintProgramMemory() {
        int cur = runner.getAddress();
        int start = cur - 5 * 2;
        int end = cur + 10 * 2;
        repaintMemory(programMemory, start, end, cur);
    }

    private void repaintMemory(JEditorPane memoryArea,
                               int from,
                               int to) {
        repaintMemory(memoryArea, from, to, -1);
    }

    private void repaintMemory(JEditorPane memoryArea,
                               int from,
                               int to,
                               int hightlight) {
        memoryArea.setText("");
        String address;
        String value;
        String valueString;
        StringBuilder b = new StringBuilder("<html>");
        b.append("<body style=\"font-size: 9px\">");
        b.append("<table>");
        for (int i = from; i <= to; i += 2) {
            address = Integer.toString(i) + " + " + Integer.toString(i + 1);
            value = "0";
            valueString = "0";
            MemoryItem m;
            synchronized (memory) {
                m = memory.get(i, MemoryItem.class);
            }
            if (m != null) {
                valueString = m.asString();
                value = m.asBinaryString();
            }
            if ( hightlight == i) {
                b.append("<tr style=\"color: #00ff00; font-weight: bold;\">");
            } else {
                b.append("<tr>");
            }

            b.append("<td>");
            b.append(address);
            b.append("</td>");
            b.append("<td>");
            b.append(Util.formatBinary(value, 16));
            b.append("</td>");
            b.append("<td>");
            b.append(valueString);
            b.append("</td>");
            b.append("</tr>");
        }
        b.append("</table>");
        b.append("</body>");
        b.append("</html>");
        memoryArea.setText(b.toString());
    }

    private void repaintRegisters() {
        commandReg.setText("<div style=\"font-size:9px\">" + "<b>Command register</b><br/>"
                + runner.getAddress() + "<br/>"
                + Util.formatBinary(runner.getAddress())+ "</div>");

        accu.setText("<div style=\"font-size:9px\">" + "<b>Accumulator</b><br/>"
                + runner.getAccu().get() + "<br/>"
                + Util.formatBinary(runner.getAccu().get())+ "</div>");

        reg2.setText("<div style=\"font-size:9px\">" + "<b>Register 1</b><br/>"
                + runner.getRegister(1).get() + "<br/>"
                + Util.formatBinary(runner.getRegister(1).get())+ "</div>");

        reg3.setText("<div style=\"font-size:9px\">" + "<b>Register 2</b><br/>"
                + runner.getRegister(2).get() + "<br/>"
                + Util.formatBinary(runner.getRegister(2).get())+ "</div>");

        reg4.setText("<div style=\"font-size:9px\">" + "<b>Register 3</b><br/>"
                + runner.getRegister(3).get() + "<br/>"
                + Util.formatBinary(runner.getRegister(3).get())+ "</div>");

        carryFlag.setText("<div style=\"font-size:9px\">" + "<b>Carry flag</b><br/>"
                + runner.getCarry()+ "</div>");

        stepCountPanel.setText("<div style=\"font-size:9px\">" + "<b>Step count</b><br/>"
                + stepCount + "</div>");

        synchronized (memory) {
            Value val504 = memory.get(504, Value.class);
            Value val506 = memory.get(506, Value.class);
            int val = Util.asInt32(val506, val504);
            result.setText("<div style=\"font-size:9px\">" + "<b>Result (504-507)</b><br/>"
                    + val + "<br/>"
                    + "<div style=\"font-size:8px\">" +
                    Util.leftPadNulls(Integer.toBinaryString(val), 32)
                    + "</div>" + "</div>");
        }
    }

    private void stopProgram() {
        running = false;
        stopped = true;

        buttonStep.setEnabled(true);
        startAddress.setEnabled(true);
        setAddress.setEnabled(true);
        setValue.setEnabled(true);
        buttonSetData.setEnabled(true);
        buttonSetStart.setEnabled(true);
        buttonClear.setEnabled(true);

        buttonRunSlow.setEnabled(true);
        buttonRunMedium.setEnabled(true);
        buttonRunFast.setEnabled(true);
        buttonStop.setEnabled(true);
    }

    private void runProgram() {
        if (running) {
            return;
        }

        stopped = false;
        running = true;

        buttonStep.setEnabled(false);
        startAddress.setEnabled(false);
        setAddress.setEnabled(false);
        setValue.setEnabled(false);
        buttonSetData.setEnabled(false);
        buttonSetStart.setEnabled(false);
        buttonClear.setEnabled(false);

        buttonRunMedium.setEnabled(true);
        buttonRunSlow.setEnabled(true);
        buttonRunFast.setEnabled(true);
        buttonStop.setEnabled(true);

        new RunnerTask().execute();
    }

    class RunnerTask extends SwingWorker<Void, Void> {
        @Override
        public Void doInBackground() {
            stepCount = 0;
            try {
                while (runner.cycle() && !stopped) {
                    Thread.sleep(delay);
                    if (updateDisplay) {
                        repaintMemoryPanes();
                        repaintRegisters();
                    }
                    stepCount++;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                stopProgram();
            }
            if (!updateDisplay) {
                repaintMemoryPanes();
                repaintRegisters();
            }
            return null;
        }
    }

    public JFrame createUi() throws IOException {
        final JFrame frame = new JFrame("ZHAW Mini Power PC");
        final JTabbedPane tabPanel = new JTabbedPane();

        final JPanel commandPanel = createBorderPanel();
        commandPanel.setMinimumSize(new Dimension(300, 0));
        commandPanel.setPreferredSize(new Dimension(450, 0));
        commandPanel.setSize(300, commandPanel.getHeight());
        commandPanel.setLayout(createBorderLayout());
        commandPanel.add(withScroll(programMemory), BorderLayout.CENTER);

        final JPanel dataPanel = createBorderPanel();
        dataPanel.setPreferredSize(new Dimension(450, 0));
        dataPanel.setLayout(createBorderLayout());
        dataPanel.add(withScroll(dataMemory), BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                commandPanel, dataPanel);

        final JPanel regPanel = createBorderPanel();
        regPanel.setPreferredSize(new Dimension(300, 0));
        regPanel.setLayout(new GridLayout(8, 1));
        regPanel.add(commandReg);
        regPanel.add(accu);
        regPanel.add(reg2);
        regPanel.add(reg3);
        regPanel.add(reg4);
        regPanel.add(carryFlag);
        regPanel.add(result);
        regPanel.add(stepCountPanel);

        final JPanel processorPanel = new JPanel();
        processorPanel.setLayout(createBorderLayout());
        processorPanel.add(splitPane, BorderLayout.CENTER);
        processorPanel.add(regPanel, BorderLayout.EAST);

        final JPanel programPanel = createBorderPanel();
        programPanel.setLayout(createBorderLayout());
        programPanel.add(withScroll(programText), BorderLayout.CENTER);

        // Button panel
        final JPanel processorButtons = createButtonPanel();

        startAddress.setPreferredSize(new Dimension(100, 30));

        final JLabel startAdrFiller = new JLabel();
        startAdrFiller.setPreferredSize(new Dimension(30, 0));

        buttonSetStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sa = startAddress.getText().trim();
                if (!sa.equals("")) {
                    runner.setAddress(Integer.parseInt(sa));
                    repaintRegisters();
                    repaintMemoryPanes();
                }
            }
        });

        buttonStep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        runner.cycle();
                        repaintRegisters();
                        repaintMemoryPanes();
                    }
                });
            }
        });
        buttonRunSlow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        delay = 1000;
                        updateDisplay = true;
                        runProgram();
                    }
                });
            }
        });
        buttonRunMedium.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        delay = 0;
                        updateDisplay = true;
                        runProgram();
                    }
                });
            }
        });
        buttonRunFast.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        delay = 0;
                        updateDisplay = false;
                        runProgram();
                    }
                });
            }
        });
        buttonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        stopProgram();
                    }
                });
            }
        });

        // memory set
        final JLabel filler = new JLabel();
        buttonSetData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int adr = Integer.parseInt(setAddress.getText());
                int val = Integer.parseInt(setValue.getText());
                synchronized (memory){
                    memory.set(adr, new Value(val));
                }
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        repaintMemoryPanes();
                    }
                });
            }
        });

        buttonClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                memory.clear(500);
                for (Register r : runner.getRegisters()) {
                    r.set(0);
                }
                runner.setAddress(0);
                runner.clearCarry();
                runner.setAddress(100);

                repaintMemoryPanes();
                repaintRegisters();
            }
        });

        filler.setPreferredSize(new Dimension(10, 0));
        setAddress.setPreferredSize(new Dimension(100, 30));
        setValue.setPreferredSize(new Dimension(100, 30));

        processorButtons.add(setAddress);
        processorButtons.add(setValue);

        processorButtons.add(buttonSetData);
        processorButtons.add(filler);

        // start setAddress
        processorButtons.add(startAddress);
        processorButtons.add(buttonSetStart);
        processorButtons.add(startAdrFiller);

        // step buttons
        processorButtons.add(buttonStep);
        processorButtons.add(buttonRunSlow);
        processorButtons.add(buttonRunMedium);
        processorButtons.add(buttonRunFast);
        processorButtons.add(buttonStop);
        processorButtons.add(buttonClear);
        processorPanel.add(processorButtons, BorderLayout.SOUTH);

        final JPanel programButtons = createButtonPanel();
        final JButton buttonLoad = new JButton("Load");

        buttonLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        String text = programText.getText();
                        Map<Integer, Operation> ops = parser.parse(text);
                        for (Map.Entry<Integer, Operation> op : ops.entrySet()) {
                            memory.set(op.getKey(), op.getValue());
                        }
                        repaintMemoryPanes();
                    }
                });
            }
        });

        programButtons.add(buttonLoad);

        programPanel.add(programButtons, BorderLayout.SOUTH);

        tabPanel.add("Processor", processorPanel);
        tabPanel.add("Program", programPanel);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.setSize(540, 380);
        frame.getContentPane().add(tabPanel);

        repaintMemoryPanes();
        repaintRegisters();

        return frame;
    }

    public static void main(String[] args) {
        final MiniPowerPcUi ui = new MiniPowerPcUi();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = null;
                try {
                    frame = ui.createUi();
                    frame.setVisible(true);
                } catch (IOException e) {
                    e.printStackTrace(); // FIXME
                }
            }
        });
    }
}
