package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a song class with a title, artist, year of release, duration (in seconds), and genre
public class Song implements Writable {

    private String title;
    private String artist;
    private int year;
    private int duration;
    private String genre;

    // EFFECTS: title, artist, year, duration, and genre are all set to given parameters
    public Song(String title, String artist, int year, int duration, String genre) {
        this.title = title;
        this.artist = artist;
        this.year = year;
        this.duration = duration;
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public int getYear() {
        return year;
    }

    public int getDuration() {
        return duration;
    }

    public String getGenre() {
        return genre;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    // EFFECTS: prints a visual representation of the information of a song
    @Override
    public String toString() {
        return title + " by " + artist + " (" + year + ")    " + Integer.toString(duration)
                + "secs" + "     Genre: " + genre;
    }

    // *** CITATION *** -> This method is based on the JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("artist", artist);
        json.put("year", year);
        json.put("duration", duration);
        json.put("genre", genre);
        return json;
    }
}
