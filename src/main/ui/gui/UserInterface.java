package ui.gui;

import exceptions.FailedToSaveFileException;
import exceptions.InsufficientFundsException;
import exceptions.NegativeNumberException;
import exceptions.RuntimeNegativeNumberException;
import jdk.nashorn.internal.scripts.JO;
import model.Borrower;
import model.Lender;
import model.User;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;

import persistence.JsonReader;
import  sun.audio.*;    //import the sun.audio package
import ui.Main;

import  java.io.*;

//common functionality among users ui
public class UserInterface extends JFrame {
//    protected LinkedList<Borrower> availableBorrowers;
    protected Lender bank;
    protected Lender lender;
    protected JTable table;
    protected Boolean loadData;
    protected JButton deposit;
    protected JButton withdraw;
    JScrollPane scrollPane;
    protected JLabel balance = new JLabel("Balance: ");


    //MODIFIES: this, super
    //EFFECTS: constructor class that initializes variables
    public UserInterface(String st) {
        super(st);
        refreshBank();
    }

    //CODE COPIED FROM: https://stackoverflow.com/questions/15449022/show-prompt-before-closing-jframe
    //EFFECTS: Creates a prompt to ask user if he wants to save the session
    protected void manageExitPrompt(Lender lender) {
        UserInterface that = this;
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                String[] objButtons;
                objButtons = new String[]{"Yes","No"};
                int promptResult = JOptionPane.showOptionDialog(null,
                        "Do you want to save the session?","Exit",
                        JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,objButtons,objButtons[1]);
                if (promptResult == JOptionPane.YES_OPTION) {
                    try {
                        lender.saveFile();
                        bank.saveFile();
                    } catch (FailedToSaveFileException e) {
                        JOptionPane.showMessageDialog(that,"Error! Could not save file");
                    }
                    System.exit(0);
                } else if (promptResult == JOptionPane.NO_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    //EFFECTS: display the borrowers in Lender class as a scrollable pane
    protected void displayTable(String[] columnNames, LinkedList<Borrower> listOfBorrowers) {
        this.table = createTable(columnNames, listOfBorrowers);
        //Create the scroll pane and add the table to it.
        scrollPane = new JScrollPane(table);
        //Add the scroll pane to this panel.
        add(scrollPane);

    }

    //EFFECTS: creates a JTable from a list of borrowers
    protected JTable createTable(String[] columnNames, LinkedList<Borrower> availableBorrowers) {
        Object[][] data = new Object[availableBorrowers.size()][4];
        int index = 0;
        for (Borrower b : availableBorrowers) {
            data[index][0] = b.getUserName();
            data[index][1] = b.getAmountBorrowed();
            data[index][2] = b.getInterestOwed();
            data[index][3] = b.getRiskScore();
            index += 1;
        }

        table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(800, 200));
        table.setFillsViewportHeight(true);
        table.setDefaultEditor(Object.class, null);
        return table;
    }

    //EFFECTS: Displays user information
    protected void displayUserInfo() {
        balance.setText("Balance: " + lender.getBalance());
        balance.setForeground(Color.red);
        add(balance, RIGHT_ALIGNMENT);

    }

    //EFFECTS: manages deposits, updates balance in gui
    //MODIFIES: this, User
    protected void depositButton(User u) {

        JLabel c = new JLabel("Please enter amount to deposit");
        String input = JOptionPane.showInputDialog(c);
        try {
            u.deposit(Double.parseDouble(input));
            balance.setText("Balance: " + u.getBalance());
            playSound("data/depositName.wav");

        } catch (RuntimeNegativeNumberException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected Action");
            e.printStackTrace();
        }

    }

    //EFFECTS: this
    public void playSound(final String url) {
        InputStream sound;
        try {
            sound = new FileInputStream(new File(url));
            AudioStream stream = new AudioStream(sound);
            AudioPlayer.player.start(stream);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //EFFECTS: manages withdraws, updates balance in gui
    //MODIFIES: this, User
    protected void withdrawButton(User u) {
        JLabel c = new JLabel("Please enter amount to withdraw");
        String input = JOptionPane.showInputDialog(c);

        try {
            u.withdraw(Double.parseDouble(input));
            playSound("data/withdraw.wav");
            JOptionPane.showMessageDialog(this, "Success!");
            balance.setText("Balance: " + u.getBalance());
        } catch (InsufficientFundsException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (NegativeNumberException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }

    }

    protected  void refreshBank() {
        try {
            JsonReader reader = new JsonReader("./data/bank.json");
            bank = reader.read();
        } catch (Exception e) {
            System.out.println("File not found");
        }
    }
}
