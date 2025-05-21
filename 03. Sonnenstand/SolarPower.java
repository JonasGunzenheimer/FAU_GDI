public class SolarPower {
    public static void main(String[] args) {
        final double EARTH_TILT_RAD = Math.toRadians(-23.44);

        /* Aufgabenstellung: 
        	Theoretisch nutzbare Leistung einer Solaranlage zu einem bestimmten Zeitpunkt und Ort bestimmen. 
        
        1. Einstelldaten der Solarpanele auslesen (DateParser)
        2. Dezimaltag t bestimmen (Angegebene Formel)
        3. Astronomische Position der Sonne ermitteln 
        	3.1 Deklination ermitteln (Formel)
            3.2 Ergebniss mit Result Printer überprüfen 
        4. maximale Leistung auslesen (Panel Data) 
        5. Nutzleistung berechenen 
        	5.1 Gewichtungsfaktor berechnen 
                5.1.1 Position der Sonne (SunCalculator) und Postition der Panele (PanelData) auslesen   
                5.1.2 DAten als 3D-Vector zurückgeben lassen 
        	5.2 Theoretische Nutzleistung berechnen (Formel) 
        
        */

        // 1. Breitengrad des Solarpanels Auslesen

        DateParser dateParser = new DateParser(args); // Ruft den Konstruktor von DateParser auf 
        double breitengrad = Math.toRadians(dateParser.getLatitude()); // Gibt den eingestellten Breitengrad zurück 

        System.out.println(breitengrad);

        // 2. Dezimaltag bestimmen 
        double dayOfYear = dateParser.getDayOfYear();
        System.out.println("DayOfYear: " + dayOfYear);
        double hourOfDay = dateParser.getHourOfDay();
        System.out.println("HourOfDay: " + hourOfDay);
        double minuteOfHour = dateParser.getMinuteOfHour();
        System.out.println("MinuteOfHour: " + minuteOfHour);

        double decimalday = Math.round((dayOfYear + (hourOfDay + (minuteOfHour / 60)) / (24)) * 1000.0) / 1000.0;

        System.out.println("Dezimaltag d: " + decimalday);

        // 3. Astronoimische Position der Sonne ermitteln 
        // 3.1 Deklination ermitteln (Formel) 

        double declination = Math
                .round(-23.44 * Math.cos(Math.toRadians((360.0 * (dayOfYear + 10.0)) / 365.0)) * 1000.0) / 1000.0;

        System.out.println("Deklination: " + declination);

        // 3.2 Ergebniss mit Result Printer überprüfen 
        ResultPrinter.sendDecimalDay(decimalday);
        ResultPrinter.sendDeclination(decimalday, Math.toDegrees(declination));
    }
}
