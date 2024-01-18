package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PlaylistTest {

    Playlist pTest;
    Song s1;
    Song s2;
    Song s3;
    Song s4;

    @BeforeEach
    void runBefore() {
        pTest = new Playlist("test1", "description1", "name1");
        s1 = new Song("Rewrite the Stars", "Zac Efron, Zendaya", 2022, 180, "pop");
        s2 = new Song("Safe With Me", "Gryffin, Audrey Mika", 2023, 185, "hiphop");
        s3 = new Song("Top of the World", "Shawn Mendes", 2021, 190, "pop");
        s4 = new Song("Safe With Me", "Taylor Swift", 2019, 185, "hiphop");

    }

    @Test
    void testConstructor() {
        assertEquals("test1", pTest.getTitle());
        assertEquals("description1", pTest.getDescription());
        assertEquals("name1", pTest.getCreatorName());
        assertEquals(0, pTest.getSongs().size());
        assertEquals(0, pTest.getTotalDuration());
        assertEquals(String.valueOf(LocalDate.now()), pTest.getDateCreation());

    }

    @Test
    void testViewPlaylistInfo() {
        String result = "Title: test1     Created by: name1     Description: description1" +
                "     Total Duration: 0 seconds     Date of Creation: " + String.valueOf(LocalDate.now());
        assertEquals(result, pTest.toString());
    }

    @Test
    void testView1Song() {
        pTest.addSong(s1);
        String result = "";
        result = result + "\n" + s1.toString();
        assertEquals(result, pTest.viewSongs());
    }

    @Test
    void testView2Songs() {
        pTest.addSong(s1);
        pTest.addSong(s2);

        String result = "";
        result = result + "\n" + s1.toString() + "\n" + s2.toString();
        assertEquals(result, pTest.viewSongs());
    }

    @Test
    void testViewEmptyPlaylist() {
        String result = ("Title: test1     Created by: name1     Description: description1     " +
                "Total Duration: 0 seconds" + "     Date of Creation: " + String.valueOf(LocalDate.now()));
        assertEquals(result, pTest.viewPlaylist());
    }

    @Test
    void testViewPlaylist() {
        pTest.addSong(s1);
        pTest.addSong(s2);

        String result = pTest.toString() + pTest.viewSongs();
        assertEquals(result, pTest.viewPlaylist());
    }

    @Test
    void testAdd1Song() {
        pTest.addSong(s1);
        assertEquals(s1, pTest.getSongs().get(0));
        assertEquals(1, pTest.getSongs().size());
    }

    @Test
    void testAdd2Songs() {
        pTest.addSong(s1);
        pTest.addSong(s2);
        assertEquals(s1, pTest.getSongs().get(0));
        assertEquals(s2, pTest.getSongs().get(1));
        assertEquals(2, pTest.getSongs().size());
    }

    @Test
    void testRemove1Song() {
        pTest.addSong(s1);
        pTest.removeSong(s1);
        assertEquals(0, pTest.getSongs().size());
    }

    @Test
    void testRemove2Songs() {
        pTest.addSong(s1);
        pTest.addSong(s3);
        pTest.addSong(s2);

        pTest.removeSong(s1);
        pTest.removeSong(s3);
        assertEquals(s2, pTest.getSongs().get(0));
        assertEquals(1, pTest.getSongs().size());
    }

    @Test
    void testPlaylistLength() {
        String result = "There are 0 songs in your playlist!";
        assertEquals(result, pTest.playlistLength());

        pTest.addSong(s1);
        result = "There is 1 song in your playlist!";
        assertEquals(result, pTest.playlistLength());

        pTest.addSong(s2);
        pTest.addSong(s3);
        result = "There are 3 songs in your playlist!";
        assertEquals(result, pTest.playlistLength());
    }

    @Test
    void testFindSong() {

        assertNull(pTest.findSong("hi"));

        pTest.addSong(s1);
        assertNull(pTest.findSong("random name"));
        assertEquals(s1, pTest.findSong("Rewrite the Stars"));

        pTest.addSong(s2);
        pTest.addSong(s3);
        assertEquals(s3, pTest.findSong("Top of the World"));
        assertEquals(s2, pTest.findSong("Safe With Me"));
        assertNull(pTest.findSong("????"));

    }


    // code coverage requires tests for setters:
    @Test
    void testSetTitle() {
        pTest.setTitle("new title");
        assertEquals("new title", pTest.getTitle());
    }

    @Test
    void testSetDescription() {
        pTest.setDescription("new description");
        assertEquals("new description", pTest.getDescription());
    }

    @Test
    void testSetCreatorName() {
        pTest.setCreatorName("new creator");
        assertEquals("new creator", pTest.getCreatorName());
    }

    @Test
    void testUpdateDuration() {
        pTest.updateDuration(180);
        assertEquals(180, pTest.getTotalDuration());
        pTest.updateDuration(-10);
        assertEquals(170, pTest.getTotalDuration());
    }


}
