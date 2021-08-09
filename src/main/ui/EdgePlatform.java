package ui;
//THE CODE BELOW, WHILE NOT COPIED, IS HEAVILY INSPIRED FROM TELLERAPP EXAMPLE

import model.Borrower;
import model.Lender;
import model.User;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.util.LinkedList;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

// Old UI used for console interface
public class EdgePlatform {
    private Scanner input;
    private Borrower borrower;
    private Lender lender;
    private LinkedList<Borrower> availableBorrowers;

    //EFFECTS: runs the Edge platform
    public EdgePlatform() {
        runApp();
    }

    //EFFECTS: establishes whether user is a borrower or lender
    public void runApp() {
        Boolean run = true;
        init();
        System.out.println("Welcome to the Peer to Peer Lending Platform");
        System.out.println("What is your name?:");
        String name = input.next();
        System.out.println(name + " would you like to borrow(1) money or lend(2) money? # only lend can save session");
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



    //MODIFIES: this
    //EFFECTS: instantiates borrower and runs different menus depending on input
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
                run = endEdge();
            } else {
                processCommonMenu(command, borrower);
            }
        }
        System.out.println("\nGoodbye!");
    }

    //EFFECTS: processes menu for guests
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

    //processes menu for borrowers
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

    //EFFECTS: processes menu and input for lenders
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

    private void viewDebt() {
        System.out.println("Current debt: " + borrower.getAmountBorrowed());
    }

    private void viewInterest() {
        System.out.println("Current interest owed: " + borrower.getInterestOwed());
    }

    private void viewBalance(User u) {
        System.out.println("Your current balance is " + u.getBalance());
    }

    //MODIFIES: this
    //EFFECTS: helps client take on debt
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

    //MODIFIES: this
    //EFFECTS: will allow customers to pay debt
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


    //doDepsosit adapted from teller class
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

    // MODIFIES: this
    // EFFECTS: conducts a withdraw transaction
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

    //EFFECTS: Display menu
    private void displayCommonMenu() {
        System.out.println("Please select an item from the menu");
        System.out.println("1 - See Balance");
        System.out.println("2 - Deposit");
        System.out.println("3 - Withdraw");
        System.out.println("4 - More Options");
        System.out.println("Q - To quit");
    }

    //EFFECTS: Display menu
    private void displayBorrowMenu() {
        System.out.println("Please select an item from the menu");
        System.out.println("1 - Current Debt");
        System.out.println("2 - Current interest payments");
        System.out.println("3 - Pay-off Debt");
        System.out.println("4 - Pay-off Interest");
        System.out.println("5 - Take out debt");
        System.out.println("Q - To quit");

    }

    //EFFECTS: Display menu
    private void displayLenderMenu() {
        System.out.println("Please select an item from the menu");
        System.out.println("1 - Current money being lent");
        System.out.println("2 - Current interest payments due");
        System.out.println("3 - Add a borrower to your portfolio");
        System.out.println("4 - View who owes you money");
        System.out.println("Q - To quit");
    }

    //MODIFIES: this
    //EFFECTS: allows lenders to lend money and gives option to recover session
    private void runLend(String name) {
        boolean run = true;
        loadDataPrompt(name);
        while (run) {
            displayCommonMenu();
            String command = input.next();
            command = command.toLowerCase();
            if (command.equals("4")) {
                displayLenderMenu();
                command = input.next();
                processLenderCommand(command);
            } else if (command.equals("q")) {
                run = endEdge();
            } else {
                processCommonMenu(command, lender);
            }
        }
        System.out.println("\nGoodbye!");
    }

    //MODIFIES: lender
    //EFFECTS: loads data prompt from json
    public void loadDataPrompt(String name) {

        System.out.println("Do you want to load the previous session? ");
        System.out.println("Yes(1), No(2)");
        String decision = input.next();
        if (decision.equals("1")) {
            try {
                JsonReader reader = new JsonReader("./data/lender.json");
                lender = reader.read();

            } catch (Exception e) {
                System.out.println("File not found");
            }
        } else {
            lender = new Lender(availableBorrowers.size() + 1, name);
        }


    }

    //EFFECTS: Display menu
    private void viewPortfolio() {
        for (Borrower item : lender.getPortfolio()) {
            System.out.println(item.getName() + " owes you " + item.getAmountBorrowed() + " with "
                    + item.getInterestOwed() + " in interest");
        }
    }

    //EFFECTS: prints accrued interest
    private void viewInterestAccrued() {
        System.out.println("You have earned: " + lender.getPotentialInterest());

    }

    //EFFECTS: prints loaned amount
    private void viewLoanedAmount() {
        System.out.println("You have lent out: " + lender.getAmountLent());
    }

    //EFFECTS: allows lender to choose borrower
    //MODIFIES: this, Lender
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
            lender.processLoan(finalist);

        }
    }

    //MODIFIES: this, lender.json
    //EFFECTS: saves lender to json if user wants to
    public Boolean endEdge() {
        System.out.println("Do you want to save the session?");
        System.out.println("Yes(1), No(2)");
        String reader = input.next();
        if (reader.equals("1")) {
            try {
                JsonWriter edgarAllenPoe = new JsonWriter("./data/lender.json");

                edgarAllenPoe.open();
                edgarAllenPoe.write(lender);
                edgarAllenPoe.close();
                System.out.println("Session was saved");
            } catch (Exception e) {
                System.out.println("The file could not be saved");
            }
        } else  {
            System.out.println("Session was not saved");
        }
        System.out.println("Have a great day!");
        return false;
    }


    //MODIFIES: This
    //EFFECTS: initialize variables
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





