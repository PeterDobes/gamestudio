package sk.tuke.gamestudio.client.games.minesweeper.tests;

import sk.tuke.gamestudio.client.games.minesweeper.Settings;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class SettingsTest {
    Settings settings;
    Path path = Paths.get(System.getProperty("user.home") +
            System.getProperty("file.separator") + "minesweeper.settings");

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getRowColMineCount() {
        settings = Settings.BEGINNER;
        assertEquals(9, settings.getRowCount());
        assertEquals(9, settings.getColumnCount());
        assertEquals(10, settings.getMineCount());
        settings = Settings.INTERMEDIATE;
        assertEquals(16, settings.getRowCount());
        assertEquals(16, settings.getColumnCount());
        assertEquals(40, settings.getMineCount());
        settings = Settings.EXPERT;
        assertEquals(16, settings.getRowCount());
        assertEquals(30, settings.getColumnCount());
        assertEquals(99, settings.getMineCount());

    }

    @Test
    public void saveLoad() {
        delet(path);
        assertFalse(Files.exists(path));

        settings = Settings.EXPERT;
        assertNotEquals(Settings.BEGINNER, settings);
        assertEquals(Settings.EXPERT, settings);
        assertEquals(99, settings.getMineCount());

        settings.save();
        assertTrue(Files.exists(path));

        settings = Settings.INTERMEDIATE;
        assertNotEquals(Settings.EXPERT, settings);
        assertEquals(Settings.INTERMEDIATE, settings);
        assertEquals(40, settings.getMineCount());

        settings = Settings.load();
        assertNotEquals(Settings.INTERMEDIATE, settings);
        assertEquals(Settings.EXPERT, settings);
        assertEquals(99, settings.getMineCount());

        delet(path);
        assertFalse(Files.exists(path));
        settings = Settings.load();
        assertEquals(Settings.BEGINNER, settings);
        assertEquals(10, settings.getMineCount());
    }


    private void delet(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}