package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SongTest {

    Song sTest;

    @BeforeEach
    void runBefore() {
        sTest = new Song("Someone You Loved", "Lewis Capaldi", 2021, 198, "pop");
    }

    @Test
    void testConstructor() {
        assertEquals("Someone You Loved", sTest.getTitle());
        assertEquals("Lewis Capaldi", sTest.getArtist());
        assertEquals(2021, sTest.getYear());
        assertEquals(198, sTest.getDuration());
        assertEquals("pop", sTest.getGenre());

    }

    @Test
    void testViewSong() {
        String result = "Someone You Loved by Lewis Capaldi (2021)    198secs     Genre: pop";
        assertEquals(result, sTest.toString());
    }

    // code coverage requires tests for setters:
    @Test
    void testSetTitle() {
        sTest.setTitle("new title");
        assertEquals("new title", sTest.getTitle());
    }

    @Test
    void testSetArtist() {
        sTest.setArtist("new artist");
        assertEquals("new artist", sTest.getArtist());
    }

    @Test
    void testSetYear() {
        sTest.setYear(2004);
        assertEquals(2004, sTest.getYear());
    }

    @Test
    void testSetDuration() {
        sTest.setDuration(12345);
        assertEquals(12345, sTest.getDuration());
    }

    @Test
    void testSetGenre() {
        sTest.setGenre("new genre");
        assertEquals("new genre", sTest.getGenre());
    }

}
