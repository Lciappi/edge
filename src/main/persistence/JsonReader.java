
package persistence;

import model.Borrower;
import model.Lender;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;
// Code for JsonReader adapted from JsonSerializationDemo
// Represents a reader that reads Lender from JSON data stored in file

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads lender from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Lender read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseLender(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses lender from JSON object and returns it
    // MODIFIES: rlender
    private Lender parseLender(JSONObject jsonObject) {
        int id = jsonObject.getInt("id");
        String name = jsonObject.getString("name");
        double balance = jsonObject.getDouble("balance");
        double potentialInterest = jsonObject.getDouble("potentialInterest");
        double amountLent = jsonObject.getDouble("amountLent");

        Lender rlender = new Lender(id, name);
        rlender.deposit(balance);
        rlender.setPotentialInterest(potentialInterest);
        rlender.setAmountLent(amountLent);

        parsePortfolio(rlender, jsonObject);

        return rlender;
    }

    // MODIFIES: rlender
    // EFFECTS: parses Borrowers from JSON object and adds them to portfolio
    private void parsePortfolio(Lender rlender, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("portfolio");

        for (Object json : jsonArray) {
            JSONObject nextBorrower = (JSONObject) json;
            addBorrower(rlender, nextBorrower);
        }
    }

    // MODIFIES: rlender
    // EFFECTS: parses Borrower from JSON object and adds it to portfolio
    private void addBorrower(Lender rlender, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int id = jsonObject.getInt("id");
        double balance = jsonObject.getDouble("balance");
        double ir = jsonObject.getDouble("interestRate");
        double ab = jsonObject.getDouble("amountBorrowed");
        double rs = jsonObject.getDouble("riskScore");
        double io = jsonObject.getDouble("interestOwed");

        Borrower taker = new Borrower(id, name);

        taker.deposit(balance);
        taker.setInterestRate(ir);
        taker.setAmountBorrowed(ab);
        taker.setRiskScore(rs);
        taker.setInterestOwed(io);

        rlender.addBorrower(taker);
    }
}
