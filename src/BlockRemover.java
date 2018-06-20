import java.util.List;

/**
 * class BlockRemover that implement HitListener interface.
 */
public class BlockRemover implements HitListener {
    // members.
    private GameLevel gameLevel;
    private Counter remainingBlocks;
    private List<AlineBlock> list;

    /**
     * Constractor.
     *
     * @param gameLevel     the GameLevel.
     * @param removedBlocks Coutner type.
     * @param list          the list of the alien.
     */
    public BlockRemover(GameLevel gameLevel, Counter removedBlocks, List<AlineBlock> list) {
        this.gameLevel = gameLevel;
        this.remainingBlocks = removedBlocks;
        this.list = list;
    }

    /**
     * @param beingHit the block that being hit
     * @param hitter   the ball the hit the block.
     */
    @SuppressWarnings("all")
    public void hitEvent(Block beingHit, Ball hitter) {

        // if the life of the block iz zero, remove it.
        if (beingHit.getHitPoints() == 0) {
            // remove listener.
            beingHit.removeHitListener(this);
            // remove from the gameLevel
            beingHit.removeFromGame(gameLevel);
            list.remove(beingHit);
            // remove 1 block of how much remainingblocks there is.
            remainingBlocks.decrease(1);
        }
    }
}