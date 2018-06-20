/**
 * public calss BallRemover implements HitListener.
 */
public class BallRemover implements HitListener {
    private GameLevel gameLevel;
    private Counter counter;

    /**
     * @param gameLevel the gameLevel.
     * @param counter how many balls there is.
     */
    public BallRemover(GameLevel gameLevel, Counter counter) {
        this.counter = counter;
        this.gameLevel = gameLevel;
    }

    /**
     * @param beingHit the block that begin Hit.
     * @param hitter   the ball hitter.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        // remove the ball from the gameLevel.
        hitter.removeFromGame(gameLevel);
        // decrease by 1 the sum of the balls.
        this.counter.decrease(1);
    }
}
