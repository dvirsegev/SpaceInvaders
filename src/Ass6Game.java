
/**
 * @author dvir segev
 */

import biuoop.GUI;

import java.io.IOException;
import java.io.File;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.BufferedReader;
import java.util.List;


/**
 * Class ASS3Game.
 */

public class Ass6Game {

    /**
     * @param args the input the user gave us.
     * @throws IOException if there is problem.
     */
    public static void main(String[] args) throws IOException {
        HighScoresTable highScoresTable = new HighScoresTable(5);
        // read the file table.
        File file = new File("table.txt");
        // if exist, load it.
        if (file.exists()) {
            highScoresTable = HighScoresTable.loadFromFile(file);
        }
        biuoop.GUI gui = new GUI("Arknoid", 800, 600);
        biuoop.KeyboardSensor keyboard = gui.getKeyboardSensor();
        Counter counter = new Counter();
        Counter life = new Counter();
        ///set live
        life.increase(3);
        // set the dialogManger.
        biuoop.DialogManager dialog = gui.getDialogManager();
        // set the gameflow.
        GameFlow gameFlow = new GameFlow(new AnimationRunner(gui, 3), keyboard, counter,
                life, highScoresTable, dialog);
        // set the AnimationRunner.
        AnimationRunner runner = new AnimationRunner(gui, 60);
        // set the menu.
        MenuAnimation<Task<Void>> menu = new MenuAnimation<>("Menu Title", keyboard, runner);
        menu.addSelection("h", "HighScores", new ShowHiScoresTask(runner,
                new HighScoresAnimation(highScoresTable), keyboard));
        List<LevelInformation> list = null;
        menu.addSelection("s", "Game", new StartGame(gameFlow, list));
        menu.addSelection("q", " Quit", new Quit());
        while (true) {
            // run the menu
            runner.run(menu);
            // get which task we need to do after we get the status.
            Task<Void> task = menu.getStatus();
            // run it.
            task.run();
            // lostRestart the menu.
            menu.reset();
        }
    }

    /**
     * @param path the string which level are we.
     * @return the levels from the file.
     * @throws IOException if can't read the file.
     */
    public static List<LevelInformation> readLevels(String path) throws IOException {
        LevelSpecificationReader l = new LevelSpecificationReader();
        List<LevelInformation> list;
        InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(path);
        try (BufferedReader is = new BufferedReader(new InputStreamReader(inputStream))) {
            list = l.fromReader(is);
        }
        return list;
    }
}