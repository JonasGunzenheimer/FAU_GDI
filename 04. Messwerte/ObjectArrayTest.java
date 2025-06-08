public class ObjectArrayTest {

    public static Object[][] data = new Object[10][2];

    public static void main(String[] args) {
        data[0] = new Object[] { "Anna", 25 };
        data[1] = new Object[] { "Bob", 30 };
        data[2] = new Object[] { "Clara", 28 };

        // Direkt ausgeben ohne Zwischenvariable
        System.out.println((String) data[0][0]); // "Anna"
        System.out.println((Integer) data[0][1]);
    }
}
