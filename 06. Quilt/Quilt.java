public class Quilt {
    public static void main(String[] args) {

        /* 
         * 1. Vorbereitung 
            -> Anzahl der verschiedneen Farben einlesn [x]
            -> maximal verfügbare Anzahl von jeder Farbe einlesen [x]
            -> höhe des Quilts auslesen (1. Dimension) [x]
            -> breite des Quilts auslesen (2. Dimension) [x]
            -> leeres Quilt erstellen (leeres Array) [x]

        * 2. 
         */

        // Neue Instanz von Manager erstellen 

        Manager quiltData = new Manager(args);

        // Farben auslesen 
        int[] coulorData = quiltData.getColorCount();
        // System.out.println("Colour Data:" + coulorData); // Test ob das gwünschte Objekt zurück gegeben wird
        // System.out.println("Anzahl an verschiedenen Farben: " + coulorData.length);
        int i = 1;
        for (int colour : coulorData) {
            // System.out.println("Farbe Nr. " + i + " -> Verfuegbare Anzahl: " + colour);
            i++;
        }

        // Höhe und Breite auslesen 
        int height = quiltData.getHeight();
        int width = quiltData.getWidth();
        // System.out.println("Hoehe: " + height + ", Breite: " + width );

        // leeres Quilt erstellen 
        int[][] quilt = new int[height][width]; 
        
        Quilt.showQuilt(quilt);
    }

    public static void showQuilt(int[][] quilt) {
        // quilt anzeigen 
        System.out.println();
        System.out.println("________");
        System.out.println("QUILT");
        System.out.println();
        for (int[] row : quilt) {
            for (int colour : row) {
                System.out.print("[" + colour + "]");
            }
            System.out.println();
        }
    }
}