/**
 * public calss ShieldRemover that implement the hitlistener interface.
 */
public class ShieldRemover implements HitListener {
    private GameLevel gameLevel;
    private Counter counter;

    /**
     * @param level    the game.
     * @param counter1 the sum of all blocks.
     */
    public ShieldRemover(GameLevel level, Counter counter1) {
        this.gameLevel = level;
        this.counter = counter1;
    }

    /**
     * @param shield     the shieldHit.
     * @param alienShoot the alienShoot.
     */
    public void hitEvent(Block shield, Ball alienShoot) {

        // if the life of the block iz zero, remove it.
        if (shield.getHitPoints() == 0) {
            // remove listener.
            shield.removeHitListener(this);
            // remove from the gameLevel
            shield.removeFromGame(gameLevel);
            // remove 1 block of how much remainingblocks there is.
            counter.decrease(1);
        }
    }
}
