package sk.tuke.gamestudio.client.minesweeper;

import java.io.*;

public class Settings implements Serializable {
    private static final String SETTING_FILE = System.getProperty("user.home") +
            System.getProperty("file.separator") + "minesweeper.settings";

    private final int rowCount;
    private final int columnCount;
    private final int mineCount;

    public static final Settings TESTING = new Settings (1, 1, 0);
    public static final Settings BEGINNER = new Settings(9, 9, 10);
    public static final Settings INTERMEDIATE = new Settings(16, 16, 40);
    public static final Settings EXPERT = new Settings(16, 30, 99);

    private Settings(int rowCount, int columnCount, int mineCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.mineCount = mineCount;
    }
    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public int getMineCount() {
        return mineCount;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Settings) {
            Settings settings = (Settings) obj;
            return rowCount == settings.getRowCount() && columnCount == settings.getColumnCount()
                    && mineCount == settings.getMineCount();
        }
        return false;
    }


    @Override
    public int hashCode() {
        return rowCount * columnCount * mineCount;
    }

    public void save() {
        try (FileOutputStream os = new FileOutputStream(SETTING_FILE);
             ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(this);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static Settings load() {
        try (FileInputStream is = new FileInputStream(SETTING_FILE);
             ObjectInputStream ois = new ObjectInputStream(is)) {
             return (Settings) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Setting difficulty to 'beginner'.");
        }
        return TESTING;
    }

}
