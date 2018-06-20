/**
 * public class ShowHiScoresTask.
 */
public class ShowHiScoresTask implements Task<Void> {
    private AnimationRunner runner;
    private Animation animation;
    private biuoop.KeyboardSensor keyboard;

    /**
     * @param runner     the animationrunner.
     * @param animation1 the animation
     * @param sensor     the keyboard.
     */
    public ShowHiScoresTask(AnimationRunner runner, Animation animation1, biuoop.KeyboardSensor sensor) {
        this.runner = runner;
        this.animation = animation1;
        this.keyboard = sensor;
    }

    /**
     * @return null.
     */
    public Void run() {
        // get the type of KeyPressStoppableAnimation.
        KeyPressStoppableAnimation keyPress = new KeyPressStoppableAnimation(this.keyboard, "space", animation);
        // run it.
        this.runner.run(keyPress);
        return null;
    }
}