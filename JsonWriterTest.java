package persistence;

import model.Playlist;

import model.Song;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Playlist p = new Playlist("playlist name", "playlist description",
                    "creator name");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {

        }
    }

    @Test
    void testWriterEmptyPlaylist() {
        try {
            Playlist p = new Playlist("playlist name", "playlist description",
                    "creator name");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyPlaylist.json");
            writer.open();
            writer.write(p);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyPlaylist.json");
            p = reader.read();
            assertEquals("playlist name", p.getTitle());
            assertEquals(0, p.getSongs().size());
            assertEquals("playlist description", p.getDescription());
            assertEquals("creator name", p.getCreatorName());
            assertEquals(0, p.getTotalDuration());
            assertEquals(String.valueOf(LocalDate.now()), p.getDateCreation());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralPlaylist() {
        try {
            Playlist p = new Playlist("playlist name", "playlist description",
                    "creator name");
            Song s1 = new Song("Kiss the Rain", "Yiruma", 2009, 202, "calming?");
            Song s2 = new Song("Die for You", "The Weekend", 2018, 205, "pop");
            p.addSong(s1);
            p.addSong(s2);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralPlaylist.json");
            writer.open();
            writer.write(p);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralPlaylist.json");
            p = reader.read();
            assertEquals("playlist name", p.getTitle());
            List<Song> songs = p.getSongs();
            assertEquals(2, songs.size());
            checkSong("Kiss the Rain", "Yiruma", 2009, 202, "calming?", songs.get(0));
            checkSong("Die for You", "The Weekend", 2018, 205, "pop", songs.get(1));

            assertEquals("playlist description", p.getDescription());
            assertEquals("creator name", p.getCreatorName());
            assertEquals(407, p.getTotalDuration());
            assertEquals(String.valueOf(LocalDate.now()), p.getDateCreation());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
