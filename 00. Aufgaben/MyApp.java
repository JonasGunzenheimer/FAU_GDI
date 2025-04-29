/* 
 * Aufgabe 01: 
 ! Schreiben Sie ein Programm für die Datei MyApp.java welches exakt den folgenden Text ausgibt:

    Hello from Java!
    Let's start coding!

    Coding adventures await.

 */

public class MyApp { // ! Gleicher Class-Name wie Programmname
    public static void main(String[] args) { // main Methode als entry Point für Programm
        System.out.println("Hello from Java!");
        System.out.println("Let's start coding!");
        System.out.println(); // ! Leerzeile. Anscheinend geht auch ein leerer Ausdruck.
        System.out.println("Coding adventures await.");
    }
}