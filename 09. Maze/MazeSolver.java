// KEINE IMPORTS für Path und Position - diese sind in der Aufgabe definiert!

public class MazeSolver {
    public static void main(String[] args) {
        Manager manager = new Manager(args);
        Position start = manager.getStartPosition();
        boolean[][] maze = manager.getSelectedMaze();
        Path path = findWay(maze, start);
        if (path == null || path.size() == 0) {
            System.out.println("@blue:No way found.");
        }
        manager.setPath(path);
    }

    public static Path findWay(boolean[][] maze, Position start) {

        //* 1. Neuen leeren Pfad erstellen */
        Path path = new Path();

        if (solveMaze(maze, start, path)) {
            return path; // ! Wenn solveMaze final <true> zurück gibt wurde der Ausgang gefunden! 
        } else {
            return null; // # ansonsten konnte kein Ausgang gefunden wernden 
        }
    }

    public static boolean solveMaze(boolean[][] maze, Position pos, Path path) {

        // * Null-Checks: Wurde maze übergeben? Wurde pos übergeben? Wurde path übergeben? 
        if (maze == null || pos == null || path == null) {
            return false;
        }

        // # Debugging 
        System.out.println("solveMaze wurde aufgerufen!");
        System.out.println("Position: " + pos.row + "/" + pos.column);

        // * Szenario 1: Übergebene Position ist der Ausgang! (liegt außerhalb der 2x2 Matrix) 
        if (pos.row < 0 || pos.row >= maze.length ||
                pos.column < 0 || pos.column >= maze[0].length) {
            return true;
        }

        // * Szenario 2: Übergebene Position ist eine Mauer (false) 
        if (maze[pos.row][pos.column]) {
            return false;
        }

        // * Szenario 3: Wir waren schon an der übergebenen Position! 
        if (path.contains(pos)) {
            return false;
        }

        // ! Wenn Szenario 1-3 nicht erfüllt sind müssen wir noch unterwegs sein
        // * Postion wird zum Pfad hinzugefügt 
        path.add(pos);

        // # Nachbarn erstellen 
        Position up = new Position(pos.row - 1, pos.column); // # Oberer Nachbar 
        Position down = new Position(pos.row + 1, pos.column); // # Unterer Nachbar 
        Position left = new Position(pos.row, pos.column - 1); // # Linker Nachbar 
        Position right = new Position(pos.row, pos.column + 1); // # Rechter Nachbar 

        // # alle Nachbarn nach einander testen 
        if (
            solveMaze(maze, left, path) ||
            solveMaze(maze, up, path) || // ! Solve Maze kann in zu diesem Zeitpunkt nur true zurück geben, wenn ein Ausgang gefunden wurde 
            solveMaze(maze, down, path) ||
            solveMaze(maze, right, path)) {
            return true; // * Wenn Ausgangn gefunden wurde, true zurückgeben 
        }

        // ! Wenn keiner der SolveMAze Aufrufe <true> zurück gibt, müssen wir eine Sackgasse erreicht haben, da alle Nachbarfelder Mauern (false) oder eine bereits beuschte Stelle (path.contains(pos)) sind
        // * wird als letzes überprüft, wenn sicher gestellt wurde, dass die Übergebene Position weder Mauer, noch Ausgang, noch bereits auf dem Pfad ist. 
        path.remove(pos); // # es wird so lange "zurück gelaufen" bis wieder ein valider Weg gefunden wird
        return false; // # Hier wird false zurück gegeben und die if-Schleife welche die Nachbarn prüft, testet den nächsten Kandidaten 
    }
}