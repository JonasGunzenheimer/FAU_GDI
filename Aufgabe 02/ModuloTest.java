class ModuloTest {
    public static void main(String[] args) {

        double num1 = 5;
        double num2 = 3;
        int wholeNumber = (int) (num1 / num2);
        System.out.println(wholeNumber);

        int rest1 = (int) (num1 / num2 * 1000) % 1000 / 10;

        System.out.println(rest1);
    }
}