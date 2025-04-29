/* 
 * Variablen

! Vervollständigen Sie das unten angegebene Programm so, dass es übersetzt und ausgeführt werden kann.

* Das Programm soll Folgendes ausgeben:

* a: 8 
* b: 13
* c: 21

*/

/* 

! Bereits vorgegebener Code: 

public class Calculator {
    public static void main(String[] args) {




        b = 13;
        int c = a + b;

        System.out.println("a: " + a);
        System.out.println("b: " + b);
        System.out.println("c: " + c);
    }
}
 */

public class Calculator {

    public static void main(String[] args) {
        int a = 8, b; // die Variable b muss erst initiiert werden, bevor ihr Wert auf "13" geändert
                      // werden kann!

        b = 13;
        int c = a + b;

        System.out.println("a: " + a);
        System.out.println("b: " + b);
        System.out.println("c: " + c);
    }

}
