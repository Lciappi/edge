package model;

public class Investments {

    private String companyName; //name of company
    private String description; //description of what it does
    private int riskScore;      //amount of risk determined by admin (0/5)No risk, (5/5) High risk
    private int requestedLoan;  //amount that the company seeks to obtain
    private int balance;        //amount that has been invested
    private int interest;       //interest that will be payed on requestedLoan

    public Investments(String companyName, String description,
                       int riskScore, int requestedLoan, int interest) {
        this.companyName = companyName;
        this.description = description;
        this.riskScore = riskScore;
        this.requestedLoan = requestedLoan;
        this.balance = 0;
        this.interest = interest;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getDescription() {
        return description;
    }

    public int getRiskScore() {
        return riskScore;
    }

    public int getRequestedLoan() {
        return requestedLoan;
    }

    public int getBalance() {
        return balance;
    }


    public int getInterest() {
        return interest;
    }


}
