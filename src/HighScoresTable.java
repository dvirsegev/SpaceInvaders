
import java.io.File;
import java.io.Serializable;
import java.io.IOException;
import java.util.ArrayList;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.util.Comparator;
import java.util.List;

/**
 * class HighScoresTable.
 */
class HighScoresTable implements Serializable, Comparator<ScoreInfo> {
    private int sizeTable;
    private List<ScoreInfo> scoreInfoList;

    /**
     * @param size the table holds up to size top scores.
     */
    public HighScoresTable(int size) {
        this.sizeTable = size;
        this.scoreInfoList = new ArrayList<>(size);
    }

    /**
     * @param score the score of the player.
     * @return true if he's can go to the records.
     */
    public boolean bigEnough(int score) {
        if (scoreInfoList.size() < this.sizeTable) {
            return true;
        } else {
/*
            this.scoreInfoList = this.getHighScores();
            return (score > this.scoreInfoList.get(sizeTable - 1).getScore());
            */
            int num = getRank(score);
            return (num - 1 != size());

        }
    }

    /**
     * @param score the scoreInfo
     */
    public void add(ScoreInfo score) {
        this.scoreInfoList = this.getHighScores();
        // check if the score can get into the list of records.
        if (bigEnough(score.getScore())) {
            this.scoreInfoList.add(score);
        }
        // if the list is bigger then the sizeTable, remove the lowest result.
        if (this.scoreInfoList.size() > this.sizeTable) {
            this.scoreInfoList = this.getHighScores();
            this.scoreInfoList.remove(sizeTable);
        }

    }

    /**
     * @return the size.
     */
    public int size() {
        return this.sizeTable;
    }

    /**
     * @return The list is sorted such that the highest
     */
    public List<ScoreInfo> getHighScores() {
        // bubble sort.
        for (int i = 0; i < scoreInfoList.size(); i++) {
            for (int j = 0; j < scoreInfoList.size() - i - 1; j++) {
                if (compare(scoreInfoList.get(j), scoreInfoList.get(j + 1)) == 0) {
                    int score = scoreInfoList.get(j).getScore();
                    String name = scoreInfoList.get(j).getName();
                    scoreInfoList.get(j).setScore(scoreInfoList.get(j + 1).getScore());
                    scoreInfoList.get(j).setName(scoreInfoList.get(j + 1).getName());
                    scoreInfoList.get(j + 1).setName(name);
                    scoreInfoList.get(j + 1).setScore(score);
                }
            }
        }

        return scoreInfoList;
    }


    /**
     * @param score the score
     * @return the rank of the current score: where will it
     * be on the list if added?
     */
    public int getRank(int score) {
        this.scoreInfoList = this.getHighScores();
        for (int i = 0; i < scoreInfoList.size(); i++) {
            if (score > scoreInfoList.get(i).getScore()) {
                return i + 1;
            }
        }
        return this.sizeTable + 1;
    }

    /**
     * Clears the table.
     */
    public void clear() {
        this.scoreInfoList.clear();
    }


    /**
     * @param filename the file.
     * @throws IOException if can't load.
     */
    public void load(File filename) throws IOException {
        this.clear();
        try (ObjectInputStream load = new ObjectInputStream(new FileInputStream(filename))) {
            HighScoresTable temp = (HighScoresTable) load.readObject();
            this.scoreInfoList = temp.scoreInfoList;
            this.sizeTable = temp.sizeTable;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Failed to load the file");
        }
    }

    /**
     * @param filename Save table data to the specified file.
     * @throws IOException if can't save.
     */
    public void save(File filename) throws IOException {
        ObjectOutputStream savetable = null;
        try {
            savetable = new ObjectOutputStream(new FileOutputStream(filename));
            savetable.writeObject(this);
        } catch (IOException e) {
            System.out.println("Failed to save");
        } finally {
            if (savetable != null) {
                savetable.close();
            }
        }

    }


    /**
     * @param filename the file.
     * @return read a table from file and return it. If the file does not exist, or there is a problem with
     * reading it, an empty table is returned.
     */
    public static HighScoresTable loadFromFile(File filename) {
        HighScoresTable temp = new HighScoresTable(0);
        try {
            temp.load(filename);
            return temp;
        } catch (IOException e) {
            System.out.println("Error");
        }
        return new HighScoresTable(0);
    }

    /**
     * @param firstScore  type of ScoreInfo.
     * @param secondScore type of ScoreInfo.
     * @return which one if the first is bigger, else 0.
     */
    public int compare(ScoreInfo firstScore, ScoreInfo secondScore) {
        if (firstScore.getScore() >= secondScore.getScore()) {
            return 1;
        }
        return 0;
    }
}