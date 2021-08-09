package persistence;

import org.json.JSONObject;

// Writable interface that enforces a toJson function to all Classes that must be saved
public interface Writable {

    JSONObject toJson();

}
