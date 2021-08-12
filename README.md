# CPSC 210 Project 

## Edge  -*Small loans for great ideas*

Description of Edge:

Peer to peer loans are revolutionising the financial industry. The idea is to create a 
platform where people who need money (borrowers) and investors willing to give money
(lenders) can meet. Borrowers get the money today by promising lenders that they will
pay back the money, with an additional interest rate. Investors are willing to take on
the risk (of not getting their money back) in order to have more money in the future.
This concept is nothing new, and banks have been doing it for centuries, however, by
removing them from the transactions, investors can get higher returns and borrowers
get lower interest rates. 


The program will have 2 types of users:
 - borrowers
 - lenders
 
 Rationale:

As someone that has lived in capitalist countries all my life, I know how
important it is for people to have access to money. Loans make the economy work,
and they can help materialize great ideas and innovations. Peer to peer lending 
also allows lenders to have complete control of how their money is being allocated,
as opposed to traditional bank accounts, were customers do not have a say on how their
money is used. Furthermore, in the future I might be inclined to apply for CS jobs that 
require finance knowledge, and that is why this project may be helpful in the future.
 
 ## User Stories
 
 "-" Phase 1 Stories , "*" Phase 2 Stories
 
 - As a user, I want to be able to deposit money to the user class
 - As a user, I want to be able to withdraw money from the user class
 - As a user, I want to be able to set my name
 - As a user, I want to be able to see the balance in my account
 
 - As a lender, I want to be able to add a borrower to my portfolio
 - As a lender, I want to be able to view the list of borrowers on my portfolio
 - As a lender, I want to be able to view the interest I have collected
 - As a lender, I want to be able to see how much money I have lent out
 - As a lender, I want to be able to select a borrower and process the loan
 * As a lender, I want to be able to choose between loading previous session or reset memory
        -> A session means saving all the fields in SuperType User and SubType Lender
 * As a lender, I want to have the option to save a session before quitting
 
 - As a borrower, I want to be able to see how much money I owe 
 - As a borrower, I want to be able to pay off my debt
 - As a borrower, I want to be able to see how much interest I owe 
 - As a borrower, I want to be able to pay off my Interest
 
 ####Phase 4: Task 2
 
 For this task I decided to make Lender, and its superclass User, robust through the use of Exceptions. I decided to
 incorporate 5 exceptions.
 
 - FailedToSaveFileException
 
 This exception is thrown in Lender's toJson method. It is then caught in the user interface and displayed as an
 error message to alert the user that the file was not properly saved.
 - InsufficientFundsException
 
 This exception is used both in Lender and User. It is used when the operation that is being performed requires
 more money than what currently is available in the user's account. Its most intriguing use is in the loanMoney method.
 LoanMoney throws the insufficient funds exception, but instead of being caught by the method that calls it (procesLoan)
 it is thrown again, and finally caught in the user interface.
 - NegativeNumberException
 
 This exception ensures that that methods such as deposit and withdraw do not have negative numbers (which would, for
 example, allow a withdraw to increase the user's balance).
 - NoNameException
 
 This was part of an implementation in order to ensure that LenderGUI's method actionPerformed() did not exceed the 
 checkstyle.
 - RunTimeNegativeNumberException 
 
 I chose to have a unchecked alternative to NegativeNumberException because for the method deposit, in the user's class,
 I did not want to add a try and catch everytime deposit is called. This is because deposit is sometimes used as a 
 "setBalance". So, whenever deposit is used as a setBalance, such as in the JsonWriter class, the exception goes 
 unchecked, but if deposit handles a user's input, then it will catch the exception and display an error message. 
 

