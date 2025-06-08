public class SolarEnergy {

    public static double[] decimalDays = new double[72];
    public static Object[][] dataPoints = new Object[73][2]; // 73 ist maximalgröße, immer 2 Werte! 

    public static double calculateFactor(double t, double t_0, double t_1) {
        double interpolationFactor = (t - t_0) / (t_1 - t_0);
        return interpolationFactor;
    }

    public static double interpolate(double lambda, double y_0, double y_1) {
        // System.out.println("Interpolationsfaktor: " + interpolationFactor);

        double interpolatedValue = (y_1 - y_0) * lambda + y_0;
        // System.out.println("Interpolierter Datenwert: " + interpolatedValue);

        return interpolatedValue; 
    }

    public static void main(String[] args) {
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
        int zaehlvariable = 0;

        // Erte Schleife die die stunde hochzählt 
        for (int i = 0; i < 24; i++) {

            // Zweite schliefe die die minuten hochzählt 
            for (int j = 0; j < 3; j++) {

                double currentDecimalDay = dayOfYear + (HourOfDay + MinuteOfHour / 60.0) / 24.0;
                // System.out.println("Neu berechneter Decimaltag: " + currentDecimalDay);

                // Speichern des DecimalDays in Array 
                decimalDays[zaehlvariable] = currentDecimalDay;
                MinuteOfHour = MinuteOfHour + 20;

                // System.out.println("Durchlauf Nr.: " + (entryNumber + 1));
                zaehlvariable++;

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

        DataPoint currentDataPoint = null;

        currentDataPoint = SunCalculator.getPrevious(SunCalculator.SOLAR, decimalDays[0]);
        // System.out.println("Derzeitiger Datenpunkt: " + currentDataPoint);

        dataPoints[0] = new Object[] { currentDataPoint.getDecimalDay(), currentDataPoint.getValue() };

        // System.out.println("Tatsächlicher Dezimaltag: " + (double) dataPoints[0][0]);
        // System.out.println("Tatsächlicher Wert: " + (double) dataPoints[0][1]);

        int DecimalDayEntryNumber = 1;
        for (double decimalDay : decimalDays) {
            // Zeigt den vorherigen (bereits geladenen) Datenpunkt
            // System.out.println("Dezimaltag im Datenpunkt: " + (double) dataPoints[entryNumber - 1][0]); 

            if (decimalDay < (double) dataPoints[DecimalDayEntryNumber - 1][0]) {
                // System.out.println("Dezimaltag wird uebersprungen! ");
                continue;
            }

            currentDataPoint = SunCalculator.getFollowing(SunCalculator.SOLAR, decimalDay);
            dataPoints[DecimalDayEntryNumber] = new Object[] { currentDataPoint.getDecimalDay(),
                    currentDataPoint.getValue() };

            DecimalDayEntryNumber++;
        }

        int counter = 0;
        int entryNumber = 0;
        int decimalDayNumber = 0;

        for (double decimalDay : decimalDays) {

            System.out.println("Anzhal der Schleifendurchlaeufe: " + counter);
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
            counter++;
        }
    }
}