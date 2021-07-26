package model;

import java.util.LinkedList;

public class Portfolio {

    private LinkedList<Investments> investments; // list of all investments
    private int projectedInterest;               // projected interest payed by all investments
    private int portfolioRiskScore;              // average of risk scores in all investments

    public Portfolio(int projectedInterest,
                     int portfolioRiskScore) {
        this.investments = new LinkedList<>();
        this.projectedInterest = 0;
        this.portfolioRiskScore = 0;
    }

    public LinkedList<Investments> getInvestments() {
        return investments;
    }

    public int getProjectedInterest() {
        return projectedInterest;
    }

    public int getPortfolioRiskScore() {
        return portfolioRiskScore;
    }
}
