
/**
 * class PaddleRemover that implement HitListener interface.
 */
public class PaddleRemover implements HitListener {

    // members.
    private GameLevel gameLevel;
    private Counter counter;

    /**
     * @param counter   the count live of the paddle.
     * @param gameLevel the GameLevel.
     */
    public PaddleRemover(GameLevel gameLevel, Counter counter) {
        this.gameLevel = gameLevel;
        this.counter = counter;
    }

    /**
     * @param beingHit the block that being hit
     * @param hitter   the ball the hit the block.
     */
    @SuppressWarnings("all")
    public void hitEvent(Block beingHit, Ball hitter) {
        // remove listener.
        beingHit.removeHitListener(this);
        // remove from the gameLevel
        beingHit.removeFromGame(gameLevel);
        counter.decrease(1);
    }
}
