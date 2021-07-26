package ui;
//THE CODE BELOW, WHILE NOT COPIED, IS HEAVILY INSPIRED FROM TELLERAPP EXAMPLE

import model.Borrower;
import model.Lender;
import model.User;

import java.util.LinkedList;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class EdgePlatform {
    private Scanner input;
    private Borrower borrower;
    private Lender lender;
    private User user;
    private LinkedList<Borrower> availableBorrowers;

    public EdgePlatform() {
        runApp();
    }

    public void runApp() {
        Boolean run = true;
        init();
        System.out.println("Welcome to the Edge Platform!");
        System.out.println("What is your name?:");
        String name = input.next();
        System.out.println(name + " would you like to borrow(type '1') money or lend(type '2') money?");
        String type = input.next();
        while (run) {
            if (type.equals("1")) {
                runBorrow(name);
                run = false;
            } else if (type.equals("2")) {
                runLend(name);
                run = false;
            } else if (run) {
                System.out.println("You didn't enter a valid option:");
                type = input.next();
            }
        }
    }

    private void runBorrow(String name) {
        boolean run = true;
        String command = null;
        borrower = new Borrower(availableBorrowers.size() + 1, name);

        while (run) {
            displayCommonMenu();
            command = input.next();
            command = command.toLowerCase();
            if (command.equals("4")) {
                displayBorrowMenu();
                command = input.next();
                processBorrowCommand(command);
            } else if (command.equals("q")) {
                run = false;
            } else {
                processCommonMenu(command, borrower);
            }
        }
        System.out.println("\nGoodbye!");
    }

    private void processCommonMenu(String command, User u) {
        if (command.equals("1")) {
            viewBalance(u);
        } else if (command.equals("2")) {
            doDeposit(u);
        } else if (command.equals("3")) {
            doWithdraw(u);
        } else {
            System.out.println("Selection not valid...");
        }

    }

    private void processBorrowCommand(String command) {
        if (command.equals("1")) {
            viewDebt();
        } else if (command.equals("2")) {
            viewInterest();
        } else if (command.equals("3")) {
            payDebtOrInterest(command);
            viewDebt();
            viewInterest();
        } else if (command.equals("4")) {
            payDebtOrInterest(command);
            viewDebt();
            viewInterest();
        } else if (command.equals("5")) {
            doDebt();;
        } else {
            System.out.println("Selection not valid...");
        }
    }

    private void viewDebt() {
        System.out.println("Current debt: " + borrower.getAmountBorrowed());
    }

    private void viewInterest() {
        System.out.println("Current interest owed: " + borrower.getInterestOwed());
    }

    private void viewBalance(User u) {
        System.out.println("Your current balance is " + u.getBalance());
    }

    private void doDebt() {

        System.out.println("Please enter amount to debt to have: ");
        double amount = input.nextDouble();

        if (amount >= 0.0) {
            borrower.loan(amount);
        } else {
            System.out.println("Cannot borrow a negative amount...\n");
        }

        viewDebt();
        viewInterest();

    }

    private void payDebtOrInterest(String command) {
        System.out.println("Please enter amount to pay off: ");
        double amount = input.nextDouble();
        if (amount >= 0.0) {
            if (command.equals("3")) {
                if (amount <= borrower.getAmountBorrowed()) {
                    borrower.payLoan(amount);
                } else {
                    System.out.println("You owe less money");
                }
            } else if (command.equals("4")) {
                if (amount <= borrower.getInterestOwed()) {
                    borrower.payInterest(amount);
                } else {
                    System.out.println("You owe less interest");
                }
            }
        } else {
            System.out.println("Cannot pay a negative amount...\n");
        }
    }


    //doDepsosit taken from teller class
    // MODIFIES: this
    // EFFECTS: conducts a deposit transaction
    private void doDeposit(User u) {
        System.out.println("Please enter amount to deposit: ");
        double amount = input.nextDouble();

        if (amount >= 0.0) {
            u.deposit(amount);
        } else {
            System.out.println("Cannot deposit negative amount...\n");
        }

        viewBalance(u);
    }

    private void doWithdraw(User u) {
        System.out.println("Please enter amount to withdraw: ");
        double amount = input.nextDouble();

        if (amount >= 0.0) {
            if (!u.withdraw(amount)) {

                System.out.println("You have insufficient funds!");
            }

        } else {
            System.out.println("Cannot deposit negative amount...\n");
        }
        viewBalance(u);
    }

    private void displayCommonMenu() {
        System.out.println("Please select an item from the menu");
        System.out.println("1 - See Balance");
        System.out.println("2 - Deposit");
        System.out.println("3 - Withdraw");
        System.out.println("4 - More Options");
        System.out.println("Q - To quit");
    }

    private void displayBorrowMenu() {
        System.out.println("Please select an item from the menu");
        System.out.println("1 - Current Debt");
        System.out.println("2 - Current interest payments");
        System.out.println("3 - Pay-off Debt");
        System.out.println("4 - Pay-off Interest");
        System.out.println("5 - Take out debt");
        System.out.println("Q - To quit");

    }

    private void runLend(String name) {
        boolean run = true;
        String command = null;
        lender = new Lender(availableBorrowers.size() + 1, name);

        while (run) {
            displayCommonMenu();
            command = input.next();
            command = command.toLowerCase();
            if (command.equals("4")) {
                displayLenderMenu();
                command = input.next();
                processLenderCommand(command);
            } else if (command.equals("q")) {
                run = false;
            } else {
                processCommonMenu(command, lender);
            }
        }
        System.out.println("\nGoodbye!");
    }

    private void processLenderCommand(String command) {

        if (command.equals("1")) {
            viewLoanedAmount();
        } else if (command.equals("2")) {
            viewInterestAccrued();
        } else if (command.equals("3")) {
            chooseBorrower();
        } else if (command.equals("4")) {
            viewPortfolio();

        } else {
            System.out.println("Selection not valid...");
        }


    }

    private void viewPortfolio() {
        for (Borrower item : lender.getPortfolio()) {
            System.out.println(item.getName() + " owes you " + item.getAmountBorrowed() + " with "
                    + item.getInterestOwed() + " in interest");
        }
    }

    private void chooseBorrower() {
        int counter = 0;
        System.out.println("Please choose a borrower, Or, press 'q' to cancel: ");
        for (Borrower candidate : availableBorrowers) {
            System.out.println(counter + " - " + candidate.getName() + " needs: $" + candidate.getAmountBorrowed()
                    + " and  $" + candidate.getInterestOwed() + " payed in interest");
            counter += 1;
        }
        String command = input.next();
        Borrower finalist = availableBorrowers.get(parseInt(command));
        if (command.equals("q")) {
            System.out.println("Leaving ...");
        } else {
            processLoan(finalist);

        }
    }

    private void processLoan(Borrower finalist) {

        if (finalist.getAmountBorrowed() <= lender.getBalance()) {
            lender.addBorrower(finalist);
            lender.loanMoney(finalist.getAmountBorrowed(), finalist.getInterestOwed());
            System.out.println(finalist.getName() + " has been added to your portfolio");
        } else {
            System.out.println("You have insufficient funds to write this loan");
        }
    }

    private void viewInterestAccrued() {
        System.out.println("You have earned: " + lender.getPotentialInterest());

    }

    private void viewLoanedAmount() {
        System.out.println("You have lent out: " + lender.getAmountLent());
    }

    private void displayLenderMenu() {
        System.out.println("Please select an item from the menu");
        System.out.println("1 - Current money being lent");
        System.out.println("2 - Current interest payments due");
        System.out.println("3 - Add a borrower to your portfolio");
        System.out.println("4 - View who owes you money");
        System.out.println("Q - To quit");
    }



    private void init() {
        input = new Scanner(System.in);
        availableBorrowers = new LinkedList<>();
        Borrower john = new Borrower(1, "John");
        Borrower matthew = new Borrower(1, "Matthew");
        Borrower igor = new Borrower(1, "Igor");
        availableBorrowers.add(john);
        availableBorrowers.add(matthew);
        availableBorrowers.add(igor);
        john.loan(100);
        john.loan(1505);
        john.loan(20);

        matthew.loan(5000);
        matthew.loan(200);
        matthew.payInterest(10);
        matthew.payLoan(200);
        igor.loan(1400);
    }


}





