import java.io.Serializable;

/**
 * public class ScoreInfo.
 */
public class ScoreInfo implements Serializable {
    private String name;
    private int score;

    /**
     * @param name  the name
     * @param score the score.
     */
    public ScoreInfo(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * @return the name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param name1 set the name.
     */
    public void setName(String name1) {
        this.name = name1;
    }

    /**
     * @return the score.
     */
    public int getScore() {
        return this.score;
    }

    /**
     * @param score1 set the score.
     */
    public void setScore(int score1) {
        this.score = score1;
    }
}