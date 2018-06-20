/**
 * public class Counter.
 */
public class Counter {
    // member.
    private int num;

    /**
     * @param number dd number to current count.
     */
    void increase(int number) {
        this.num = this.num + number;

    }

    /**
     * @param number subtract number from current count.
     */
    void decrease(int number) {
        this.num = this.num - number;
    }

    /**
     * @return current count.
     */
    int getValue() {
        return this.num;
    }
}