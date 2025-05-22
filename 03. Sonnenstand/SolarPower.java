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
        3.3 Position der Sonne auslesen lassen (SunCalculator)
        4. maximale Leistung auslesen (Panel Data) 
        5. Nutzleistung berechenen 
        5.1 Gewichtungsfaktor berechnen 
        5.1.1 Postition der Panele (PanelData) auslesen   
        5.1.2 DAten als 3D-Vector zurückgeben lassen 
        5.2 Theoretische Nutzleistung berechnen (Formel) 
        
        */

        // 1. Breitengrad des Solarpanels Auslesen

        DateParser dateParser = new DateParser(args); // Ruft den Konstruktor von DateParser auf 
        double latitude = Math.toRadians(dateParser.getLatitude()); // Gibt den eingestellten Breitengrad zurück 

        System.out.println("Breitengrad: " + latitude);

        // 2. Dezimaltag bestimmen 
        int dayOfYear = dateParser.getDayOfYear();
        System.out.println("DayOfYear: " + dayOfYear);
        int hourOfDay = dateParser.getHourOfDay();
        System.out.println("HourOfDay: " + hourOfDay);
        int minuteOfHour = dateParser.getMinuteOfHour();
        System.out.println("MinuteOfHour: " + minuteOfHour);

        double decimalDay = (dayOfYear + (hourOfDay + (minuteOfHour / 60.0)) / (24.0));

        System.out.println("Dezimaltag d: " + decimalDay);

        // 3. Astronoimische Position der Sonne ermitteln 
        // 3.1 Deklination ermitteln (Formel) 

        double declination = -23.44 * Math.cos(Math.toRadians((360.0 * (dayOfYear + 10.0)) / 365.0)); // ! Umwandlung in Radianten für den cos (Ergebnis dann aber in Grad)

        System.out.println("Deklination: " + declination + "°");

        // 3.2 Ergebniss mit Result Printer überprüfen 
        ResultPrinter.sendDecimalDay(decimalDay);
        ResultPrinter.sendDeclination(decimalDay, declination);

        // 3.3 Position der Sonne auslesen lassen (SunCalculator)  
        // ! Achtung: SunCalculator stellt zwar die Methode bereit, es wird aber ein Vec3D Object zurückgegeben 
        Vec3D sunPosition = SunCalculator.getPosition(latitude, hourOfDay, minuteOfHour, Math.toRadians(declination));
        System.out.println("Sonnenposition: " + sunPosition);

        // 4. Maximale Leistung auslesen (Panel Data)
        PanelData output = new PanelData(args);

        double voltage = output.getData(PanelData.VOLTAGE);
        System.out.println("Voltage: " + voltage);

        double current = output.getData(PanelData.CURRENT);
        System.out.println("Current: " + current);

        double power = output.getData(PanelData.POWER);
        System.out.println("Power: " + power);

        //  5. Nutzleistung berechenen 
        //	5.1 Gewichtungsfaktor 
        //  5.1.1 Position der Panele (PanelData) auslesen  

        PanelData panelState = new PanelData(args); // Methode in PanelData brauch ein Objekt 
        Vec3D panelPosition = panelState.getPosition(); // getPosition gibt aber ein Vec3D-Obejkt zurück (Datentyp richitg angeben)
        System.out.println("Panel Position [Vector]: " + panelPosition);

        // 5.1.2 Gewichtungsfaktor berechenen

        double weightingFactor = Math.max(0, panelPosition.scalarProduct(sunPosition)); // da Math.max immer die größere der beiden Zahlen ausgibt, wird bei 𝑤 < 0 "0" ausgegeben 
        System.out.println("Gewichtungsfaktor: " + weightingFactor);

        // 5.2 Theoretische Nutzleisung berechnen 
        double effectivePower = Math.round(weightingFactor * power);
        System.out.println("Nutzleistung [W]: " + effectivePower);

        ResultPrinter.sendPower(decimalDay, effectivePower);
    }
}