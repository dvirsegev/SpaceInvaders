import biuoop.DrawSurface;

/**
 * public interface Animation.
 */
public interface Animation {
    /**
     * @param d  doOneFrame(DrawSurface) is in charge of the logic.
     * @param dt it specifies the amount of seconds passed since the last call
     */
    void doOneFrame(DrawSurface d, double dt);

    /**
     * @return true if need to continue, false if stop.
     */
    boolean shouldStop();
}
