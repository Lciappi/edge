package ui.gui;

import exceptions.FailedToSaveFileException;
import exceptions.InsufficientFundsException;
import model.Borrower;
import model.Lender;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Creates GUI for lenders who want to add a borrower to their portfolio
public class AddBorrowerGUI extends UserInterface implements ActionListener {

    //declaring fields
    private JLabel tittleLabel;
    private JLabel instructions;
    private JButton submitLoan;
    private JButton exit;

    //EFFECTS: constructor class
    //MODIFIES: this, UserInterface
    public AddBorrowerGUI(Lender lender) {
        super("Add Borrower");
        this.lender = lender;

        guiElements();

        pack();
        setVisible(true);
        manageExitPrompt(lender);
    }

    //EFFECTS: manages events
    //MODIFIES: UserInterface
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("submitLoan")) {
            Borrower candidate = availableBorrowers.get(table.getSelectedRow());
            try {
                lender.processLoan(candidate);
                playSound("data/depositName.wav");
                JOptionPane.showMessageDialog(this, "Success!" + candidate.getAmountBorrowed()
                        + " were lent to " + candidate.getAmountBorrowed());
                balance.setText(Double.toString(lender.getBalance()));
            } catch (InsufficientFundsException exc) {
                playSound("data/withdraw.wav");
                JOptionPane.showMessageDialog(this,"Error! Insufficient Funds");
            }
        }
        if (e.getActionCommand().equals("exit")) {
            try {
                lender.saveFile();
            } catch (FailedToSaveFileException exception) {
                JOptionPane.showMessageDialog(this,"Error! Could not save file");
            }
            new LenderGUI(true);
            this.dispose();
        }
    }

    //EFFECTS: initializes and adds gui elements to panel
    //MODIFIES: this
    public void guiElements() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(50, 50, 50, 50));
        setLayout(new FlowLayout());
        this.setLocationRelativeTo(null);

        instructions = new JLabel("Please select a borrower to add to your portfolio");
        tittleLabel = new JLabel("Add Borrowers to Portfolio");
        submitLoan = new JButton("Grant Loan");

        submitLoan.setActionCommand("submitLoan");
        submitLoan.addActionListener(this);

        exit = new JButton("Go back");
        exit.setActionCommand("exit");
        exit.addActionListener(this);
        String[] columnNames = {"Name", "Amount Requested", "Potential Interest", "Risk Score"};

        add(tittleLabel, TOP_ALIGNMENT);
        displayUserInfo();
        displayTable(columnNames, availableBorrowers);
        add(submitLoan, BOTTOM_ALIGNMENT);
        add(exit, BOTTOM_ALIGNMENT);
        add(instructions);
    }
}
