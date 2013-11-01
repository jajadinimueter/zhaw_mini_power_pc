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

    private final JTextArea programText;

    private final JEditorPane commandMemoryLabel;
    private final JEditorPane dataMemoryLabel;

    private final JEditorPane commandReg;
    private final JEditorPane accu;
    private final JEditorPane reg2;
    private final JEditorPane reg3;
    private final JEditorPane reg4;
    private final JEditorPane carryFlag;

    public MiniPowerPcUi() {
        this.memory = new HashMapMemory();
        this.runnerFactory = new MiniPowerPcRunnerFactory();
        this.parser = new MnemonicsParser(new DefaultOperationFactory());
        this.runner = runnerFactory.create(memory, 100);

        programText = createProgramTextArea();
        commandMemoryLabel = createMemoryLabel();
        dataMemoryLabel = createMemoryLabel();

        commandReg = createCenterLabel("command register");
        accu = createCenterLabel("accumulator");
        reg2 = createCenterLabel("register 2");
        reg3 = createCenterLabel("register 3");
        reg4 = createCenterLabel("register 4");
        carryFlag = createCenterLabel("carry flag");
    }

    private BorderLayout createBorderLayout() {
        return new BorderLayout(10, 10);
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

    private JEditorPane createCenterLabel(String text) {
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

    private void repaintMemory(JEditorPane memoryArea, int from, int to) {
        memoryArea.setText("");
        String address;
        String value;
        String valueString;
        StringBuilder b = new StringBuilder("<html>");
        b.append("<table>");
        for (int i = from; i <= to; i += 2) {
            address = Integer.toString(i) + " + " + Integer.toString(i + 1);
            value = "0";
            valueString = "-";
            MemoryItem m = memory.get(i, MemoryItem.class);
            if (m != null) {
                value = m.asBinaryString();
                valueString = m.asString();
            }
            b.append("<tr>");
            b.append("<td>");
            b.append("<b>");
            b.append(address);
            b.append("</b>");
            b.append("</td>");
            b.append("<td>");
            b.append(Util.leftPadNulls(value, 16));
            b.append("</td>");
            b.append("<td>");
            b.append("<b>");
            b.append(valueString);
            b.append("</b>");
            b.append("</td>");
            b.append("</tr>");
        }
        b.append("</table>");
        b.append("</html>");
        memoryArea.setText(b.toString());
    }

    private void repaintRegisters() {
        commandReg.setText("<b>command pointer</b><br/><br/>"
                + runner.getAddress());

        accu.setText("<b>accumulator</b><br/><br/>"
                + runner.getAccu().get());

        reg2.setText("<b>register 2</b><br/><br/>"
                + runner.getRegister(1).get());

        reg3.setText("<b>register 3</b><br/><br/>"
                + runner.getRegister(2).get());

        reg4.setText("<b>register 4</b><br/><br/>"
                + runner.getRegister(3).get());

        carryFlag.setText("<b>carry</b><br/><br/>"
                + runner.getCarry());

    }

    public JFrame createUi() throws IOException {
        final JFrame frame = new JFrame("ZHAW Mini Power PC");
        final JTabbedPane tabPanel = new JTabbedPane();


        final JPanel commandPanel = createBorderPanel();
        commandPanel.setPreferredSize(new Dimension(600, 0));
        commandPanel.setSize(300, commandPanel.getHeight());
        commandPanel.setLayout(createBorderLayout());
        commandPanel.add(commandMemoryLabel, BorderLayout.CENTER);

        final JPanel dataPanel = createBorderPanel();
        dataPanel.setPreferredSize(new Dimension(600, 0));
        dataPanel.setLayout(createBorderLayout());
        dataPanel.add(dataMemoryLabel, BorderLayout.CENTER);

        final JPanel regPanel = createBorderPanel();
        regPanel.setPreferredSize(new Dimension(300, 0));
        regPanel.setLayout(new GridLayout(6, 1));
        regPanel.add(commandReg);
        regPanel.add(accu);
        regPanel.add(reg2);
        regPanel.add(reg3);
        regPanel.add(reg4);
        regPanel.add(carryFlag);

        final JPanel processorPanel = new JPanel();
        processorPanel.setLayout(createBorderLayout());
        processorPanel.add(commandPanel, BorderLayout.WEST);
        processorPanel.add(dataPanel, BorderLayout.CENTER);
        processorPanel.add(regPanel, BorderLayout.EAST);

        final JPanel programPanel = createBorderPanel();
        programPanel.setLayout(createBorderLayout());
        programPanel.add(programText, BorderLayout.CENTER);

        // Button panel
        final JPanel processorButtons = createButtonPanel();

        final JTextField startAddress = new JTextField();
        startAddress.setPreferredSize(new Dimension(100, 30));

        final JLabel startAdrFiller = new JLabel();
        startAdrFiller.setPreferredSize(new Dimension(30, 0));

        final JButton buttonPause = new JButton("Stop");
        final JButton buttonStart = new JButton("Set start");
        final JButton buttonStep = new JButton("Step");
        buttonStep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        runner.cycle();
                        repaintRegisters();
                    }
                });
            }
        });
        final JButton buttonRunSlow = new JButton("Slow");
        final JButton buttonRunFast = new JButton("Fast");
        final JButton buttonClear = new JButton("Clear");

        // memory set
        final JLabel filler = new JLabel();
        final JTextField address = new JTextField();
        final JTextField value = new JTextField();
        final JButton setDataButton = new JButton("Set");
        setDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int adr = Integer.parseInt(address.getText());
                int val = Integer.parseInt(value.getText());
                memory.set(adr, new Value(val));
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        repaintMemory(dataMemoryLabel, 500, 528);
                        repaintMemory(commandMemoryLabel, 100, 150);
                    }
                });
            }
        });


        filler.setPreferredSize(new Dimension(10, 0));
        address.setPreferredSize(new Dimension(100, 30));
        value.setPreferredSize(new Dimension(100, 30));

        processorButtons.add(address);
        processorButtons.add(value);

        processorButtons.add(setDataButton);
        processorButtons.add(filler);

        // start address
        processorButtons.add(startAddress);
        processorButtons.add(buttonStart);
        processorButtons.add(startAdrFiller);

        // step buttons
        processorButtons.add(buttonStep);
        processorButtons.add(buttonRunSlow);
        processorButtons.add(buttonRunFast);
        processorButtons.add(buttonPause);
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
                        repaintMemory(commandMemoryLabel, 100, 150);
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

        repaintMemory(dataMemoryLabel, 500, 528);
        repaintMemory(commandMemoryLabel, 100, 150);
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
