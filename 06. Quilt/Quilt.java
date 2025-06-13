public class Quilt {
    public static void main(String[] args) {

        // Neue Instanz von Manager erstellen 

        Manager quiltData = new Manager(args);

        // Farben auslesen 
        int[] colorData = quiltData.getColorCount();
        // System.out.println("color Data:" + colorData); // Test ob das gwünschte Objekt zurück gegeben wird
        // System.out.println("Anzahl an verschiedenen Farben: " + colorData.length);
        int i = 1;
        for (int color : colorData) {
            // System.out.println("Farbe Nr. " + i + " -> Verfuegbare Anzahl: " + color);
            i++;
        }

        // Höhe und Breite auslesen 
        int height = quiltData.getHeight();
        int width = quiltData.getWidth();
        // System.out.println("Hoehe: " + height + ", Breite: " + width );

        // leeres Quilt erstellen 
        int[][] quilt = new int[height][width]; 
        
        // Quilt.showQuilt(quilt);
        Quilt.initRandom(quilt, colorData);
        // System.out.println("Quilt wurde erstellt!");
        
        // ! Testen über Manager Klasse 
        quiltData.testRandomQuilt(quilt);
        
        Quilt.fixQuilt(quilt, colorData);
        System.out.println("Anzahl der Durchlaufe: " + durchlaeufe);
        Quilt.showQuilt(quilt);
        quiltData.testFinalQuilt(quilt); 
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

    public static void initRandom(int[][] quilt, int[] colors) {

        // System.out.print("Farben Ausgangszustand: [");
        int colorSum = 0; 
        for (int color : colors) {
            // System.out.print(color + " ");
            colorSum = colorSum + color; 
        }
        // System.out.println("]");
        // System.out.println("Anzhal an verfuegbaren Karten zu Beginn: " + colorSum);

        // quilt durchgehen und jedem platz eine verfügbare farbe zuteilen 
        for (int row = 0; row < quilt.length; row++) {
            for (int collum = 0; collum < quilt[row].length; collum++) {
                // Random Color holen und an die stelle im quilt einsetzten 
                int randColor = Quilt.randomColor(colors); 
                quilt[row][collum] = randColor; 

                // von der eingesetzten farbe -1 
                colors[randColor] = colors[randColor] - 1; 
                // System.out.println("Gewaehlte Farbe: Nr. " + randColor);
                // System.out.print("Verfuegbare farben: [");
                for (int color : colors) {
                    // System.out.print(color + " ");
                }
                // System.out.println("]");

            }
        }
        // System.out.print("Farben Endzustand: [");
        colorSum = 0; 
        for (int color : colors) {
            // System.out.print(color + " ");
            colorSum = colorSum + color;
        }
        // System.out.println("]");
        // System.out.println("Anzhal an verfuegbaren Karten zu Ende: " + colorSum);
    }

    public static int durchlaeufe = 0; 

    public static void fixQuilt(int[][] quilt, int[] colors) {
        boolean hasConflicts = true;
        while (hasConflicts) {
            durchlaeufe++; 
            hasConflicts = false;
            for (int i = 0; i < quilt.length; i++) {
                for (int j = 0; j < quilt[i].length; j++) {
                    if (!fixPatch(quilt, colors, i, j)) {
                        hasConflicts = true;
                    }
                }
            }
        }
    }

    public static boolean fixPatch(int[][] quilt, int[] colors, int row, int collum) {
        
        // Nachbarn herausfinden 
        int[] neighbors = Manager.getNeighbors(quilt, row, collum); 
        // Quilt.printArray("Nachbarfelder", neighbors);

        // Prüfen ob eines der Nachbarfelder gleich dem Wert im Quilt ist 
        for (int value : neighbors) {
            if (value == quilt[row][collum]) {
                // System.out.println("Feld wird ausgetauscht!");

                // Quilt.printArray("Farben: ", colors);
                // Auszutauschende Farbe wieder in das colors array hinzufügen 
                colors[quilt[row][collum]] = colors[quilt[row][collum]] +1; 
                // Quilt.printArray("Farben: ", colors);
                // Neue Zahl eintragen und recursiv testen 
                int randColor = Quilt.randomColor(colors);
                quilt[row][collum] = randColor;
                
                // von der eingesetzten farbe -1 
                colors[randColor] = colors[randColor] - 1;
                // System.out.println("Gewaehlte Farbe: Nr. " + randColor);
                // Quilt.printArray("Farben: ", colors);
                
                return false; 
            }
        }

        return true; 
    }

    /* 
     * HELPERS
     */

     public static void printArray(String label, int[] array) {
        System.out.print(label + ": ");
        printArray(array);
    }

    public static void printArray(int[] array) {
        System.out.print("[");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]);
            if (i < array.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
}