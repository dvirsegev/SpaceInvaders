import biuoop.GUI;

/**
 * public class AnimationRunner.
 */
public class AnimationRunner {
    private biuoop.GUI gui;
    private int framesPerSecond;
    private biuoop.Sleeper sleeper;

    /**
     * @param gui             the design of BUI.
     * @param framesPerSecond framesPerSecond.
     */
    public AnimationRunner(biuoop.GUI gui, int framesPerSecond) {
        this.gui = gui;
        this.framesPerSecond = framesPerSecond;
        sleeper = new biuoop.Sleeper();
    }

    /**
     * @return the Gui.
     */
    public GUI getGui() {
        return gui;
    }

    /**
     * @param animation the Animation.
     */
    public void run(Animation animation) {
        int millisecondsPerFrame = 1000 / framesPerSecond;
        // while shouldStop is return false.
        while (!animation.shouldStop()) {
            // timing
            long startTime = System.currentTimeMillis();
            biuoop.DrawSurface d = gui.getDrawSurface();
            // call the function doOneFrame in the GameLevel
            animation.doOneFrame(d, (double) millisecondsPerFrame / 1000);
            if (!animation.shouldStop()) {
                gui.show(d);
            }
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
}
