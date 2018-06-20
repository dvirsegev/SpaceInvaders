/**
 * @param <T> generic.
 */
public interface Menu<T> extends Animation {
    /**
     * @param key       the key.
     * @param message   the message.
     * @param returnVal the return.
     */
    void addSelection(String key, String message, T returnVal);

    /**
     * @return the T.
     */
    T getStatus();

    /**
     * @param key     the key.
     * @param message the message
     * @param subMenu the submenu.
     */
    void addSubMenu(String key, String message, Menu<T> subMenu);
}