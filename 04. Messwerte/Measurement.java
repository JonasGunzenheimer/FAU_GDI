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
        * AB HIER KOPIEREN 
        * ==============================================================================================================
        */

        Measurement.createDecimalDays(args);
        Measurement.visualizeTheoreticalPower(args);
        Measurement.getSolarDataPoints(args);
        Measurement.visualizeActualPower(args);

    }

    //! Globale Variablen:
    // Anlegen eines Arrays für die decimalDays. Pro Stunde 3 einheiten eines Decimaltages. 00, 20, 40 => 24 * 3 
    public static double[] decimalDays = new double[72];

    public static void createDecimalDays(String[] args) {

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

                double currentDecimalDay = dayOfYear + (HourOfDay + MinuteOfHour / 60.0) / 24.0
                ;
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

    public static Object[][] dataPoints = new Object[73][2]; // 73 ist maximalgröße, immer 2 Werte! 

    public static void getSolarDataPoints(String[] args) {

        // Alle Datenpunkte in ein ARray laden um besser damit rechnen zu können
        // An sich müssen für alle Messerwrte (auser letzten) die Folowing nicht gelsen werden. Die Previous des näöchsten sind die Following des letzten 

        //! erster Previous wert auslesen und eintragen! 

        DataPoint currentDataPoint = null;

        currentDataPoint = SunCalculator.getPrevious(SunCalculator.SOLAR, decimalDays[0]);
        // System.out.println("Derzeitiger Datenpunkt: " + currentDataPoint);

        dataPoints[0] = new Object[] { currentDataPoint.getDecimalDay(), currentDataPoint.getValue() };

        // System.out.println("Tatsächlicher Dezimaltag: " + (double) dataPoints[0][0]);
        // System.out.println("Tatsächlicher Wert: " + (double) dataPoints[0][1]);

        int entryNumber = 1;
        for (double decimalDay : decimalDays) {
            // Zeigt den vorherigen (bereits geladenen) Datenpunkt
            // System.out.println("Dezimaltag im Datenpunkt: " + (double) dataPoints[entryNumber - 1][0]); 

            if (decimalDay < (double) dataPoints[entryNumber - 1][0]) {
                // System.out.println("Dezimaltag wird uebersprungen! ");
                continue;
            }

            currentDataPoint = SunCalculator.getFollowing(SunCalculator.SOLAR, decimalDay);
            dataPoints[entryNumber] = new Object[] { currentDataPoint.getDecimalDay(), currentDataPoint.getValue() };

            entryNumber++;
        }

        // * Komplettes Array anzeigen:
        // System.out.print("DataPoints: [");
        // for (int i = 0; i < entryNumber; i++) {
        //     System.out.print(dataPoints[i]);
        //     if (i < entryNumber - 1) {
        //         System.out.print(", ");
        //     }
        // }

        // System.out.println("]");
        // System.out.println("Anzahl der Datenpunkten: " + entryNumber);

        //! Anschließend können mit einer weiteren Methode die im Array gespeicherten Datenpunkte ausgelesen und verarbeitet (interpolation) werden. Die vizualizeActualPower MEthode kümmert sich dann nur noch ium das senden 
    }

    public static void visualizeActualPower(String[] args) {

        // Immer die beiden neben einander liegenden Werte auslesen und für die rechnung nutzen 
        // Value auslesen und Decimaltag auslsen 
        // interpolationsfaktor berechenen 
        // Über interpolationsfaktor die eigentlichen Messwerte ausrechenen 

        int counter = 0;
        int entryNumber = 0;
        int decimalDayNumber = 0;

        for (double decimalDay : decimalDays) {

            // System.out.println("Anzhal der Schleifendurchlaeufe: " + counter);
            double t = decimalDay;
            double t_0 = (double) dataPoints[entryNumber][0];
            double t_1 = (double) dataPoints[entryNumber + 1][0];

            if (t_0 < 0 || t_1 < 0) {
                // System.out.println("Ungeültigen Wert erkannt! t_0 = " + t_0 + ". t_1 = " + t_1 + ".");
                counter++; 
                continue; 
            }

            if (t > t_0 && t >= t_1) {
                entryNumber++;
                t_0 = (double) dataPoints[entryNumber][0];
                t_1 = (double) dataPoints[entryNumber + 1][0];
            }

            // System.out.println("t_0 = " + t_0);
            // System.out.println("t = " + t);
            // System.out.println("t_1= " + t_1);

            double interpolationFactor = (t - t_0) / (t_1 - t_0);
            // System.out.println("Interpolationsfaktor: " + interpolationFactor);

            double y_0 = (double) dataPoints[entryNumber][1];
            double y_1 = (double) dataPoints[entryNumber + 1][1];

            double interpolatedValue = (y_1 - y_0) * interpolationFactor + y_0;
            // System.out.println("Interpolierter Datenwert: " + interpolatedValue);

            ResultPrinter.sendSolarProduction(t, interpolatedValue);
            // System.out.println("Wert gesendet! ");
            counter++;
        }
        /*
        * ==============================================================================================================
        * BIS HIER KOPIEREN 
        */

    }
}
