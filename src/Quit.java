/**
 * public class Quit.
 */
public class Quit implements Task<Void> {
    /**
     * @return null. just Exit the program.
     */
    public Void run() {
        // exit the program.
        System.exit(0);
        return null;
    }
}
