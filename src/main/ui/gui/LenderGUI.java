package ui.gui;

import exceptions.NoNameException;
import model.Lender;
import persistence.JsonReader;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

// Provides a user interface for lenders
public class LenderGUI extends UserInterface implements ActionListener {

    private JLabel greetLabel;
    protected JTextField nameField = new JTextField(5);
    private JButton editName = new JButton("Add Name");

    private JLabel amountLent = new JLabel("Amount Lent: ");
    private JLabel amountInterest = new JLabel("Interest Owed: ");
    private JButton addBorrower = new JButton("Add Borrower");

    String[] columnNames;

    public LenderGUI(Boolean loadData) {
        super("Lender GUI");
        this.loadData = loadData;

        init();
        columnNames = new String[]{"Name", "Amount Borrowed", "Interest Owed"};
        guiElements();

        checkNLoadData();
        displayUserInfo();
        displayLenderInfo();
        displayTable(columnNames, lender.getPortfolio());

        add(addBorrower, BOTTOM_ALIGNMENT);
        add(amountLent, CENTER_ALIGNMENT);
        add(amountInterest, CENTER_ALIGNMENT);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
        this.add(new JLabel(new ImageIcon("data/logo.png")));

        manageExitPrompt(lender);
    }


    //EFFECTS: manages events
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("changeName")) {
            String name = nameField.getText();
            fixName(name);
        } else {
            try {
                if (lender.getName() == null) {
                    throw new NoNameException("Lender has no name");
                } else {
                    if (e.getActionCommand().equals("addBorrower")) {
                        new AddBorrowerGUI(lender);
                        this.dispose();
                    }
                    if (e.getActionCommand().equals("Deposit")) {
                        depositButton(lender);
                    }
                    if (e.getActionCommand().equals("Withdraw")) {
                        withdrawButton(lender);
                    }
                }
            } catch (NoNameException exception) {
                JOptionPane.showMessageDialog(this, "Must add your name first");
            }
        }
    }

    //REQUIRES: Lender to be initialized
    //EFFECTS: displays userinfo
    private void displayLenderInfo() {
        amountInterest.setText("Potential Interest: " + lender.getPotentialInterest());
        amountLent.setText("Amount Lent: " + lender.getAmountLent());
        addBorrower.setActionCommand("addBorrower");
        addBorrower.addActionListener(this);


    }

    //modifies:THIS
    //EFFECTS: remove TextFields and Buttons relating to name change
    private void fixName(String name) {
        lender.setUserName(name);
        editName.setVisible(false);
        nameField.setVisible(false);
        greetLabel.setText("Hi, " + name + ", welcome!");

    }

    //MODIFIES: This
    //EFFECTS: initialize JFrame
    private void init() {

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setPreferredSize(new Dimension(1024, 600));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));
        setLayout(new FlowLayout());
        this.setLocationRelativeTo(null);


    }

    //MODIFIES: this
    //EFFECTS: initializes and manages gui elements
    private void guiElements() {
        editName.setActionCommand("changeName");
        editName.addActionListener(this);
        greetLabel = new JLabel("Welcome, please add your name");

        deposit = new JButton("Deposit");
        deposit.addActionListener(this);
        deposit.setActionCommand("Deposit");

        withdraw = new JButton("Withdraw");
        withdraw.setActionCommand("Withdraw");
        withdraw.addActionListener(this);

        add(greetLabel,LEFT_ALIGNMENT);
        add(deposit, LEFT_ALIGNMENT);
        add(withdraw, LEFT_ALIGNMENT);
        add(nameField, TOP_ALIGNMENT);
        add(editName, TOP_ALIGNMENT);

    }

    //MODIFIES: this
    //EFFECTS: if user chose to load data, it reads from JSON file lender class
    private void checkNLoadData() {
        if (loadData) {
            JsonReader reader = new JsonReader("./data/lender.json");
            try {
                lender = reader.read();
                fixName(lender.getName());

            } catch (Exception e) {
                System.out.println("Bank file not found");
            }
        } else {
            lender = new Lender(super.bank.getPortfolio().size() + 1, nameField.getName());
        }
    }


}
