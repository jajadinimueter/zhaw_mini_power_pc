package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.gui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 */
public class MiniPowerPcUi {
    private BorderLayout createBorderLayout() {
        return new BorderLayout(10, 10);
    }

    private JTextArea createMemoryLabel(String text) {
        JTextArea l = new JTextArea(text);
        l.setEnabled(false);
        l.setDisabledTextColor(Color.black);
        l.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createBevelBorder(BevelBorder.RAISED),
                new EmptyBorder(20,20,20,20)));
        return l;
    }

    private JTextArea createProgramTextArea(String text) {
        JTextArea p = createMemoryLabel(text);
        p.setEnabled(true);
        return p;
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

    private JTextArea createCenterLabel(String text) {
        JTextArea l = new JTextArea(text);
        l.setAlignmentX(0.5f);
        l.setEnabled(false);
        l.setDisabledTextColor(Color.black);
        l.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createBevelBorder(BevelBorder.RAISED),
                new EmptyBorder(5, 5, 5, 5)));
        return l;
    }

    public JFrame createUi() {
        final JFrame frame = new JFrame("ZHAW Mini Power PC");
        final JTabbedPane tabPanel = new JTabbedPane();

        // program text area
        final JTextArea programText = createProgramTextArea("program");

        // memory labels
        final JTextArea commandMemoryLabel = createMemoryLabel("command memory");
        final JTextArea dataMemoryLabel = createMemoryLabel("data memory");

        // register labels
        final JTextArea commandRegLabel = createCenterLabel("command register");
        final JTextArea reg1Label = createCenterLabel("accumulator");
        final JTextArea reg2Label = createCenterLabel("register 2");
        final JTextArea reg3Label = createCenterLabel("register 3");
        final JTextArea reg4Label = createCenterLabel("register 4");
        final JTextArea carryLabel = createCenterLabel("carry flag");

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
        regPanel.add(commandRegLabel);
        regPanel.add(reg1Label);
        regPanel.add(reg2Label);
        regPanel.add(reg3Label);
        regPanel.add(reg4Label);
        regPanel.add(carryLabel);

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

        final JButton buttonStop = new JButton("Stop");
        final JButton buttonStart = new JButton("Start");
        final JButton buttonStep = new JButton("Step");
        final JButton buttonRunSlow = new JButton("Slow");
        final JButton buttonRunFast = new JButton("Fast");

        // memory set
        final JLabel filler = new JLabel();
        final JButton setDataButton = new JButton("Set");
        final JTextField address = new JTextField();
        final JTextField value = new JTextField();

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
        processorButtons.add(buttonStop);
        processorButtons.add(startAdrFiller);

        // step buttons
        processorButtons.add(buttonStep);
        processorButtons.add(buttonRunSlow);
        processorButtons.add(buttonRunFast);
        processorPanel.add(processorButtons, BorderLayout.SOUTH);

        final JPanel programButtons = createButtonPanel();
        final JButton buttonLoad = new JButton("Load");
        programButtons.add(buttonLoad);
        programPanel.add(programButtons, BorderLayout.SOUTH);

        tabPanel.add("Processor", processorPanel);
        tabPanel.add("Program", programPanel);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.setSize(540, 380);
        frame.getContentPane().add(tabPanel);

        return frame;
    }

    public static void main(String[] args) {
        final MiniPowerPcUi ui = new MiniPowerPcUi();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = ui.createUi();
                frame.setVisible(true);
            }
        });
    }
}
