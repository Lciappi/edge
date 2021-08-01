package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LenderTest {
    private Lender testUser;
    private Borrower testBorrower;

    @BeforeEach
    void setUp() {
        testUser = new Lender(1, "Leonardo");
        testBorrower = new Borrower(2, "Lucca");
    }

    @Test
    void testConstructor() {
        assertEquals( 0, testUser.getBalance());
        assertEquals(1, testUser.getId());
        assertEquals("Leonardo", testUser.getName());

    }

    @Test
    void addBorrowergetPortolioTest(){
        Borrower b1 = new Borrower(123, "Pat");
        Borrower b2 = new Borrower(321, "John");
        Borrower b3 = new Borrower(231, "Clayton");

        testUser.addBorrower(b1);
        testUser.addBorrower(b2);
        testUser.addBorrower(b3);

        assertEquals(3, testUser.getPortfolio().size());
        assertTrue(testUser.getPortfolio().contains(b1));
        assertTrue(testUser.getPortfolio().contains(b2));
        assertTrue(testUser.getPortfolio().contains(b3));
    }

    @Test
    void testLoanPassMoney(){
        testUser.deposit(1000);

        assertEquals(321.4, testUser.loanMoney(678.6,33.93));

        assertEquals(321.4, testUser.getBalance());
        assertEquals(678.6, testUser.getAmountLent());
        assertEquals(33.93, testUser.getPotentialInterest());

    }

    @Test
    void processLoanTestPass(){
        testUser.deposit(2000);
        testBorrower.loan(500);

        testUser.processLoan(testBorrower);

        assertEquals(1, testUser.getPortfolio().size());
        assertEquals(1500, testUser.getBalance());
        assertEquals(500, testUser.getAmountLent());

    }

    @Test
    void processLoanTestFail(){
        testUser.deposit(2000);
        testBorrower.loan(3500);

        testUser.processLoan(testBorrower);

        assertEquals(0, testUser.getPortfolio().size());
        assertEquals(2000, testUser.getBalance());
        assertEquals(0, testUser.getAmountLent());

    }


}
