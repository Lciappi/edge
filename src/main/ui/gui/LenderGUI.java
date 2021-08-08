package ui.gui;

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

        checkNLoadData();
        guiElements();
        displayUserInfo();
        displayLenderInfo();
        displayTable(columnNames, lender.getPortfolio());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);

        manageExitPrompt(lender);
    }


    //EFFECTS: manages events
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("changeName")) {
            String name = nameField.getText();
            lender.setUserName(name);
            fixName(name);
        }

        if (e.getActionCommand().equals("addBorrower")) {
            new AddBorrowerGUI(lender, this);
            System.out.println("Add Borrowers");
            this.dispose();
        }

        if (e.getActionCommand().equals("Deposit")) {
            depositButton(lender);
        }

        if (e.getActionCommand().equals("Withdraw")) {
            withdrawButton(lender);
        }
    }

    //REQUIRES: Lender to be initialized
    //EFFECTS: displays userinfo
    private void displayLenderInfo() {
        amountInterest.setText("Potential Interest: " + lender.getPotentialInterest());
        amountLent.setText("Amount Lent: " + lender.getAmountLent());
        addBorrower.setActionCommand("addBorrower");
        addBorrower.addActionListener(this);

        add(amountLent);
        add(amountInterest);
        add(addBorrower);

    }

    //modifies:THIS
    //EFFECTS: remove TextFields and Buttons relating to name change
    private void fixName(String name) {
        editName.setVisible(false);
        nameField.setVisible(false);
        greetLabel.setText("Hi, " + name + ", welcome!");

    }

    //MODIFIES: This
    //EFFECTS: initialize JFrame
    private void init() {

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setPreferredSize(new Dimension(1024, 720));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(500, 350, 500, 350));
        setLayout(new FlowLayout());


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


        add(deposit);
        add(withdraw);
        add(nameField);
        add(editName);
        add(greetLabel);

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
                System.out.println("File not found");
            }
        } else {
            lender = new Lender(availableBorrowers.size() + 1, nameField.getName());
        }
    }

}