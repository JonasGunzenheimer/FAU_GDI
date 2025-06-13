public class Quilt {
    public static void main(String[] args) {

        /* 
         * 1. Vorbereitung 
            -> Anzahl der verschiedneen Farben einlesn [x]
            -> maximal verfügbare Anzahl von jeder Farbe einlesen [x]
            -> höhe des Quilts auslesen (1. Dimension) [x]
            -> breite des Quilts auslesen (2. Dimension) [x]
            -> leeres Quilt erstellen (leeres Array) [x]

        * 2. Zufallsfarbe 
            -> Methode anlegen 
            -> Array mit Farben übergeben 
            -> Zufällige farbe zurückgeben, welche noch verrfügbare quadrate hat 
         */

        // Neue Instanz von Manager erstellen 

        Manager quiltData = new Manager(args);

        // Farben auslesen 
        int[] initialColorData = quiltData.getColorCount();
        // System.out.println("color Data:" + initialColorData); // Test ob das gwünschte Objekt zurück gegeben wird
        // System.out.println("Anzahl an verschiedenen Farben: " + initialColorData.length);
        int i = 1;
        for (int color : initialColorData) {
            // System.out.println("Farbe Nr. " + i + " -> Verfuegbare Anzahl: " + color);
            i++;
        }

        // Höhe und Breite auslesen 
        int height = quiltData.getHeight();
        int width = quiltData.getWidth();
        // System.out.println("Hoehe: " + height + ", Breite: " + width );

        // leeres Quilt erstellen 
        int[][] quilt = new int[height][width]; 
        
        Quilt.showQuilt(quilt);
        Quilt.initRandom(quilt, initialColorData);
        System.out.println("Quilt wurde erstellt!");
        Quilt.showQuilt(quilt);

        // ! Testen über Manager Klasse 
        Manager randomQuilt = new Manager(args);
        System.out.println("Status: " + randomQuilt.testRandomQuilt(quilt));
    }

    public static void showQuilt(int[][] quilt) {
        // quilt anzeigen
        System.out.println();
        System.out.println("________");
        System.out.println("QUILT");
        System.out.println();
        for (int[] row : quilt) {
            for (int color : row) {
                if (color < 10) { // kein printf? 
                    System.out.print("[ " + color + "]"); // einstellige Zahlen mit Leerzeichen
                } else {
                    System.out.print("[" + color + "]"); // zweistellige Zahlen normal
                }
            }
            System.out.println();
        }
    }

    public static int randomColor(int[] colors) {

        // Zuerst prüfen welche Farben noch verfügbar sind 
        int validColorsSize = 0; 
        
        // wie groß ist das Array mit verfügbaren Farben? 
        for (int i = 0; i < colors.length; i++) {
            if (colors[i] > 0) {
                validColorsSize++; 
            }
        }
        // System.out.println("Anzahl der verfuegbaren Farben: " + validColorsSize);
        
        int[] validColors = new int[validColorsSize]; 

        // Verfügbare Farben in Array schreiben 
        
        int j = 0; 
        // System.out.print("Verfuegbare Farben: [");
        for (int i = 0; i < colors.length; i++) {
            if (colors[i] > 0) {
                validColors[j] = i; 
                // System.out.print(validColors[j] + " ");
                j++; 
            }
        }
        // System.out.println("]");

        int randi = (int) (Math.random() * (validColors.length));

        // Aus einer der verfügbaren Farben auswählne und diese zurückgeben 
        return validColors[randi]; 
    }

    public static void initRandom(int[][] quilt, int[] initialColorData) {

        // Kopie des Farb Arrays 
        int[] colors = initialColorData; 
        System.out.print("Farben Ausgangszustand: [");
        int colorSum = 0; 
        for (int color : colors) {
            System.out.print(color + " ");
            colorSum = colorSum + color; 
        }
        System.out.println("]");
        System.out.println("Anzhal an verfuegbaren Karten zu Beginn: " + colorSum);

        // quilt durchgehen und jedem platz eine verfügbare farbe zuteilen 
        for (int row = 0; row < quilt.length; row++) {
            for (int collum = 0; collum < quilt[row].length; collum++) {
                // Random Color holen und an die stelle im quilt einsetzten 
                int randColor = Quilt.randomColor(colors); 
                quilt[row][collum] = randColor; 

                // von der eingesetzten farbe -1 
                colors[randColor] = colors[randColor] - 1; 
                System.out.println("Gewaehlte Farbe: Nr. " + randColor);
                System.out.print("Verfuegbare farben: [");
                for (int color : colors) {
                    System.out.print(color + " ");
                }
                System.out.println("]");

            }
        }
        System.out.print("Farben Endzustand: [");
        colorSum = 0; 
        for (int color : colors) {
            System.out.print(color + " ");
            colorSum = colorSum + color;
        }
        System.out.println("]");
        System.out.println("Anzhal an verfuegbaren Karten zu Ende: " + colorSum);


    }
}