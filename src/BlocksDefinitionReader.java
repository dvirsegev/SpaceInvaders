import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * public class BlocksDefinitionReader.
 */
public class BlocksDefinitionReader {

    /**
     * @param splittedLine  the line of default.
     * @param defaultValues the map.
     */
    public static void setDefault(String[] splittedLine, Map<String, String> defaultValues) {
        for (int j = 1; j < splittedLine.length; j++) {
            String[] defaultHeight = splittedLine[j].split(":");
            defaultValues.put(defaultHeight[0], defaultHeight[1]);
        }

    }


    /**
     * @param reader the file
     * @return BlocksFromSymbolsFactory.
     */
    public static BlocksFromSymbolsFactory fromReader(java.io.Reader reader) {
        Map<String, Integer> spacerWidths = new TreeMap<>();
        Map<String, BlockCreator> blockCreators = new TreeMap<>();
        BlocksFromSymbolsFactory factory = new BlocksFromSymbolsFactory(spacerWidths, blockCreators);
        Map<String, String> defaultValues = new TreeMap<>();
        char[] cbuf = new char[10000];
        try {
            reader.read(cbuf);
            String s = new String(cbuf);
            String[] lines = s.split("\n");
            for (String line : lines) {
                if (line.isEmpty() || (line.charAt(0) == '#')) {
                    continue;
                }
                String[] splittedLine = line.split(" ");
                if (splittedLine[0].equals("default")) {
                    setDefault(splittedLine, defaultValues);
                    //factory.setDefaultHeight(new Integer(defaultHeight[1]));
                } else if (splittedLine[0].equals("bdef")) {
                    CreateBlock creator = new CreateBlock();
                    creator.setDefault(defaultValues);
                    for (int j = 1; j < splittedLine.length; j++) {
                        String[] keyAndValue = splittedLine[j].split(":");
                        if (keyAndValue.length != 2) {
                            continue;
                        }
                        // the letter of the block.
                        if (keyAndValue[0].equals("symbol")) {
                            creator.setSymbol(keyAndValue[1]);
                            // the width of the block.
                        } else if (keyAndValue[0].equals("width")) {
                            creator.setWidth(new Integer(keyAndValue[1]));
                            // the hit_points of the block.
                        } else if (keyAndValue[0].equals("hit_points")) {
                            creator.setLive(Integer.parseInt(keyAndValue[1]));
                            // the fill of the block.
                        } else if (keyAndValue[0].equals("fill")) {
                            creator.fillColorOrImage(keyAndValue[1]);
                            // the height of the block.
                        } else if (keyAndValue[0].equals("height")) {
                            creator.setHeight(Integer.parseInt(keyAndValue[1]));
                        } else if (keyAndValue[0].equals("stroke")) {
                            creator.fillStorke(keyAndValue[1]);
                        } else if (keyAndValue[0].startsWith("fill-")) {
                            String[] splitFill = keyAndValue[0].split("-");
                            if (splitFill.length == 2) {
                                int fillNumber = new Integer(splitFill[1]);
                                if (fillNumber == 1) {
                                    creator.fillColorOrImage(keyAndValue[1]);
                                }
                                creator.fillNColorOrImage(fillNumber, keyAndValue[1]);
                            }
                        }
                    }
                    // add block to the map of block Creators.
                    blockCreators.put(creator.getString(), creator);
                    // check if the line start with sdef.
                } else if (splittedLine[0].equals("sdef")) {
                    // set space.
                    setMapSpace(spacerWidths, splittedLine);
                }

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        factory.setBlockCreators(blockCreators);
        factory.setSpacerWidths(spacerWidths);
        return factory;
    }

    /**
     * @param spacerWidths the map of spacer.
     * @param splittedLine the line.
     */
    public static void setMapSpace(Map<String, Integer> spacerWidths, String[] splittedLine) {
        String[] symbols = splittedLine[1].split(":");
        String[] width = splittedLine[2].split(":");
        String sub = width[1].substring(0, 2);
        String sign = null;
        int widthBlock = 0;
        if (symbols[0].equals("symbol")) {
            sign = symbols[1];
        }
        if (width[0].equals("width")) {
            sub = sub.trim();
            widthBlock = Integer.parseInt(sub);
        }
        if (sign != null && widthBlock != 0) {
            spacerWidths.put(sign, widthBlock);
        }
    }
}

