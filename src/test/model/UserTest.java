package model;

import static org.junit.jupiter.api.Assertions.*;

import exceptions.InsufficientFundsException;
import exceptions.NegativeNumberException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTest {
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User(1, "Leonardo");
    }

    @Test
    void testConstructor() {
        assertEquals( 0, testUser.getBalance());
        assertEquals(1, testUser.getId());
        assertEquals("Leonardo", testUser.getName());
    }

    @Test
    void depositTest(){
        testUser.deposit(1000.1);
        assertEquals(testUser.getBalance(), 1000.1);
        testUser.deposit(312.55);
        assertEquals(testUser.getBalance(), 1312.65);

    }

    @Test
    void withdrawFailTest(){
        testUser.deposit(1000);
        try {
            testUser.withdraw(2120.21);
            fail("Should throw Exception");
        } catch (InsufficientFundsException e) {
        } catch (NegativeNumberException e) {
            fail("Should not throw negative number exceptiomn");
        }

        assertEquals(1000, testUser.getBalance());

        try {
            testUser.withdraw(100000);
            fail("Should throw Exception");
        } catch (InsufficientFundsException e) {
        } catch (NegativeNumberException e) {
            fail("Should not throw negative number exceptiomn");
        }
        assertEquals(1000, testUser.getBalance());

    }

    @Test
    void withdrawPassTest(){
        try {
            testUser.deposit(2312.65);
            testUser.withdraw(1000.30);
            assertEquals(1312.35, testUser.getBalance());
            testUser.withdraw(100);
            assertEquals(1212.35, testUser.getBalance());
        } catch (Exception e) {
            fail("Should not have thrown exceptions");
        }
    }


}