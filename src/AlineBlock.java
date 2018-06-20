import java.awt.Color;

/**
 * class of AlineBlock that extends Block.
 */
public class AlineBlock extends Block {
    private double speed = 30;

    /**
     * @param blockTangle shape rectangle,
     * @param color       color
     * @param storke      the stroke.
     */
    public AlineBlock(Rectangle blockTangle, Color color, Color storke) {
        super(blockTangle, color, storke);
    }

    /**
     * @return speed.
     */
    public double getSpeed() {
        return speed;
    }


}
