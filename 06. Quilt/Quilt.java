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
        
        Quilt.showQuilt(quilt);
        int randomColor = Quilt.randomColor(colorData); 
        System.out.println("Erhaltene zufaellige Farbe: " + randomColor);
    }

    public static void showQuilt(int[][] quilt) {
        // quilt anzeigen 
        System.out.println();
        System.out.println("________");
        System.out.println("QUILT");
        System.out.println();
        for (int[] row : quilt) {
            for (int color : row) {
                System.out.print("[" + color + "]");
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
        System.out.print("Verfuegbare Farben: [");
        for (int i = 0; i < colors.length; i++) {
            if (colors[i] > 0) {
                validColors[j] = i + 1; 
                System.out.print(validColors[j] + " ");
                j++; 
            }
        }
        System.out.println("]");

        int randi = (int) (Math.random() * (validColors.length + 1));

        // Aus einer der verfügbaren Farben auswählne und diese zurückgeben 
        return validColors[randi]; 
    }
}