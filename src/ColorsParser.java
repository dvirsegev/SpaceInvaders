import java.awt.Color;

/**
 * public class ColorsParser.
 */
public class ColorsParser {
    /**
     * @param s String
     * @return parse color definition and return the specified color.
     */
    public java.awt.Color colorFromString(String s) {
        switch (s) {
            case "color(yellow)":
                return Color.yellow;
            case "color(white)":
                return Color.white;
            case "color(red)":
                return Color.RED;
            case "color(blue)":
                return Color.BLUE;
            case "color(black)":
                return Color.BLACK;
            case "color(pink)":
                return Color.pink;
            case "color(gray)":
                return Color.GRAY;
            case "color(cyan)":
                return Color.cyan;
            case "color(green)":
                return Color.green;
            case "color(orange)":
                return Color.orange;
            case "color(lightGray)":
                return Color.lightGray;
            default:
                return null;
        }
    }

    /**
     * @param color string line.
     * @return the Color
     */
    public java.awt.Color colorRgb(String color) {
        int x, y, z;
        String[] s = color.split(",");
        x = Integer.parseInt(s[0]);
        y = Integer.parseInt(s[1]);
        z = Integer.parseInt(s[2]);
        return new Color(x, y, z);
    }
}