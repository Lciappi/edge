package persistence;

import model.Borrower;

import static org.junit.jupiter.api.Assertions.assertEquals;
// Adapted from JsonSerializationDemo
public class JsonTest {

    protected void checkBorrower(int id, double balance, String name, double ir, double ab, double rs, double io,
                                 Borrower b) {

        assertEquals(id, b.getId());
        assertEquals(balance, b.getBalance());
        assertEquals(name, b.getName());
        assertEquals(ir, b.getInterestRate());
        assertEquals(ab, b.getAmountBorrowed());
        assertEquals(rs, b.getRiskScore());
        assertEquals(io, b.getInterestOwed());

    }
}
