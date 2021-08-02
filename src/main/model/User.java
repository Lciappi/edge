package model;

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

    //REQUIRES: amount must be a positive double
    //MODIFIES: this
    //EFFECTS: increase balance by amount
    public void deposit(double amount) {
        this.balance += amount;
    }

    //REQUIRES: amount to be greater than 0
    //MODIFIES: this
    //EFFECTS: if amount is smaller than balance
    //         - then it removes amount from balance
    //         - returns true
    //         otherwise, not then it only returns false
    public boolean withdraw(double amount) {

        if (amount <= balance) {
            balance = balance - amount;
            return true;
        } else {
            return false;
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

    public String getName() {
        return userName;
    }
}
