package ui.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// manages load data prompt at start of the program
public class LoadDataPromptGUI extends JFrame implements ActionListener {

    private JLabel greetLabel;
    private JLabel greetLabel2;

    private JButton yesBtn = new JButton("yes");
    private JButton noBtn = new JButton("no");

    //EFFECTS: initializes required variables
    public LoadDataPromptGUI() {
        super("Load data");

        guiElements();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);

    }

    //MODIFIES: this
    //EFFECTS: initializes and manages gui elements
    private void guiElements() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 500));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(50, 50, 50, 50));
        setLayout(new FlowLayout());

        yesBtn.setActionCommand("yesBtn");
        yesBtn.addActionListener(this);
        yesBtn.setLocation(100,50);

        noBtn.setActionCommand("noBtn");
        noBtn.addActionListener(this);
        noBtn.setLocation(100,50);

        greetLabel = new JLabel("Do you want to load the previous session?");
        greetLabel2 = new JLabel("Welcome to the Platform");

        this.add(new JLabel(new ImageIcon("src/main/ui/gui/logo.png")));

        add(greetLabel2);
        add(greetLabel);
        add(yesBtn);
        add(noBtn);
    }

    //EFFECTS: manages events
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("yesBtn")) {
            System.out.println("Loading data");
            new LenderGUI(true);
            dispose();

        }
        if (e.getActionCommand().equals("noBtn")) {
            System.out.println("Data not loaded");
            new LenderGUI(false);
            dispose();

        }
    }

}
