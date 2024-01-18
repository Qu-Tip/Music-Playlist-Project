package persistence;

import model.Song;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkSong(String title, String artist, int year, int duration, String genre, Song song) {
        assertEquals(title, song.getTitle());
        assertEquals(artist, song.getArtist());
        assertEquals(year, song.getYear());
        assertEquals(duration, song.getDuration());
        assertEquals(genre, song.getGenre());
    }
}
