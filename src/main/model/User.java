package model;

public class User {



    private int balance;           //amount of money in the account
    private int id;                //unique userid
    private String name;           //name of investor
    private Portfolio portfolio;   //investor's portfolio

    public User(int id, String name, Portfolio portfolio) {
        this.balance = 0;
        this.id = id;
        this.name = name;
        this.portfolio = new Portfolio(0,0);
    }


}
