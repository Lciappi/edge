package model;

import sun.awt.image.ImageWatched;

import java.util.LinkedList;

public class Lender extends User {

    private double potentialInterest;         // interest that will eventually be collected when loan ends
    private double amountLent;               // how much money has been lent out



    private LinkedList<Borrower> portfolio; // who the lender has given money to

    public Lender(int id, String name) {
        super(id, name);
        this.portfolio = new LinkedList<>();
    }

    //REQUIRES: amount >= 0, amount <= balance
    //MODIFIES: this, User
    //EFFECTS: removes amount from balance, adds amount to amountLent, adds interest
    //         returns the user's balance

    public double loanMoney(double amount, double interest) {
        super.withdraw(amount);
        this.amountLent += amount;
        this.potentialInterest += interest;
        return super.getBalance();

    }

    //MODIFIES: this
    //EFFECTS: adds borrower to linkedlist
    public void addBorrower(Borrower client) {
        this.portfolio.add(client);
    }

    public double getPotentialInterest() {
        return potentialInterest;
    }

    public LinkedList<Borrower> getPortfolio() {
        return portfolio;
    }

    public double getAmountLent() {
        return Math.round(amountLent * 100) / 100D;

    }
}
