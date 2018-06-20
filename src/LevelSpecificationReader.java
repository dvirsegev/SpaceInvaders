import java.awt.Color;
import java.awt.Image;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * public class LevelSpecificationReader.
 */
public class LevelSpecificationReader {

    private int sumBalls;
    private int widthPaddle;
    private List<Velocity> velocities;
    private int speedPaddle;
    private int startX;
    private int startY;
    private int rowHeight;
    private int numBlocks;
    private BlocksFromSymbolsFactory factory;
    private Map<AlineBlock, Point> map = new HashMap<>();
    private static final int SIZE = 10000;

    /**
     * @param s1 type of string.
     * @param s2 type of string.
     * @return new Velcotiy.
     */
    private Velocity enterVel(String s1, String s2) {
        return new Velocity(Double.valueOf(s1), Double.valueOf(s2));
    }

    /**
     * @param reader the file we read.
     * @return the list of the levels.
     */
    public List<LevelInformation> fromReader(java.io.Reader reader) {
        // the list of levels
        List<LevelInformation> levels = new ArrayList<>();
        // the level
        LevelImplementation ilevel = null;
        List<AlineBlock> blockList;
        blockList = new ArrayList<>();
        char[] cbuf = new char[SIZE];

        try {
            // read all the file
            reader.read(cbuf);
            String s = new String(cbuf);
            // split into lines.
            String[] lines = s.split("\n");
            for (String line : lines) {
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                // if it equal to this, the level is null.
                if (line.equals("START_LEVEL")) {
                    ilevel = null;
                }
                // split the line according to :
                String[] keyAndValue = line.split(":");
                if (keyAndValue.length == 2) {
                    if (keyAndValue[0].equals("level_name")) {
                        ilevel = new LevelImplementation();
                        ilevel.setName(keyAndValue[1]);
                    }
                    // check the lines and add parameters..
                    checkLine(keyAndValue, ilevel);
                } else if (factory != null) {
                    if (!keyAndValue[0].equals("START_BLOCKS") && !keyAndValue[0].equals("END_BLOCKS")
                            && !keyAndValue[0].equals("END_LEVEL") && !keyAndValue[0].equals("START_LEVEL")) {
                        String[] blocks = keyAndValue[0].split("");
                        int tempX = startX;
                        int tempY = startY;
                        for (String block1 : blocks) {
                            if (factory.isBlockSymbol(block1)) {
                                AlineBlock alineBlock = factory.getBlock(block1, tempX, tempY);
                                map.put(alineBlock, new Point(tempX, tempY));
                                blockList.add(alineBlock);
                                tempX += factory.getWidth(block1);
                            } else if (factory.isSpaceSymbol(block1) && blocks.length != 1) {
                                tempX += factory.getSpaceWidth(block1);
                            }
                        }
                        startY += rowHeight + 1;
                    } else if (keyAndValue[0].equals("END_BLOCKS")) {
                        // add the blockList to the level
                        if (ilevel != null) {
                            List<AlineBlock> temp = blockList;
                            ilevel.setBlockList(temp);
                            ilevel.setMap(map);
                            ilevel.addAllInfo(sumBalls, this.widthPaddle, this.speedPaddle, this.velocities);
                            // add the level into the level-information. list.
                            levels.add(ilevel);
                            blockList = new ArrayList<>();
                        }
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // return the levels.
        return levels;
    }

    /**
     * @param ilevel     the level.
     * @param background the line of the backgroud.
     */
    public void setBackground(LevelImplementation ilevel, String background) {
        if (background.startsWith("color")) {
            // create new colorParses.
            ColorsParser colorParser = new ColorsParser();
            // add the color.
            Color color = colorParser.colorFromString(background);
            if (color == null) {
                background = background.substring(10, background.length() - 2);
                color = colorParser.colorRgb(background);
            }
            // set the backgroud to be this color.
            ilevel.setBackground(new ColorSprite(color));
            // if the background is image.
        } else if (background.startsWith("image")) {
            Image img = null;
            int firstIndex = background.indexOf("(");
            int secondIndex = background.indexOf(")");
            // check input.
            if ((firstIndex < 0) || (secondIndex < 0)) {
                return;
            }
            String imageFileName = background.substring(firstIndex + 1, secondIndex);
            try {
                // read the img.
                InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(imageFileName);
                img = ImageIO.read(is);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (img != null) {
                // set the image to be the background.
                ilevel.setBackground(new ImageSprite(img));
            }
        }

    }

    /**
     * @param keyAndValue the line.
     * @param ilevel      the level
     */
    public void checkLine(String[] keyAndValue, LevelImplementation ilevel) {
        switch (keyAndValue[0]) {
            case "ball_velocities":
                int count = 0;
                //list of velocities.
                velocities = new ArrayList<>();
                // split the line.
                String[] strings = keyAndValue[1].split(" ");
                for (String velocity : strings) {
                    String[] v = velocity.split(",");
                    count++;
                    // add velocity to the list.
                    velocities.add(enterVel(v[0], v[1]));
                }
                // create how much balls there is.
                sumBalls = count;
                break;
            case "block_definitions":
                String blockFileName = keyAndValue[1];
                BufferedReader is = null;
                try {
                    InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(blockFileName);
                    is = new BufferedReader(new InputStreamReader(inputStream));
                    this.factory = BlocksDefinitionReader.fromReader(is);
                    //ilevel.setBlockList(factory);
                } finally {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case "paddle_speed":
                this.speedPaddle = Integer.parseInt(keyAndValue[1]);
                break;
            case "paddle_width":
                this.widthPaddle = Integer.parseInt(keyAndValue[1]);
                break;
            case "blocks_start_x":
                startX = Integer.parseInt(keyAndValue[1]);
                break;
            case "blocks_start_y":
                startY = Integer.parseInt(keyAndValue[1]);
                break;
            case "row_height":
                rowHeight = Integer.parseInt(keyAndValue[1]);
                break;
            case "num_blocks":
                numBlocks = Integer.parseInt(keyAndValue[1]);
                break;
            case "background":
                setBackground(ilevel, keyAndValue[1]);
                break;
            default:
                break;
        }

    }
}
