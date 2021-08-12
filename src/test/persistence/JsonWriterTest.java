package persistence;

import model.Borrower;
import model.Lender;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
//ADAPTED FROM JsonSerializationDemo

public class JsonWriterTest extends JsonTest{
    @Test
    void testWriterInvalidFile() {
        try {
            Lender wlender = new Lender(1, "I am Failure");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyPortfolio() {
        try {
            Lender wlender = new Lender(11,"Pat");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyPortfolio.json");
            writer.open();
            writer.write(wlender);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyPortfolio.json");

            wlender = reader.read();
            assertEquals("Pat", wlender.getName());
            assertEquals(13, wlender.getId());
            assertEquals(0, wlender.getPortfolio().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralLender() {
        try {
            Lender wlender = new Lender(12,"Zach");
            wlender.addBorrower(new Borrower(13, "Brown"));
            wlender.addBorrower(new Borrower(16, "Baptiste"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralPortfolio.json");
            writer.open();
            writer.write(wlender);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralPortfolio.json");
            wlender = reader.read();
            assertEquals("Zach", wlender.getName());

            LinkedList<Borrower> porta = wlender.getPortfolio();
            assertEquals(2, porta.size());
            checkBorrower(15,0,"Brown",1,0,0,0,porta.get(0));
            checkBorrower(18,0,"Baptiste",1,0,0,0,porta.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
