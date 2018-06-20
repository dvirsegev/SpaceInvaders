/**
 * public interface BlockCreator.
 */
public interface BlockCreator {
    /**
     * @param xpos startX of the block.
     * @param ypos startY of the block.
     * @return new Block.
     */
    AlineBlock create(int xpos, int ypos);

    /**
     * @return getWidth.
     */
    int getWidth();

    /**
     * @return get Height.
     */
    int getHeight();

}
