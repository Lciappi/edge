package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonWriter;
import persistence.Writable;

import java.util.LinkedList;

// Creates a type of edge user that can lend out their money
public class Lender extends User implements Writable {

    private double potentialInterest;          // interest that will eventually be collected when loan ends
    private double amountLent;                // how much money has been lent out
    private LinkedList<Borrower> portfolio;  // who the lender has given money to

    //REQUIRES: id must be unique positive integer and name should be at least size 1
    //EFFECTS: instantiates linkedlist, sets id and name in supertype
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

    //EFFECTS if lender can afford the loan, call loanmoney function, add borrower to portfolio
    //MODIFIES: this
    public Boolean processLoan(Borrower finalist) {

        if (finalist.getAmountBorrowed() <= this.getBalance()) {
            this.addBorrower(finalist);
            this.loanMoney(finalist.getAmountBorrowed(), finalist.getInterestOwed());
            System.out.println(finalist.getName() + " has been added to your portfolio");
            return true;
        } else {
            System.out.println("You have insufficient funds to write this loan");
            return false;
        }
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

    // toJson and portfolioToJson from SerializationDemo
    // TODO
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", this.getId());
        json.put("name", this.getUserName());
        json.put("balance", this.getBalance());

        json.put("potentialInterest", this.getPotentialInterest());
        json.put("amountLent", this.getAmountLent());
        json.put("portfolio", portfolioToJson());

        return json;
    }

    // EFFECTS: returns portfolio as a JSON Array with JSON objects
    public JSONArray portfolioToJson() {

        JSONArray jsonArray = new JSONArray();

        for (Borrower b : portfolio) {
            jsonArray.put(b.toJson());
        }

        return jsonArray;

    }

    public double getAmountLent() {
        return Math.round(amountLent * 100) / 100D;
    }

    public void setPotentialInterest(double potentialInterest) {
        this.potentialInterest = potentialInterest;
    }

    public void setAmountLent(double amountLent) {
        this.amountLent = amountLent;
    }

    public void saveFile() {
        JsonWriter edgarAllenPoe = new JsonWriter("./data/lender.json");
        try {
            edgarAllenPoe.open();
            edgarAllenPoe.write(this);
            edgarAllenPoe.close();

        } catch (Exception e) {
            System.out.println("Could not save file");
        }
    }
}
