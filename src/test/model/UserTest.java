package model;

import static org.junit.jupiter.api.Assertions.*;

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

        assertFalse(testUser.withdraw(2120.21));
        assertEquals(1000, testUser.getBalance());
        assertFalse(testUser.withdraw(100000));
        assertEquals(1000, testUser.getBalance());

    }

    @Test
    void withdrawPassTest(){
        testUser.deposit(2312.65);
        assertTrue(testUser.withdraw(1000.30));
        assertEquals(1312.35, testUser.getBalance());
        assertTrue(testUser.withdraw(100));
        assertEquals(1212.35, testUser.getBalance());

    }


}