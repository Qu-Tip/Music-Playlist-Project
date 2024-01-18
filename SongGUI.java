package ui;

import model.Song;

import javax.swing.*;

// Represents the gui for an individual song
public class SongGUI extends JPanel {

    private Song s1;
    private JLabel title;
    private JLabel artist;
    private JLabel year;
    private JLabel duration;
    private JLabel genre;

    // EFFECTS: constructs a new SongGUI, with the same fields as its corresponding song
    public SongGUI(Song s1) {
        this.s1 = s1;
        title = new JLabel(s1.getTitle());
        artist = new JLabel(s1.getArtist());
        year = new JLabel(Integer.toString(s1.getYear()));
        duration = new JLabel(Integer.toString(s1.getDuration()));
        genre = new JLabel(s1.getGenre());

        // add labels to the container (JPanel) so that they can be displayed
        add(title);
        add(artist);
        add(year);
        add(duration);
        add(genre);

    }

    // EFFECTS: returns the Song object correlated to the songGUI object (this)
    public Song getSong() {
        return s1;
    }

    // MODIFIES: this
    // EFFECTS: sets the SongGUI title and artist to the Song's title and artist
    public void setGUI() {
        title.setText(s1.getTitle());
        artist.setText(s1.getArtist());
    }
}
