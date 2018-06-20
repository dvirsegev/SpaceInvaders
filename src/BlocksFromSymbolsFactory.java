
import java.util.Map;
import java.util.TreeMap;

/**
 * public class BlocksFromSymbolsFactory.
 */
public class BlocksFromSymbolsFactory {
    private Map<String, Integer> spacerWidths = new TreeMap<>();
    private Map<String, BlockCreator> blockCreators = new TreeMap<>();

    /**
     * @param spacerWidths1  the map of spaces.
     * @param blockCreators1 the map of string and blocks
     */
    public BlocksFromSymbolsFactory(Map<String, Integer> spacerWidths1, Map<String, BlockCreator> blockCreators1) {
        this.spacerWidths = spacerWidths1;
        this.blockCreators = blockCreators1;
    }

    /**
     * @param blockCreatorMap the map of string and blocks
     */
    public void setBlockCreators(Map<String, BlockCreator> blockCreatorMap) {
        this.blockCreators = blockCreatorMap;
    }

    /**
     * @param stringIntegerMap the map of string and spaces.
     */
    public void setSpacerWidths(Map<String, Integer> stringIntegerMap) {
        this.spacerWidths = stringIntegerMap;
    }

    /**
     * @param s string.
     * @return true if 's' is a valid space symbol.
     */
    public boolean isSpaceSymbol(String s) {
        return (spacerWidths.containsKey(s));
    }


    /**
     * @param s string.
     * @return true if 's' is a valid block symbol.
     */
    public boolean isBlockSymbol(String s) {
        return (blockCreators.containsKey(s));
    }


    /**
     * @param s    the key of the map.
     * @param xpos start with.
     * @param ypos start with.
     * @return Block.
     */
    public AlineBlock getBlock(String s, int xpos, int ypos) {

        return this.blockCreators.get(s).create(xpos, ypos);
    }

    /**
     * @param s string.
     * @return the width in pixels associated with the given spacer-symbol.
     */
    public int getSpaceWidth(String s) {
        return this.spacerWidths.get(s);
    }

    /**
     * @param s key of block.
     * @return width of the block.
     */

    public int getWidth(String s) {
        return this.blockCreators.get(s).getWidth();
    }
    /**
     * @param s key of block.
     * @return height of the block.
     */
    public int getHeight(String s) {
        return this.blockCreators.get(s).getWidth();
    }

}
