/**
 * interface HitListerner.
 */
public interface HitListener {

    /**
     * @param beingHit the block that begin Hit.
     * @param hitter   the ball hitter.
     */
    void hitEvent(Block beingHit, Ball hitter);


}