package model;

import exceptions.InsufficientFundsException;
import exceptions.NegativeNumberException;
import exceptions.RuntimeNegativeNumberException;

// A class for all the users of the Edge platform
public class User {

    private int id;                  // unique userid
    private String userName;        // name of investor
    private double balance;        // amount of money in the account

    //REQUIRES: id must be a unique positive integer, userName length >= 1
    //EFFECTS: Sets balance to 0, sets id to user, sets username
    public User(int id, String name) {
        this.balance = 0;
        this.id = id;
        this.userName = name;
    }

    //MODIFIES: this
    //EFFECTS: increase balance by amount, throws negative exception if amount is negative
    //         I choose deposit to throw a runtime exception instead of a check exception because
    //         deposit is sometimes used as setBalance(), which means amount will not be negative(not set by user)
    public void deposit(double amount) throws RuntimeNegativeNumberException {
        if (amount < 0) {
            throw new RuntimeNegativeNumberException("Cannot deposit Negative Number");
        }
        this.balance += amount;
    }

    //MODIFIES: this
    //EFFECTS: if amount is smaller than balance
    //         - then it removes amount from balance
    //         - returns true
    //         otherwise, not then it only returns false
    public void withdraw(double amount) throws InsufficientFundsException, NegativeNumberException {
        if (amount <= 0) {
            throw new NegativeNumberException("Cannot withdraw negative or Zero amount");
        }
        if (amount <= balance) {
            balance = balance - amount;
        } else {
            throw new InsufficientFundsException("Insufficient Funds to withdraw");
        }

    }

    //EFFECTS: gets balance and rounds it to 2 decimal places
    public double getBalance() {
        return Math.round(balance * 100) / 100D;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String username) {
        this.userName = username;
    }

    public String getName() {
        return userName;
    }
}
