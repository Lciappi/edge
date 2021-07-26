package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LenderTest {
    private Lender testUser;

    @BeforeEach
    void setUp() {
        testUser = new Lender(1, "Leonardo");
    }

    @Test
    void testConstructor() {
        assertEquals( 0, testUser.getBalance());
        assertEquals(1, testUser.getId());
        assertEquals("Leonardo", testUser.getName());

    }


    @Test
    void testLoanPassMoney(){
        testUser.deposit(1000);

        assertEquals(321.4, testUser.loanMoney(678.6,33.93));

        assertEquals(321.4, testUser.getBalance());
        assertEquals(678.6, testUser.getAmountLent());
        assertEquals(33.93, testUser.getPotentialInterest());

    }

}
