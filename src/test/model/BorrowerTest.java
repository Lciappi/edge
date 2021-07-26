package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class BorrowerTest {
    private Borrower testUser;

    @BeforeEach
    void setUp() {
        testUser = new Borrower(1, "Leonardo");
    }

    @Test
    void testConstructor() {
        assertEquals( 0, testUser.getBalance());
        assertEquals(1, testUser.getId());
        assertEquals("Leonardo", testUser.getName());

        assertEquals(0, testUser.getAmountBorrowed());
        assertEquals(0, testUser.getRiskScore());
        assertEquals(1, testUser.getInterestRate());

    }

    @Test
    void testLoan(){
        testUser.deposit(100);
        testUser.loan(3251.5);

        assertEquals(3251.5, testUser.getAmountBorrowed());
        assertEquals(100, testUser.getRiskScore());
        assertEquals(3351.5, testUser.getBalance());
        assertEquals(32.515, testUser.getInterestOwed());

        //this section also tests setInterest function
        assertEquals(2, testUser.getInterestRate());


    }

    @Test
    void testMultipleLoan(){
        testUser.deposit(90);

        testUser.loan(1000);


        assertEquals(1000, testUser.getAmountBorrowed());
        assertEquals(100, testUser.getRiskScore());
        assertEquals(1090, testUser.getBalance());
        assertEquals(10, testUser.getInterestOwed());

        assertEquals(2, testUser.getInterestRate());

        testUser.loan(2000);

        assertEquals(3000, testUser.getAmountBorrowed());
        assertEquals(200, testUser.getRiskScore());
        assertEquals(3090, testUser.getBalance());
        assertEquals(50, testUser.getInterestOwed());

        assertEquals(4, testUser.getInterestRate());

        testUser.loan(500);

        assertEquals(3500, testUser.getAmountBorrowed());
        assertEquals(300, testUser.getRiskScore());
        assertEquals(3590, testUser.getBalance());
        assertEquals(70, testUser.getInterestOwed());

        //this section also tests setInterest function
        assertEquals(8, testUser.getInterestRate());


    }

    @Test
    void testPayLoan(){
        testUser.loan(5000);
        testUser.loan(2000.3);

        assertEquals(3744.04, testUser.payLoan(3256.26));

        assertEquals(7000.3-3256.26, testUser.getAmountBorrowed());
        assertEquals(176.74199677156693, testUser.getRiskScore());

    }

    @Test void testPayInterest(){
        testUser.loan(5000);
        testUser.loan(2000.3);

        assertEquals(3744.04, testUser.payLoan(3256.26));

        assertEquals(90.006, testUser.getInterestOwed());
        assertEquals(176.74199677156693, testUser.getRiskScore());

    }


}
