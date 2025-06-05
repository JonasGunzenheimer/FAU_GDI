/* 
* Ziel: Vergleich der Theoretisch errechneten Leistung mit einer tatsächlich gemessenen Leistung. Anschließende Visualisierung in einem Data Graph 
 * 1. Visualisieren der theoretischen Nutzleistung 
 * 1.1. Auslesen des gewünschten Tages mit DateParser (getDayofYear)
 * 1.2. Berechenen des Dezimaltages für einen beliebigen Zeitpunkt t 
 * 1.3. Berechnen der theoretischen Leistung mit getPower
 * 1.4. Senden an den Graphen mit mit sendPower 
 * 
 */

public class Measurement {
    public static void main(String[] args) {
        final int DELTA_MINUTE = 20;
        // 1. Visualisieren der tehoretischen Nutzleistung 
        Measurement.powerToGraph(args);

    }

    public static void powerToGraph(String[] args) {

        int HourOfDay = 0;
        int MinuteOfHour = 0;

        double decimalDay;
        // Erstellen eines neues Objekts über das dann die Methoden der DateParser Klasse aufgerufen werden können 
        DateParser date = new DateParser(args);
        PanelData power = new PanelData(args);

        // 1.1. Auslesen des gewünschten Tages mit DateParser (getDayofYear)

        double dayOfYear = date.getDayOfYear();
        System.out.println("Day of Year: " + dayOfYear);

        // 1.2. Errechnen des Dezimaltage in 20 Minutenschritten 

        // Erte Schleife die die stunde hochzählt 
        for (int i = 0; i <= 24; i++) {

            // Zweite schliefe die die minuten hochzählt 
            for (int j = 0; j <= 3; j++) {

                decimalDay = dayOfYear + (HourOfDay + MinuteOfHour / 60.0) / 24.0;

                System.out.println("Dezimaltag: " + decimalDay);

                MinuteOfHour = MinuteOfHour + 20;

                double theoreticalPower = power.getPower(decimalDay);

                System.out.println("Theoretische Leistung: " + theoreticalPower);

                ResultPrinter.sendPower(decimalDay, theoreticalPower);
            }

            HourOfDay++;
            MinuteOfHour = 0;
        }

    }
}
