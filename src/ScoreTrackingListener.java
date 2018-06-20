/**
 * public class ScoreTrackingListener.
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    /**
     * @param scoreCounter the score.
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * @param beingHit the block that begin Hit.
     * @param hitter   the ball hitter.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getHitPoints() == 0) {
            currentScore.increase(100);
            // remove lisener.
            beingHit.removeHitListener(this);
        }
    }
}