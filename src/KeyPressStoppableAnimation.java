import biuoop.DrawSurface;

/**
 * public class KeyPressStoppableAnimation.
 */
public class KeyPressStoppableAnimation implements Animation {

    private String keys;
    private biuoop.KeyboardSensor sensors;
    private Animation animation1;
    private boolean isAlreadyPressed;
    private boolean aBoolean;

    /**
     * @param sensor    KeyboardSensor sensor.
     * @param key       the string that stop the loop.
     * @param animation the animation we run.
     */
    public KeyPressStoppableAnimation(biuoop.KeyboardSensor sensor, String key, Animation animation) {
        this.animation1 = animation;
        this.sensors = sensor;
        this.keys = key;
        this.isAlreadyPressed = true;
        this.aBoolean = false;
    }

    /**
     * @param d  doOneFrame(DrawSurface)     is in charge of the logic.
     * @param dt it specifies the amount of seconds passed since the last call
     */
    public void doOneFrame(DrawSurface d, double dt) {
        if (!sensors.isPressed(this.keys)) {
            animation1.doOneFrame(d, dt);
            isAlreadyPressed = false;
        } else if (sensors.isPressed(this.keys)) {
                if (!isAlreadyPressed) {
                isAlreadyPressed = true;
                aBoolean = true;
            }
        }
    }

    /**
     * @return a boolean.
     */
    public boolean shouldStop() {
        return aBoolean;
    }
}
