// The CountdownAnimation will display the given gameScreen,
// for numOfSeconds seconds, and on top of them it will show
// a countdown from countFrom back to 1, where each number will
// appear on the screen for (numOfSeconds / countFrom) secods, before
// it is replaced with the next one.

/**
 * public class CountdownAnimation that implements Animation.
 */
public class CountdownAnimation implements Animation {
    private int countFrom;
    private boolean stop;
    private long count;
    private SpriteCollection gameScreen;
    private biuoop.Sleeper sleeper;

    /**
     * @param numOfSeconds the second.
     * @param countFrom    how many second count
     * @param gameScreen   the gamescreen.
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.countFrom = countFrom;
        this.gameScreen = gameScreen;
        this.stop = false;
        this.sleeper = new biuoop.Sleeper();
        count = (long) numOfSeconds * 1000 / countFrom;
    }

    /**
     * @param d  doOneFrame(DrawSurface) is in charge of the logic.
     * @param dt the frame.
     */
    public void doOneFrame(biuoop.DrawSurface d, double dt) {
        gameScreen.drawAllOn(d);
        d.drawText(350, d.getHeight() / 2, " Let's go in:   " + countFrom, 32);
        sleeper.sleepFor(count);
        countFrom--;
        if (countFrom < 0) {
            stop = true;
        }
    }

    /**
     * @return stop.
     */
    public boolean shouldStop() {
        return stop;
    }
}