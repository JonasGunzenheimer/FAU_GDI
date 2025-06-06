/* 
* Ziel: Vergleich der Theoretisch errechneten Leistung mit einer tatsächlich gemessenen Leistung. Anschließende Visualisierung in einem Data Graph 
 * 1. Erstellen einer getDecimalDay Methode um 20 min Intervall-Dezimaltage zu berechenen. Berechnen aller deciamltage und spoeichern in Array (global)
 * 2. Theroetische Power daten in Graph schreiben 
 * 2.1. Array durchlaufen und für jeden Deziamltag den Zugehörigen wert der theoreticalPower durchlaufen 
 * 3. Tatsächliche Power Daten in Graph schreiben (Selbes vorgehen wie in 2.1:)
 * 3.1 für jedes Element in dem Array den Dezimaltag auslesen und interpolation der werte + schreiben in graph durchführen -> Wichtig! Wrte zwischenspeichern und immer nur den nächsten WErt auslesen 
 * 
 */

public class Measurement {

    public static void main(String[] args) {
        final int DELTA_MINUTE = 20;

        /*
        * =======================================================
        * AB HIER KOPIEREN 
        */

        Measurement.createDecimalDays(args);
        Measurement.visualizeTheoreticalPower(args);
        Measurement.visualizeActualPower(args); 
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
        // System.out.print("Dezimaltage: [");
        // for (int i = 0; i < decimalDays.length; i++) {
        //     System.out.print(decimalDays[i]);
        //     if (i < decimalDays.length - 1) {
        //         System.out.print(", ");
        //     }
        // }
        // System.out.println("]");

        return decimalDays;

    }

    public static void visualizeTheoreticalPower(String[] args) {

        PanelData power = new PanelData(args);

        // Array mit DezimalTagen durchlaufen und für jeden die theoretische Leistung ausgeben 
        for (double decimalDay : decimalDays) {
            // System.out.println(decimalDay);
            double theoreticalPower = power.getPower(decimalDay);

            // System.out.println("Theoretische Leistung am Dezimaltag " + decimalDay +": " + (Math.round(theoreticalPower * 100.0)/100.0) + "");

            ResultPrinter.sendPower(decimalDay, theoreticalPower);
        }

    }

    public static void visualizeActualPower(String[] args) {


        // 1. Messwerte auslesen -> Für jeden Dezimaltag t gibt es ein t_0 und ein t_1. Für jedes t_0 gibt es ein y_0 und für jedes t_1 gitb es ein y_1. Zwischen diesen beiden werten muss interpoliert werden 
        // 1.1 exakte Zeitpunkte der Messwerte auslesen 

        // DataPoint dataPoint_1 = SunCalculator.getPrevious(SunCalculator.SOLAR, 293.0); 
        // System.out.println(dataPoint_1);

        // double dcimalDay_1 = dataPoint_1.getDecimalDay(); 
        // System.out.println(dcimalDay_1);

        // double value_1 = dataPoint_1.getValue(); 
        // System.out.println(value_1);
        for (double decimalDay : decimalDays) { 
            
            // Letzer Datenpunkt vor dem Dezimaltag auslesen #
            DataPoint previousDataPoint = SunCalculator.getPrevious(SunCalculator.SOLAR, decimalDay); 
            // System.out.println("Vorheriger Datenpunkt: " + previousDataPoint);

            // Aus dem Vorherigen Datenpunkt Decimaltag und Wert auslesen 
            double previousDecimalDay = previousDataPoint.getDecimalDay(); 
            System.out.println("Dezimaltag des vorherigen Datenpunktes: " + previousDecimalDay);

            double previousValue = previousDataPoint.getValue(); 
            System.out.println("Leistung des vorherigen Datenpunktes: " + previousValue);

        }

        
        /*
        * =======================================================
        * BIS HIER KOPIEREN 
        */
        
    }
}
