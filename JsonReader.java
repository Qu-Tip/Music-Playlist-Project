package persistence;

import model.Playlist;
import model.Song;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads playlist from JSON data stored in file
// *** CITATION *** -> Most of this class is based on the JsonSerializationDemo
public class JsonReader {

    private String source;

    // represents a reader that reads playlist from JSON data stored in file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads playlist from file and returns it;
    //          throws IOException if an error occurs reading data from file
    public Playlist read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePlaylist(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder content = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> content.append(s));
        }

        return content.toString();
    }

    // EFFECTS: parses playlist from JSON Object and returns it
    private Playlist parsePlaylist(JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        String description = jsonObject.getString("description");
        String creatorName = jsonObject.getString("creatorName");
        Playlist p = new Playlist(title, description, creatorName);
        addSongs(p, jsonObject);
        return p;

    }

    // MODIFIES: p
    // EFFECTS: parses songs from JSON object and adds them to playlist
    private void addSongs(Playlist p, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("songs");
        for (Object json : jsonArray) {
            JSONObject nextSong = (JSONObject) json;
            addSong(p, nextSong);
        }
    }

    // MODIFIES: p
    // EFFECTS: parses song from JSON object and adds it to playlist
    private void addSong(Playlist p, JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        String artist = jsonObject.getString("artist");
        int year = jsonObject.getInt("year");
        int duration = jsonObject.getInt("duration");
        String genre = jsonObject.getString("genre");

        Song song = new Song(title, artist, year, duration, genre);
        p.addSong(song);
    }

}
