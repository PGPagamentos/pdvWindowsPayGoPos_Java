package br.com.paygo.ui;

import br.com.paygo.PTI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;
import java.awt.*;

public class UserInterface {
    private final JFrame applicationWindow = new JFrame();
    private final JTextArea logArea = new JTextArea();
    private Thread ptiThread;
    private final PTI pti;

    public UserInterface() {
        pti = new PTI(this);

        logArea.setEditable(false);
        setupWindow();
    }

    private void setupWindow() {
        applicationWindow.setTitle("POS - Java");
        applicationWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        applicationWindow.setLayout(new GridLayout());
        applicationWindow.getContentPane().add(setUpPanel());
        applicationWindow.pack();
        applicationWindow.setVisible(true);
    }

    private JPanel setUpPanel() {
        JButton startButton = new JButton("Iniciar Aplicação");
        startButton.addActionListener(e -> {
            if (ptiThread == null) {
                ptiThread = new Thread(pti);
                ptiThread.start();
            }
        });

        JButton stopButton = new JButton("Parar Aplicação");
        stopButton.addActionListener(e -> {
            if (ptiThread != null) {
                pti.stopRunning();
                ptiThread = null;
            }
        });

        JButton clearLogButton = new JButton("Limpar Log");
        clearLogButton.addActionListener(e -> logArea.setText(""));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, 3));
        topPanel.setBorder(new EmptyBorder(5, 0, 10, 0));

        topPanel.add(startButton);
        topPanel.add(stopButton);
        topPanel.add(clearLogButton);

        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(logArea);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setPreferredSize(new Dimension(400, 375));

        DefaultCaret caret = (DefaultCaret)logArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        JPanel scrollWrapper = new JPanel();
        scrollWrapper.add(scroll);
        scrollWrapper.setBorder(new EmptyBorder(0, 0, 10, 0));

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(BorderLayout.NORTH, topPanel);
        panel.add(BorderLayout.CENTER, scrollWrapper);

        return panel;
    }

    public void alert(String message) {
        JOptionPane.showMessageDialog(applicationWindow, message);
    }

    public void logInfo(String message) {
        logArea.append(message + "\n");
    }
}
