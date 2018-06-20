import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Class ASS3Game.
 */
public class GameLevel implements Animation {
    private AnimationRunner runner;
    private boolean running;
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private biuoop.GUI gui;
    private Paddle paddle;
    private Counter counterBlocks;
    private Counter counterShield;
    private Counter score;
    private double speed;
    private boolean aBoolean = true;
    private Counter lives;
    private biuoop.KeyboardSensor keyboard;
    private double paddleShot;
    private LevelInformation levelInformation;
    private ScoreTrackingListener trackingListener;
    private BlockRemover blockRemover;
    private List<Block> shields;
    private int count = 0;
    private double alienShoot;
    private Map<AlineBlock, Point> maps;
    private static final int MAXWIDTH = 800;
    private Counter counterLifePaddle;
    private static final double ALLOWSHOT = 0.0;


    /**
     * initialize the game.
     *
     * @param levelInformation the level.
     * @param animationRunner  type of AnimationRunner.
     * @param keyboard         the keyboardSensor.
     * @param score1           the score of the games.
     * @param lives1           the lives of the user.
     * @param spped1           the speed of the alien.
     * @param maps             the map of Block to his upperLeft start.
     */
    public GameLevel(LevelInformation levelInformation, AnimationRunner animationRunner,
                     KeyboardSensor keyboard, Counter score1, Counter lives1,
                     double spped1, Map<AlineBlock, Point> maps) {
        // new SpriteCollection.
        this.sprites = new SpriteCollection();
        // the size and the name of the screen.
        this.gui = animationRunner.getGui();
        // new environment collection.
        this.environment = new GameEnvironment();
        // new counter of remove block
        this.counterBlocks = new Counter();
        this.counterShield = new Counter();
        // new counter for scoring.
        this.score = score1;
        // new counter for lives.
        this.lives = lives1;
        // new runner for moveOneStep.
        this.runner = new AnimationRunner(gui, 60);
        // getKeyboardSenor.
        this.keyboard = keyboard;
        //  the level.
        this.levelInformation = levelInformation;
        // the shields
        this.shields = new ArrayList<>();
        // how many shields there is.
        this.counterShield = new Counter();
        //the speed of the alien
        this.speed = spped1;
        // the shoot of the alien.
        this.alienShoot = 0;
        // life of the paddle.
        counterLifePaddle = new Counter();
        this.maps = maps;
    }

    /**
     * @param c type of Collidable.
     * @apiNote adding the c to the list of Collidables.
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * @param s object type of sprite.
     * @apiNote adding the s to the list of sprites.
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * @param c remove from collidable list.
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);

    }

    /**
     * @param s remove from sprite list.
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }


    /**
     * intialize the game: put the balls and the blocks to the game.
     */
    public void initialize() {
        // setBackGround.
        this.sprites.addSprite(levelInformation.getBackground());
        // create the block up.
        Rectangle info = new Rectangle(new Point(0, 0), MAXWIDTH, 20);
        Block information = new Block(info, Color.BLACK);
        information.setSpirte(this);
        LivesIndicator livesIndicator = new LivesIndicator(information, lives);
        addSprite(livesIndicator);
        // Create scoreIndicator for the score and add it to the spriteCo   llection.
        ScoreIndicator scoreIndicator = new ScoreIndicator(information, score, levelInformation);
        // add it to the sprite Collection.
        this.addSprite(scoreIndicator);
        // call blockRemover method, for remove alien.
        blockRemover = new BlockRemover(this, counterBlocks, levelInformation.blocks());
        // create trackingListener method, for the scoing each time we hit a alien.
        trackingListener = new ScoreTrackingListener(score);
        // createAliens
        createAliens();
        setShield();
    }

    /**
     * createAliens
     */
    public void createAliens() {
        for (int i = 0; i < this.levelInformation.blocks().size(); i++) {
            levelInformation.blocks().get(i).addToGame(this);
            levelInformation.blocks().get(i).addHitListener(blockRemover);
            levelInformation.blocks().get(i).addHitListener(trackingListener);
            levelInformation.blocks().get(i).setHit(1);
            counterBlocks.increase(1);
        }
    }

    /**
     * @return this running.
     */
    public boolean shouldStop() {
        return !this.running;
    }


    /**
     * @param d  doOneFrame(DrawSurface) is in charge of the logic.
     * @param dt the frame.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        // drawAllOn the sprites.
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed(dt, this);
        moveAlien(dt);
        List<AlineBlock> lowestEnemy = new ArrayList<>();
        if (levelInformation.blocks().size() != 0) {
            loadAlienShoot(levelInformation.blocks(), lowestEnemy);
        }
        for (int i = 0; i < levelInformation.blocks().size(); i++) {
            if (levelInformation.blocks().get(i).getCollisionRectangle().getDownLeft().getY() > 470) {
                lostRestart();
                running = false;
                break;
            }
        }
        if (paddleShot != ALLOWSHOT) {
            this.paddleShot -= dt;
        }
        if (alienShoot >= ALLOWSHOT) {
            alienShoot -= dt;
        } else {
            fireAlien(lowestEnemy);
        }
        // if the user pressed p,  acting PauseScreen.
        if (this.keyboard.isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(keyboard, "space", new PauseScreen()));
        }
        if (this.keyboard.isPressed(KeyboardSensor.SPACE_KEY) && this.paddleShot <= 0.0) {
            setPaddleFire();
        }
        if (this.counterBlocks.getValue() == 0) {
            score.increase(100);
            this.removeSprite(paddle);
            this.removeCollidable(paddle);
            running = false;
            goodRestart();
        }
        if (this.counterShield.getValue() == 0) {
            running = false;
            lostRestart();
        }
        if (this.counterLifePaddle.getValue() == 0) {
            running = false;
            lostRestart();
        }
    }

    /**
     * lostRestart the game.
     */
    public void lostRestart() {
        this.removeSprite(paddle);
        this.removeCollidable(paddle);
        setAlienAgain();
        this.lives.decrease(1);
        if (count != 0) {
            this.speed = (speed / (count * 1.2));
        }
    }

    /**
     * move to the next level.
     */
    public void goodRestart() {
        //setAlienAgain();
        this.removeSprite(paddle);
        this.removeCollidable(paddle);
        deleteShields();
    }


    /**
     * build again the alien.
     */
    public void setAlienAgain() {
        for (int i = 0; i < levelInformation.blocks().size(); i++) {
            Block b = levelInformation.blocks().get(i);
            Point start = maps.get(b);
            levelInformation.blocks().get(i).getCollisionRectangle().setUpperLeft(start);
            levelInformation.blocks().get(i).addHitListener(trackingListener);
            levelInformation.blocks().get(i).addHitListener(blockRemover);
            this.counterBlocks.increase(1);
        }
    }

    /**
     * setPaddleFire.
     */
    public void setPaddleFire() {
        this.paddleShot = 0.35;
        int index = (int) this.paddle.getCollisionRectangle().getUpperLeft().getX();
        Ball ball = new Ball(index + this.paddle.getWidthpad() / 2,
                530, 4, Color.WHITE, this.environment);
        ball.setVelocity(0, -500);
        ball.addToGame(this);

    }

    /**
     * @param blocks      list of the all aliens.
     * @param lowestEnemy create the list with the lowest place.
     */
    public void loadAlienShoot(List<AlineBlock> blocks, List<AlineBlock> lowestEnemy) {
        List<AlineBlock> oneRaw = new ArrayList<>();
        double yPosition = blocks.get(0).getCollisionRectangle().getUpperLeft().getY();
        for (AlineBlock upperBlock : blocks) {
            if (upperBlock.getCollisionRectangle().getUpperLeft().getY() == yPosition) {
                Point upperPoint = upperBlock.getCollisionRectangle().getUpperLeft();
                oneRaw.add(upperBlock);
                for (AlineBlock lowerBlock : blocks) {
                    Point lowerPoint = lowerBlock.getCollisionRectangle().getUpperLeft();
                    if (Math.abs(lowerPoint.getX() - upperPoint.getX()) < 1) {
                        if (lowerPoint.getY() > upperPoint.getY()) {
                            oneRaw.add(lowerBlock);
                        }
                    }
                }
                lowestEnemy.add(oneRaw.get(oneRaw.size() - 1));
            }
            oneRaw.clear();
        }

    }

    /**
     * @param listLowest the list of the alien that can shot.
     */
    private void fireAlien(List<AlineBlock> listLowest) {
        Random random = new Random();
        int randomNumber = random.nextInt(listLowest.size());
        AlineBlock alineBlock = listLowest.get(randomNumber);
        Rectangle rectangle = alineBlock.getCollisionRectangle();
        double x = rectangle.getUpperLeft().getX() + rectangle.getWidth() / 2;
        double y = rectangle.getUpperLeft().getY() + rectangle.getHeight() + 50;
        Ball fireBall = new Ball((int) x, (int) y, 4, Color.red, environment);
        Velocity v = new Velocity(0, 500);
        // Setting the velocity of the ball.
        fireBall.setVelocity(v);
        // Adding this object to the game.
        fireBall.addToGame(this);
        alienShoot = 0.5;
    }


    /**
     * Run the game -start the animation loop, drawing the blocks and moving the balls.
     */
    public void playOneTurn() {
        setPaddle();
        this.runner.run(new CountdownAnimation(2, 3, sprites));
        this.running = true;
        this.runner.run(this);
    }

    /**
     * set the Paddle again to the game.
     */
    public void setPaddle() {
        counterLifePaddle.increase(1);
        PaddleRemover paddleRemover = new PaddleRemover(this, counterLifePaddle);
        this.paddle = new Paddle(gui.getKeyboardSensor(), this.environment);
        this.paddle.setWidthpad(this.levelInformation.paddleWidth());
        this.paddle.setSpeed(this.levelInformation.paddleSpeed());
        this.paddle.addHitListener(paddleRemover);
        this.paddle.addToGame(this);
    }

    /**
     * @return the lives in the game.
     */
    public int getLives() {
        return this.lives.getValue();
    }

    /**
     * @return how much blocks there is.
     */
    public int getCounterBlock() {
        return this.counterBlocks.getValue();
    }

    /**
     * @return the score.
     */
    public Counter getScore() {
        return this.score;
    }

    /**
     * setShield.
     */
    public void setShield() {
        ShieldRemover shieldRemover = new ShieldRemover(this, this.counterShield);
        int x, y;
        for (int startX = 100; startX <= 650; startX += 250) {
            x = startX;
            y = 470;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 15; j++) {
                    Block block = new Block(new Rectangle(new Point(x, y), 10, 10), Color.YELLOW);
                    block.addHitListener(shieldRemover);
                    block.setHit(1);
                    block.addToGame(this);
                    this.shields.add(block);
                    x += 10;
                    this.counterShield.increase(1);
                }
                x = startX;
                y += 10;
            }
        }
    }

    /**
     * detele Shields.
     */
    public void deleteShields() {
        for (Block shield : shields) {
            shield.removeFromGame(this);
        }
    }

    /**
     * @param dt the frame.
     *           how to move the alien .
     */
    public void moveAlien(double dt) {
        if (aBoolean) {
            for (int i = 0; i < levelInformation.blocks().size(); i++) {
                boolean answer = moveOneStep(dt, levelInformation.blocks().get(i).getCollisionRectangle());
                if (!answer) {
                    setDic(levelInformation.blocks());
                    break;
                }
            }
        } else {
            for (int i = 0; i < levelInformation.blocks().size(); i++) {
                boolean answer = movebackStep(dt, levelInformation.blocks().get(i).getCollisionRectangle());
                if (!answer) {
                    setDic(levelInformation.blocks());
                    break;
                }
            }
        }
    }

    /**
     * @param blocks Change direction.
     */
    public void setDic(List<AlineBlock> blocks) {
        double y;
        for (int i = 0; i < levelInformation.blocks().size(); i++) {
            y = blocks.get(i).getCollisionRectangle().getUpperLeft().getY() + 15;
            levelInformation.blocks().get(i).getCollisionRectangle().setUpperLeft(
                    new Point(levelInformation.blocks().get(i).getCollisionRectangle().getUpperLeft().getX(), y));
            //recPaddle.setUpperLeft(new Point(recPaddle.getUpperLeft().getX(), y));
        }
        this.speed = speed * 1.2;
        count++;
        aBoolean = !aBoolean;
    }

    /**
     * @param dt        the frame.
     * @param recPaddle move the alien right.
     * @return false if we reach the limit.
     */
    public boolean moveOneStep(double dt, Rectangle recPaddle) {
        double dtspeed = speed * dt;
        /* check if the left top point is reached to the limit of the screen. if not, move each
        corners of the rectangle steps to the left.
        */
        if (recPaddle.getUpperLeft().getX() <= MAXWIDTH) {
            recPaddle.getUpperLeft().setnewPoint(recPaddle.getUpperLeft().getX() + dtspeed,
                    recPaddle.getUpperLeft().getY());
            recPaddle.getDownLeft().setnewPoint(recPaddle.getDownLeft().getX() + dtspeed,
                    recPaddle.getDownLeft().getY());
            recPaddle.getDownRight().setnewPoint(recPaddle.getDownRight().getX() + dtspeed,
                    recPaddle.getDownRight().getY());
            recPaddle.getUpperRight().setnewPoint(recPaddle.getUpperRight().getX() + dtspeed,
                    recPaddle.getUpperRight().getY());
        }
        return !(recPaddle.getUpperRight().getX() >= MAXWIDTH);
    }

    /**
     * @param dt        the frame.
     * @param recPaddle move the alien left.
     * @return false if we reach the limit.
     */
    public boolean movebackStep(double dt, Rectangle recPaddle) {
        double dtspeed = speed * dt;
        recPaddle.getUpperLeft().setnewPoint(recPaddle.getUpperLeft().getX() - dtspeed,
                recPaddle.getUpperLeft().getY());
        recPaddle.getDownLeft().setnewPoint(recPaddle.getDownLeft().getX() - dtspeed,
                recPaddle.getDownLeft().getY());
        recPaddle.getDownRight().setnewPoint(recPaddle.getDownRight().getX() - dtspeed,
                recPaddle.getDownRight().getY());
        recPaddle.getUpperRight().setnewPoint(recPaddle.getUpperRight().getX() - dtspeed,
                recPaddle.getUpperRight().getY());
        return !(recPaddle.getUpperLeft().getX() <= 0);
    }
}