package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.time.LocalDate;
import java.util.ArrayList;

// Represents a playlist with a title, songs (list), description, name of creator,
//                              total duration (in seconds), date of creation
// Dates are Strings in the form MM/DD/YYYY; for ex: 02/11/2023
public class Playlist implements Writable {

    private String title;
    private ArrayList<Song> songs;
    private String description;
    private String creatorName;
    private int totalDuration;
    private final String dateCreation;

    private EventLog eventLog;

    // EFFECTS: title, description, and creatorName are set to given parameters
    //          songs is an empty arrayList, totalDuration set to 0, dateCreation is "today's" date
    public Playlist(String title, String description, String creatorName) {
        this.title = title;
        this.description = description;
        this.creatorName = creatorName;
        songs = new ArrayList<>();
        totalDuration = 0;
        dateCreation = String.valueOf(LocalDate.now());

        eventLog = EventLog.getInstance();
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public String getDescription() {
        return description;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public int getTotalDuration() {
        return totalDuration;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    // MODIFIES: this
    // EFFECTS: updates total duration when song is added or removed. duration can be positive or negative.
    public void updateDuration(int duration) {
        totalDuration += duration;
    }

    // EFFECTS: returns representation of all playlist info in String format
    @Override
    public String toString() {
        return ("Title: " + title + "     Created by: " + creatorName + "     Description: "
                + description + "     Total Duration: " + Integer.toString(totalDuration)
                + " seconds     Date of Creation: " + dateCreation);
    }

    // EFFECTS: returns representation of song info for every song on playlist
    public String viewSongs() {
        String allSongs = "";
        for (Song s : songs) {
            allSongs = allSongs + "\n" + s.toString();
        }

        return allSongs;
    }

    // EFFECTS: returns representation of playlist + songs info (User Story 1)
    public String viewPlaylist() {
        return toString() + viewSongs();
    }

    // MODIFIES: this
    // EFFECTS: adds song object in songs playlist (User Story 2) and updates duration
    public void addSong(Song s) {
        songs.add(s);
        updateDuration(s.getDuration());
        eventLog.logEvent(new Event("A song has been added to the playlist!"));
    }

    // MODIFIES: this
    // EFFECTS: removes the given song object from songs playlist (User Story 3) and updates duration
    public void removeSong(Song s) {
        songs.remove(s);
        updateDuration(s.getDuration() * -1);
        eventLog.logEvent(new Event("A song has been removed from the playlist!"));
    }

    // EFFECTS: prints out a statement showing the number of songs in the playlist (User Story 4)
    public String playlistLength() {
        if (songs.size() == 1) {
            eventLog.logEvent(new Event("Playlist length has been checked!"));
            return "There is 1 song in your playlist!";
        } else {
            eventLog.logEvent(new Event("Playlist length has been checked!"));
            return "There are " + songs.size() + " songs in your playlist!";
        }
    }

    // EFFECTS: searches for song in playlist with title "title"
    // returns song if found, null if not found
    public Song findSong(String title) {
        for (Song s : songs) {
            if (s.getTitle().equals(title)) {
                return s;
            }
        }
        return null;
    }

    // *** CITATION *** -> This method is based on the JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("songs", songsToJson());
        json.put("description", description);
        json.put("creatorName", creatorName);
        return json;
    }

    // *** CITATION *** -> This method is based on the JsonSerializationDemo
    // EFFECTS: returns songs in playlist as JSONArray
    private JSONArray songsToJson() {

        JSONArray jsonArray = new JSONArray();

        for (Song s : songs) {
            jsonArray.put(s.toJson());
        }

        return jsonArray;
    }

}
