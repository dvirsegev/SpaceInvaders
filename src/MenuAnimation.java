import biuoop.DrawSurface;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * @param <T> generics code.
 *            Remember!- Menu extends Animation.
 */
public class MenuAnimation<T> implements Menu<T> {

    private String title;
    private biuoop.KeyboardSensor keyboardSensor;
    private T type;
    private List<String> keys;
    private List<String> message;
    private List<T> types;
    private Boolean aBoolean;
    private List<Menu<T>> menus;
    private List<Object> check;
    private AnimationRunner runner;

    /**
     * @param string  the title.
     * @param ks      the keyboard.
     * @param runner1 animation runner.
     */
    public MenuAnimation(String string, biuoop.KeyboardSensor ks, AnimationRunner runner1) {
        this.title = string;
        this.keyboardSensor = ks;
        this.keys = new ArrayList<>();
        this.types = new ArrayList<>();
        this.message = new ArrayList<>();
        this.aBoolean = false;
        this.menus = new ArrayList<>();
        this.check = new ArrayList<>();
        this.runner = runner1;
    }

    /**
     * @param key       the key to stop the animation.
     * @param imessage  the message show in the menu.
     * @param returnVal the animation we run.
     */
    public void addSelection(String key, String imessage, T returnVal) {
        this.keys.add(key);
        this.message.add(imessage);
        this.types.add(returnVal);
        this.check.add(null);
        this.menus.add(null);
    }

    /**
     * @return the animation we run
     */
    public T getStatus() {
        return this.type;
    }

    @Override
    public void addSubMenu(String key, String imessage, Menu<T> subMenu) {
        this.menus.add(subMenu);
        this.keys.add(key);
        this.message.add(imessage);
        this.check.add(true);
        this.types.add(null);
    }

    /**
     * @param d  doOneFrame(DrawSurface) is in charge of the logic.
     * @param dt it specifies the amount of seconds passed since the last call
     */
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.BLACK);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        d.setColor(Color.BLACK);
        d.drawText(301, 50, this.title, 32);
        d.drawText(299, 50, this.title, 32);
        d.drawText(300, 51, this.title, 32);
        d.drawText(300, 49, this.title, 32);
        d.setColor(Color.YELLOW);
        d.drawText(300, 50, this.title, 32);
        d.setColor(Color.YELLOW);
        d.drawLine(290, 51, 460, 50);

        int i;
        for (i = 0; i < this.message.size(); ++i) {
            int optionX = 300;
            int optionY = 150 + i * 50;
            String optionText = "(" + this.keys.get(i) + ") " + this.message.get(i);
            d.setColor(Color.RED);
            d.drawText(optionX + 1, optionY, optionText, 24);
            d.drawText(optionX - 1, optionY, optionText, 24);
            d.drawText(optionX, optionY + 1, optionText, 24);
            d.drawText(optionX, optionY - 1, optionText, 24);
            d.setColor(Color.GREEN);
            d.drawText(optionX, optionY, optionText, 24);
        }
        for (int j = 0; j < keys.size(); j++) {
            if (keyboardSensor.isPressed(keys.get(j))) {
                if (menus.get(j) != null) {
                    MenuAnimation<T> nextMenu = (MenuAnimation<T>) this.menus.get(j);
                    this.runner.run(nextMenu);
                    this.type = nextMenu.getStatus();
                    aBoolean = true;
                    nextMenu.reset();
                } else {
                    this.type = this.types.get(j);
                    aBoolean = true;
                }
                break;
            }
        }

    }

    /**
     * reset the menu.
     */
    public void reset() {
        this.type = null;
        aBoolean = false;
    }

    /**
     * @return true or false.
     */
    public boolean shouldStop() {
        return aBoolean;
    }
}
