package model;

import com.sun.org.apache.xpath.internal.operations.Neg;
import exceptions.NegativeNumberException;
import org.json.JSONObject;
import persistence.Writable;

// A type of edge user that can borrow money
public class Borrower extends User implements Writable {


    private double interestRate;     //cost of borrowing money as a percentage
    private double amountBorrowed;   //amount of money borrowed
    private double riskScore;        //how risky the user is (based on number of loans & payments)
    private double interestOwed;  //how much money they owe in interest payments

    //REQUIRES: id must be a unique positive integer that has not been used before
    //EFFECTS: passes id and name to supertype constructor, sets interest rate for loans
    //         and sets amount borrowed to 0
    //MODIFIES: this, user
    public Borrower(int previousId, String name) {
        super(previousId + 1, name);
        this.amountBorrowed = 0;
        this.riskScore = 0;
        setInterest();

    }

    //REQUIRES: amount must be double > 0
    //MODIFIES: this, User
    //EFFECTS:  -updates amount borrowed by amount
    //          -updates risk score
    //          -deposits borrowed money to user's account
    //          -updates interest owed
    //          -sets new interest after borrowing money
    public void loan(double amount) {
        this.amountBorrowed += amount;
        this.riskScore += 100;
        super.deposit(amount);
        this.interestOwed += amount * (interestRate / 100d);
        setInterest();

    }

    //REQUIRES: amount must be double > 0 and < than interestOwed
    //MODIFIES: this
    //EFFECTS:  reduces interest by amount, reduces riskScore,
    //          returns interestOwed
    public void payInterest(double amount) {
        this.riskScore -= (amount / interestOwed) * 50;
        this.interestOwed -= amount;

    }

    //REQUIRES: amount must be double > 0 and < amountBorrowed
    //MODIFIES: this
    //EFFECTS:  reduces loan by amount, reduces riskScore by formula,
    //          returns amountBorrowed
    public double payLoan(double amount) {
        this.riskScore = riskScore - (amount / amountBorrowed) * 50;
        this.amountBorrowed -= amount;
        return amountBorrowed;

    }

    //MODIFIES: this
    //EFFECTS: Calculates the interest borrowers will pay for loans and sets it
    public double setInterest() {
        this.interestRate = Math.pow(2, riskScore / 100d);
        return interestRate;

    }

    //EFFECTS: Converts borrower object to JsonObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("id", this.getId());
        json.put("name", this.getUserName());
        json.put("balance", this.getBalance());

        json.put("interestRate", this.getInterestRate());
        json.put("amountBorrowed", this.getAmountBorrowed());
        json.put("riskScore", this.getRiskScore());
        json.put("interestOwed", this.getInterestOwed());

        return json;
    }


    //getters and setters
    public double getRiskScore() {
        return riskScore;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public double getInterestOwed() {
        return interestOwed;
    }

    public double getAmountBorrowed() {
        return amountBorrowed;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public void setAmountBorrowed(double amountBorrowed) {
        this.amountBorrowed = amountBorrowed;
    }

    public void setRiskScore(double riskScore) {
        this.riskScore = riskScore;
    }

    public void setInterestOwed(double interestOwed) {
        this.interestOwed = interestOwed;
    }
}
