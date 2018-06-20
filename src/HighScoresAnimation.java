import java.awt.Color;
import java.util.List;

/**
 * public class HighScoresAnimation.
 */
public class HighScoresAnimation implements Animation {
    private HighScoresTable highScoresTable;

    /**
     * @param scoresTable the table.
     */
    public HighScoresAnimation(HighScoresTable scoresTable) {
        this.highScoresTable = scoresTable;
    }

    /**
     * @param d  doOneFrame(DrawSurface) is in charge of the logic.
     * @param dt it specifies the amount of seconds passed since the last call.
     */
    public void doOneFrame(biuoop.DrawSurface d, double dt) {
        d.setColor(Color.LIGHT_GRAY);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        d.setColor(Color.RED);
        List<ScoreInfo> scoreInfoList = this.highScoresTable.getHighScores();
        ScoreInfo scoreInfo;
        int nameX = 100;
        int y = 100;
        int scoreX = 450;
        d.drawText(nameX, y, "Name", 32);
        d.drawText(scoreX, y, "Score", 32);
        d.drawText(40, 50, "High Score", 40);
        for (int i = 0; i < scoreInfoList.size(); ++i) {
            y += 30;
            scoreInfo = scoreInfoList.get(i);
            d.setColor(Color.BLACK);
            d.drawText(nameX, y, scoreInfo.getName(), 26);
            d.drawText(scoreX, y, Integer.toString(scoreInfo.getScore()), 26);
        }

        String msg = "Press space to continue";
        d.setColor(Color.BLACK);
        d.drawText(199, 500, msg, 32);
        d.setColor(Color.decode("#1788d0"));
        d.drawText(200, 501, msg, 32);
        d.setColor(Color.BLACK);
        d.drawText(202, 502, msg, 32);
    }

    /**
     * @return false;
     */
    public boolean shouldStop() {
        return false;
    }
}
