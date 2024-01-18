package persistence;

import org.json.JSONObject;

// *** CITATION *** -> Most of this interface is based on the JsonSerializationDemo
public interface Writable {

    // EFFECTS: returns this as a JSON object
    JSONObject toJson();
}
