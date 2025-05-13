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

        // 1. Konstanten anlegen

        final double VOLTAGE = 233.0;
        final double CURRENT = 9.1;
        System.out.println("    Spannung: " + VOLTAGE + "V");
        System.out.println("Stromstaerke: " + CURRENT + "A");
        System.out.println(); // ! Leerzeile

        // 2. Variable für Leistung

        float pwr = (float) (VOLTAGE * CURRENT);
        System.out.println("    Leistung: " + pwr + "W");

        // 3. Berechnung der Energie

        int time = 12;

        // 3.1 Ausgeben der Energie als Zahl mit max zwei Nachkommastellen: 
        // 3.1.1 Energie als Zahl mit allen Nachkommastellen berechenen 
        double energyWithAllDecimals = (pwr * time) / 1000; // * Nein, das ist nicht KI Generiert, ich benenne meine Variablen tatsächlich so eigentümlich :D
        // System.out.println("Energy with all decimals: " + energyWithAllDecimals);

        // 3.1.2 Ganze Zahlen extrahieren (Vorkommastellen)
        int energyWholeNumber = (int) (energyWithAllDecimals);
        // System.out.println("energyWholeNumber" + energyWholeNumber);

        // 3.1.3 Verschieben des Komma's um zwei Stellen
        int energyDecimalsPre = (int) (energyWithAllDecimals * 100);
        // System.out.println("Energy Decimals Precursor: " + energyDecimalsPre);

        // 3.1.4 Extrahieren der letzten beiden Stellen mit Modulo 
        double energyDecimalsAsWhole = (energyDecimalsPre % 100);

        // 3.1.5 Umwanndeln der ganzen Zahl in eine Nachkkommstelle  
        double energyDecimals = energyDecimalsAsWhole / 100.0; // ! 100.0 muss hier explizit <100> als Double geschrieben werden, da Java bei der Division sonst als <int> ausgiebt

        // 3.1.6 Zusammenfügen der ganzen Zahl und der Nachkommstalle 
        float energy = (float) (energyWholeNumber + energyDecimals);

        // System.out.println("energyDecimalsAsWholeNumber: " + energyDecimalsAsWhole);
        // System.out.println("energyDecimals: " + energyDecimals);

        System.out.println(" Energie 12h: " + energy + "kWh");
    }
}