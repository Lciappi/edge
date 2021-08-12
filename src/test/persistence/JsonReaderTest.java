package persistence;

import model.Borrower;
import model.Lender;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

//CODE ADAPTED FROM JsonSerializationDemo

public class JsonReaderTest extends JsonTest{
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Lender rlender = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyPortfolio() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyPortfolio.json");
        try {
            Lender rlender = reader.read();

            assertEquals("John", rlender.getName());
            assertEquals(1000.32, rlender.getBalance());
            assertEquals(2, rlender.getId());
            assertEquals(0, rlender.getAmountLent());
            assertEquals(0, rlender.getPotentialInterest());
            assertEquals(0, rlender.getPortfolio().size());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralLender() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralLender.json");
        try {
            Lender rlender = reader.read();

            assertEquals("John", rlender.getName());
            assertEquals(256456.32, rlender.getBalance());
            assertEquals(3, rlender.getId());
            assertEquals(1000, rlender.getAmountLent());
            assertEquals(50, rlender.getPotentialInterest());


            List<Borrower> portfolio = rlender.getPortfolio();

            assertEquals(2, rlender.getPortfolio().size());
            checkBorrower(4, 51.32, "Mayer", 0.05, 500, 1, 25, portfolio.get(0));
            checkBorrower(5, 23.2, "Pino", 0.05, 500, 4, 25, portfolio.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
