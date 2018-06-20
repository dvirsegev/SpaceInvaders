import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * public class LevelImplementation .
 */
public class LevelImplementation implements LevelInformation {

    private int sumBalls;
    private int width;
    private int speed;
    private String name;
    private List<Velocity> velocities;
    private List<AlineBlock> blockList;
    private Sprite background;
    private Map<AlineBlock, Point> blockPointMap;

    /**
     * @param sumballs    sum of balls.
     * @param width1      the width1 of paddle.
     * @param speed1      the speed of paddle.
     * @param velocities1 the speed of each balls.
     */
    public void addAllInfo(int sumballs, int width1, int speed1, List<Velocity> velocities1) {
        this.sumBalls = sumballs;
        this.width = width1;
        this.speed = speed1;
        this.velocities = velocities1;
    }

    /**
     * @param s1 setName.
     */
    public void setName(String s1) {
        this.name = s1;
    }

    /**
     * @return the numbers of the ball
     */
    @Override
    public int numberOfBalls() {
        return this.sumBalls;
    }

    /**
     * @return the paddle speed.
     */
    @Override
    public int paddleSpeed() {
        return this.speed;
    }

    /**
     * @return the paddle width.
     */
    @Override
    public int paddleWidth() {
        return this.width;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> list = new ArrayList<>();
        for (int i = 0; i < numberOfBalls(); i++) {
            list.add(this.velocities.get(i));

        }
        return list;
    }

    @Override
    public String levelName() {
        return this.name;
    }

    @Override
    public Sprite getBackground() {
        return background;
    }

    @Override
    public void setBackground(Sprite sprite) {
        this.background = sprite;
    }

    @Override
    public List<AlineBlock> blocks() {
        return this.blockList;
    }

    /**
     * @param map set map for blocks.
     */
    @Override
    public void setMap(Map<AlineBlock, Point> map) {
        this.blockPointMap = map;
    }

    @Override
    public Map<AlineBlock, Point> getMap() {
        return this.blockPointMap;
    }


    /**
     * @param blockList1 setBlockList.
     */
    public void setBlockList(List<AlineBlock> blockList1) {
        this.blockList = blockList1;
    }

}



