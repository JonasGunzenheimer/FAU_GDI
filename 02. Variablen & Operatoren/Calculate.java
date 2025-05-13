/* 
 * Erstellen Sie ein Programm in der öffentlichen Klasse Calculate. Legen Sie darin zwei Konstanten mit dem Datentypen double für eine Spannung (VOLTAGE) und eine Stromstärke (CURRENT) an. Initialisieren Sie die Konstanten mit den unten angegebenen Werten. Legen Sie eine weitere Variable pwr mit dem Datentypen float an und speichern Sie darin die resultierende Leistung (Produkt aus VOLTAGE und CURRENT).

* Berechnen Sie außerdem die in 12 Stunden verbrauchte Energie (energy als float)  in kWh. Sie können dafür die folgende Formel verwenden:  energy=pwr∗time1000.

![Schwer] Geben Sie die Energie mit maximal zwei Nachkommastellen aus. Sie dürfen unnötige Stellen weglassen und müssen nicht korrekt runden. Verwenden Sie für die Bestimmung der formatierten Ausgabe weder Methodenaufrufe noch Kontrollstrukturen.

* Ihr Programm soll nun die Werte exakt wie unten abgebildet ausgeben. Verwenden Sie für die Ausgabe nur die entsprechenden Variablen. Legen Sie außer den vier oben angegebenen Variablen und Konstanten keine zusätzlichen an.

    Spannung: 233.0V
Stromstaerke: 9.1A

    Leistung: 2120.3W
 Energie 12h: 25.44kWh
 */

public class Calculate {
    public static void main(String[] args) {
        final double VOLTAGE = 233.0;
        final double CURRENT = 9.1;

        float pwr = (float) (VOLTAGE * CURRENT);
        float energy = (float) ((int) (pwr * 12 / 10) / 100.0);

        System.out.println(" Spannung: " + VOLTAGE + "V");
        System.out.println("Stromstaerke: " + CURRENT + "A");
        System.out.println();
        System.out.println(" Leistung: " + pwr + "W");
        System.out.println(" Energie 12h: " + energy + "kWh");
    }
}