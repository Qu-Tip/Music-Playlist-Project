package ui;

import model.Event;
import model.EventLog;
import model.Song;
import model.Playlist;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.*;

import java.io.FileNotFoundException;
import java.io.IOException;

// Represents the playlist gui that provides the features of the playlist application
// Image taken from: https://www.pokemon.com/us/pokedex/litleo
public class PlaylistGUI extends JFrame implements WindowListener {

    // DefaultListModel manages a JList, which holds data displayed by a GUI component
    private DefaultListModel<Song> defaultPlaylistModel;
    // displays songs and allows users to select items
    private JList<Song> playlist;
    private JScrollPane scrollPane;
    private Playlist p1;
    private JLabel playlistInfo;

    private JButton editPlaylistInfo;
    private JButton addSong;
    private JButton removeSong;
    private JButton playlistSize;
    private JButton editSong;
    private JButton savePlaylist;
    private JButton loadPlaylist;

    private static final String JSON_STORE = "./data/playlist.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private EventLog eventLog;

    // EFFECTS: constructs a new PlaylistGUI, with a panel + buttons + actionListeners
    public PlaylistGUI() throws FileNotFoundException {
        super("Playlist Application");        // sets title of gui
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);   // exit gui when frame is closed

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        eventLog = EventLog.getInstance();
        addWindowListener(this);
        createPlaylist();

        // Buttons for all the features:
        makePanel();
        addActionListeners1();
        addActionListeners2();

        pack();              // packs/resizes a gui window so that all the components fit
        setVisible(true);    // makes GUI visible
    }

    // EFFECTS: creates a new playlist with default fields, along with other components, such as
    //          defaultPlaylistModel and JList that controls the playlist GUI display
    private void createPlaylist() {
        p1 = new Playlist("---", "---", "---");
        playlistInfo = new JLabel();
        playlistInfo.setText(p1.toString());

        defaultPlaylistModel = new DefaultListModel<>();
        playlist = new JList<>(defaultPlaylistModel);
        scrollPane = new JScrollPane(playlist);
        Dimension dim = new Dimension(10, 20);
        scrollPane.setPreferredSize(dim);
        add(scrollPane, BorderLayout.WEST);

        ImageIcon image = new ImageIcon("./data/Litleo.png");
        JLabel imageLabel = new JLabel(image);
        add(imageLabel, BorderLayout.EAST);

        add(playlistInfo, BorderLayout.SOUTH);
    }

    // EFFECTS: creates a panel with all the necessary components
    private void makePanel() {
        // Create a panel and add all the buttons
        JPanel playlistPanel = new JPanel(new FlowLayout());
        scrollPane.add(playlist);
        playlistPanel.add(playlist);  // add JList to panel

        editPlaylistInfo = new JButton("Edit Playlist");
        addSong = new JButton("Add a Song");
        removeSong = new JButton("Remove a Song");
        playlistSize = new JButton("Check # of songs");
        editSong = new JButton("Edit song");
        savePlaylist = new JButton("Save");
        loadPlaylist = new JButton("Load");

        playlistPanel.add(editPlaylistInfo);
        playlistPanel.add(addSong);
        playlistPanel.add(removeSong);
        playlistPanel.add(playlistSize);
        playlistPanel.add(editSong);
        playlistPanel.add(savePlaylist);
        playlistPanel.add(loadPlaylist);
        add(playlistPanel, BorderLayout.NORTH);
    }

    // EFFECTS: activates actionListeners (when button is clicked)
    private void addActionListeners1() {
        // EFFECTS:
        editPlaylistInfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editPlaylistInfo();
            }
        });

        // EFFECTS:
        addSong.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addSong();
            }
        });

        // EFFECTS:
        removeSong.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeSong();
            }
        });

    }

    // EFFECTS: activates other actionListeners (when button is clicked)
    private void addActionListeners2() {

        // EFFECTS:
        playlistSize.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getPlaylistSize();
            }
        });

        editSong.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editSong();
            }
        });


        savePlaylist.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                savePlaylist();
            }
        });

        loadPlaylist.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadPlaylist();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: allows user to edit the playlist title, description, or creator name.
    private void editPlaylistInfo() {

        String[] choices = {"Edit Playlist Title", "Edit Playlist Description", "Edit Creator Name", "Go Back"};

        // NOTES: JOptionPane.DEFAULT_OPTION is for the types of buttons used
        //        JOptionPane.PLAIN_MESSAGE is for the type of messages used
        //        choices[0] is by default the option selected when the display opens

        int decision = JOptionPane.showOptionDialog(this,
                "Select what you want to edit about your playlist: ", "Edit Playlist Info",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, choices, choices[0]);

        switch (decision) {
            case 0:
                editPlaylistTitle();
                break;
            case 1:
                editPlaylistDescription();
                break;
            case 2:
                editPlaylistName();
                break;
            default:
                break;
        }

        playlistInfo.setText(p1.toString());
    }

    // MODIFIES: this
    // EFFECTS: edits the playlist title according to the user input
    private void editPlaylistTitle() {
        String title = JOptionPane.showInputDialog(this, "Edit Playlist Title", p1.getTitle());
        if (title != null) {
            p1.setTitle(title);
            JOptionPane.showMessageDialog(this, "Your playlist title has been updated!");
        }
    }

    // MODIFIES: this
    // EFFECTS: edits the playlist description according to the user input
    private void editPlaylistDescription() {
        String description = JOptionPane.showInputDialog(this, "Edit Playlist Description",
                p1.getDescription());
        if (description.length() > 120) {
            JOptionPane.showMessageDialog(this, "That description is too long!");
        } else {
            p1.setDescription(description);
            JOptionPane.showMessageDialog(this, "Your playlist description has been updated!");
        }
    }

    // MODIFIES: this
    // EFFECTS: edits the playlist creator name according to the user input
    private void editPlaylistName() {
        String name = JOptionPane.showInputDialog(this,
                "Edit Creator Name", p1.getCreatorName());
        if (name != null) {
            p1.setCreatorName(name);
            JOptionPane.showMessageDialog(this, "Your name has been updated!");
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a song to the playlist
    //          user inputs song information and song is added, provided that it is not already in the playlist
    private void addSong() {
        Song song = inputSong();
        if (p1.getSongs().contains(song)) {
            JOptionPane.showMessageDialog(this, "This song already exists in your playlist!");
        } else {
            p1.addSong(song);
            defaultPlaylistModel.addElement(song);
            playlistInfo.setText(p1.toString());
            JOptionPane.showMessageDialog(this,
                    song.getTitle() + " by " + song.getArtist() + " has been added to your playlist!");
        }
    }

    // MODIFIES: this
    // EFFECTS: removes a song from a playlist
    //          user selects the song they want to remove, index of that selection is saved and used to remove song
    //          from the playlist and the GUI display.
    private void removeSong() {

        int index = playlist.getSelectedIndex();
        if (index != -1) {
            Song song = playlist.getSelectedValue();

            p1.removeSong(song);
            defaultPlaylistModel.removeElement(song);
            playlistInfo.setText(p1.toString());

            JOptionPane.showMessageDialog(this, song.getTitle() + " by "
                    + song.getArtist() + " has been removed from your playlist!");
        } else {
            JOptionPane.showMessageDialog(this,
                    "Please select a song!");
        }
    }

    // EFFECTS: displays a message that shows the number of songs in the playlist
    private void getPlaylistSize() {
        JOptionPane.showMessageDialog(this, p1.playlistLength());
    }

    // MODIFIES: this
    // EFFECTS: edits the song title or artist
    private void editSongChoices(Song song, SongGUI songGUI) {
        String[] choices = {"Edit Title", "Edit Artist"};
        int decision = JOptionPane.showOptionDialog(this,
                "What would you like to change about this song?", "Edit Song Information",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, choices, choices[0]);

        if (decision == 0) {
            String t = JOptionPane.showInputDialog(this,
                    "Edit Song Title:", song.getTitle());
            song.setTitle(t);
            songGUI.setGUI();
            JOptionPane.showMessageDialog(this, "The title has been changed!");
        }

        if (decision == 1) {
            String artist = JOptionPane.showInputDialog(this,
                    "Edit Song Title:", song.getArtist());
            song.setArtist(artist);
            songGUI.setGUI();
            JOptionPane.showMessageDialog(this, "The artist has been changed!");
        }
    }

    // EFFECTS: edits the song's title or artist
    private void editSong() {
        int index = playlist.getSelectedIndex();
        if (index != -1) {
            Song song = playlist.getSelectedValue();
            SongGUI s1 = new SongGUI(song);
            editSongChoices(song, s1);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a song!");
        }
    }

    // EFFECTS: asks the user for song information (fields) and creates + returns a new Song object
    //          NOTE: JOptionPane.showInputDialog displays a box for users to input text
    private Song inputSong() {
        // ask for all necessary information from user
        String title = JOptionPane.showInputDialog(this, "Enter song title: ");
        String artist = JOptionPane.showInputDialog(this, "Enter artist name: ");
        int year = Integer.parseInt(JOptionPane.showInputDialog(this,
                "Enter year of release: "));
        int duration = Integer.parseInt(JOptionPane.showInputDialog(this,
                "Enter duration (in seconds)"));
        String genre = JOptionPane.showInputDialog(this, "Enter genre: ");

        // create/add new song to playlist and DefaultListModel
        return new Song(title, artist, year, duration, genre);
    }

    // EFFECTS: saves the playlist to files
    private void savePlaylist() {
        try {
            jsonWriter.open();
            jsonWriter.write(p1);
            jsonWriter.close();
            JOptionPane.showMessageDialog(this, "Saved " + p1.getTitle() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads the playlist from file
    private void loadPlaylist() {
        try {
            p1 = jsonReader.read();
            defaultPlaylistModel.clear();
            for (Song song : p1.getSongs()) {
                defaultPlaylistModel.addElement(song);
            }
            playlistInfo.setText(p1.toString());
            JOptionPane.showMessageDialog(this,
                    "Loaded " + p1.getTitle() + " from " + JSON_STORE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Unable to read to file: " + JSON_STORE);
        }
    }


    public static void main(String[] args) {
        try {
            new PlaylistGUI();
        } catch (FileNotFoundException e) {
            System.out.println("The file was not found");
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        printLog(EventLog.getInstance());
    }

    public void printLog(EventLog e) {
        for (Event el : e) {
            System.out.println(el.toString());
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
