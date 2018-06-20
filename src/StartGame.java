import java.util.List;

/**
 * class of StartGame that implements Task<Void> .
 */
public class StartGame implements Task<Void> {
    private GameFlow gameFlow;
    private List<LevelInformation> levelInformation;

    /**
     * @param gameFlow1        the game
     * @param levelInformation the levels
     */
    public StartGame(GameFlow gameFlow1, List<LevelInformation> levelInformation) {
        this.gameFlow = gameFlow1;
        this.levelInformation = levelInformation;
    }

    /**
     * @return null
     */
    public Void run() {
        // run the levels.
        gameFlow.runLevels(levelInformation);
        return null;
    }
}