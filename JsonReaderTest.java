package persistence;

import model.Playlist;
import model.Song;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Playlist p = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // exception should be thrown :)
        }
    }

    @Test
    void testReaderEmptyPlaylist() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyPlaylist.json");
        try {
            Playlist p = reader.read();
            assertEquals("playlist name", p.getTitle());
            assertEquals(0, p.getSongs().size());
            assertEquals("playlist description", p.getDescription());
            assertEquals(0, p.getTotalDuration());
            assertEquals("creator name", p.getCreatorName());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralPlaylist() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralPlaylist.json");
        try {
            Playlist p = reader.read();
            assertEquals("playlist name", p.getTitle());
            List<Song> songs = p.getSongs();
            assertEquals(2, songs.size());
            checkSong("Kiss the Rain", "Yiruma", 2009, 202, "calming?", songs.get(0));
            checkSong("Die for You", "The Weekend", 2018, 205, "pop", songs.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
