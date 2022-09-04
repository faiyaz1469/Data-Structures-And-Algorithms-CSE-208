public class KeyValuePair {
    private final String key;
    private final int value;

    public KeyValuePair(String key, int value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public static void main(String[] args) {

        double load = 0.4;

        while ( load <= 0.9) {
            System.out.println(load);
            load *= 10;
            load += 1;
            load /= 10;

        }
    }
}
