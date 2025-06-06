/* 
* Ziel: Vergleich der Theoretisch errechneten Leistung mit einer tatsächlich gemessenen Leistung. Anschließende Visualisierung in einem Data Graph 
 * 1. Erstellen einer getDecimalDay Methode um 20 min Intervall-Dezimaltage zu berechenen. Berechnen aller deciamltage und spoeichern in Array (global)
 * 2. Theroetische Power daten in Graph schreiben 
 * 2.1. Array durchlaufen und für jeden Deziamltag den Zugehörigen wert der theoreticalPower durchlaufen 
 * 3. Tatsächliche Power Daten in Graph schreiben (Selbes vorgehen wie in 2.1:)
 * 3.1 für jedes Element in dem Array den Dezimaltag auslesen und interpolation der werte + schreiben in graph durchführen -> Wichtig! Wrte zwischenspeichern und immer nur den nächsten WErt auslesen 
 * 
 */

import java.util.Arrays;

public class Measurement {

    public static void main(String[] args) {
        final int DELTA_MINUTE = 20;

        /*
        * =======================================================
        * AB HIER KOPIEREN 
        */

        Measurement.createDecimalDays(args);
        Measurement.visualizeTheoreticalPower(args);
    }

    //! Globale Variablen:
    // Anlegen eines Arrays für die decimalDays. Pro Stunde 3 einheiten eines Decimaltages. 00, 20, 40 => 24 * 3 
    public static double[] decimalDays = new double[72];

    public static double[] createDecimalDays(String[] args) {

        // Deklarieren der Variablen für HourOfDay und MinuteOfHour 
        int HourOfDay = 0;
        int MinuteOfHour = 0;

        // Erstellen der nötigen Objekte für das auslesen des Tages (DateParser)
        DateParser date = new DateParser(args);
        // System.out.println("DATE: " + date);

        // Auslesen des eingestellten Tages mit DateParser
        double dayOfYear = date.getDayOfYear();
        // System.out.println("Day of Year: " + dayOfYear);

        // Zählvaribale für die stellen im Array 
        int entryNumber = 0;

        // Erte Schleife die die stunde hochzählt 
        for (int i = 0; i < 24; i++) {

            // Zweite schliefe die die minuten hochzählt 
            for (int j = 0; j < 3; j++) {

                double currentDecimalDay = Math.round((dayOfYear + (HourOfDay + MinuteOfHour / 60.0) / 24.0)
                        * 1000.0) / 1000.0;
                // System.out.println("Neu berechneter Decimaltag: " + currentDecimalDay);

                // Speichern des DecimalDays in Array 
                decimalDays[entryNumber] = currentDecimalDay;
                MinuteOfHour = MinuteOfHour + 20;

                // System.out.println("Durchlauf Nr.: " + (entryNumber + 1));
                entryNumber++;

            }

            HourOfDay++;
            MinuteOfHour = 0;
        }

        //* Komplettes Array anzeigen:
        System.out.print("Dezimaltage: [");
        for (int i = 0; i < decimalDays.length; i++) {
            System.out.print(decimalDays[i]);
            if (i < decimalDays.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");

        return decimalDays;

    }

    public static void visualizeTheoreticalPower(String[] args) {

        PanelData power = new PanelData(args);

        for (double decimalDay : decimalDays) {
            // System.out.println(decimalDay);
            double theoreticalPower = power.getPower(decimalDay);
    
            System.out.println("Theoretische Leistung: " + theoreticalPower);
    
            ResultPrinter.sendPower(decimalDay, theoreticalPower);
        }

        
        /*
        * =======================================================
        * BIS HIER KOPIEREN 
        */
        
    }
}
