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

    public static double getInterpolated(int type, double decimalDay) { 

        DataPoint previousDataPoint = SunCalculator.getPrevious(type, decimalDay);
        // System.out.println("Vorheriger DataPoint:" + previousDataPoint);
        DataPoint followingDataPoint = SunCalculator.getFollowing(type, decimalDay);
        // System.out.println("Nachfolgender DataPoint:" + followingDataPoint);

        double t = decimalDay; 
        double previous_t_0 = previousDataPoint.getDecimalDay(); 
        double previous_t_1 = followingDataPoint.getDecimalDay();

        double lambda = SolarEnergy.calculateFactor(t, previous_t_0, previous_t_1); 

        double previous_y_0 = previousDataPoint.getValue();
        double previous_y_1 = followingDataPoint.getValue();

        double interpolatedValue = SolarEnergy.interpolate(lambda, previous_y_0, previous_y_1); 

        return interpolatedValue; 
    }

    public static double calculateColorAngle(double netPower, double minUsage, double solarPower) {

        if (netPower = minUsage) {
            return 360.0; 
        }
        if (netPower = solarPower) {
            return 120.0; 
        }

        double normalized = (netPower - minUsage) / (solarPower - minUsage);

        double colorAngle = 360 - normalized * 240;

        return colorAngle;
    }

    public static void main(String[] args) {

        /* 
         * ==============================================================================================================
         * DEZIMALTAG BERECHENEN 
         * ==============================================================================================================
         */

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

                /* 
                * ===========================================================================================
                * INTERPOLIERTEN WERT BERECHNEN
                * ===========================================================================================
                */
                double interpolatedSolarValue =  SolarEnergy.getInterpolated(SunCalculator.SOLAR, currentDecimalDay); 
                System.out.println("Interpolierter Solar Wert: " + interpolatedSolarValue);
                ResultPrinter.sendSolarProduction(currentDecimalDay, interpolatedSolarValue); 
                double interpolatedUsageValue =  SolarEnergy.getInterpolated(SunCalculator.USAGE, currentDecimalDay); 
                System.out.println("Interpolierter Usage Wert: " + interpolatedUsageValue);
                ResultPrinter.sendUsage(currentDecimalDay, interpolatedUsageValue); 

                
                /* 
                * ===========================================================================================
                * NET POWER BERECHNEN UND SENDEN 
                * ===========================================================================================
                */
                
                double netPower = interpolatedSolarValue - interpolatedUsageValue; 
                double colorAngle = SolarEnergy.calculateColorAngle(netPower, -1 * interpolatedUsageValue , interpolatedSolarValue ); 
                ResultPrinter.sendNetPower(currentDecimalDay, netPower, colorAngle); 


                
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

    }
}