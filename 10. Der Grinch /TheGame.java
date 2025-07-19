public class TheGame {
    public static void main(String[] args) {
        GrinchGame game = new GrinchGame(args);
    }

    class GrinchGame implements WorldProvider, GameEventHandler {

        final public SnowWorld snowWorld; // # Speicheradresse für SnowWorld-Instanz

        public GrinchGame(String[] args) { // ! Konstruktor 
            this.snowWorld = new SnowWorld(args, this); // * snowWorld erwartet als DatenTyp GameEnventHandler. Geht, da GameEventHanderl in GrinchGame implementiert ist! 
            Grinch grinch = new Grinch(this);
        }

        @Override
        public SnowWorld getSnowWorld() {
            if (this.snowWorld != null) { // # Erst checken ob SnowWorld Instanz korrekt erstellt wurde 
                return this.snowWorld;
            } else {
                return null;
            }
        }

        @Override // * Für mich als Hinﬁ´weis "Das ist eine zu einem interface gehörende Methode!"
        public void onClick(int column, int row) {
            if (this.snowWorld.patchOnTile(column, row) == null) {
                this.snowWorld.createSnowPatchAt(column, row);
            } else {
                this.melt(column, row, 0.0);
            }
        }

        public void melt(int column, int row, double wait) { // * ist für schmelzen des auf dem Feld liegenden Objektes verantwortlich 

            // # übergebene Position auf Gültigkeit prüfen 
            if (row < 0 || row >= this.snowWorld.rows || column < 0 || column >= this.snowWorld.columns) { // ! >= weil liste bei 0 beginnt 
                throw new IllegalArgumentException("Übergebenes Feld liegt aushalb der Welt!"); // ! Methode wird bei Error automatisch beendet! 
            }

            // * SnowPatch-Instanz auf dem aufgerufenen Feld holen 
            SnowPatch currentPatch = this.snowWorld.patchOnTile(column, row);

            // * Überprüfen ob bereits Schneefigur && nicht gerade schmelzend && beginnt nich tbald zu shcmelzen 
            if (currentPatch != null &&
                    currentPatch.isSnowFigure() &&
                    !currentPatch.willMeltSoon() &&
                    !currentPatch.isMelting()) {

                currentPatch.startMeltingIn(wait); // # Schneefigur schmilz in <wait> 
            }

            // * rekursiv umliegende Schneefiguren Schmelzen 
            // # Nachbarfelder 
            int[][] neighbors = {
                    { column, row - 1 }, // OBEN 
                    { column, row + 1 }, // UNTEN 
                    { column - 1, row }, // LINKS 
                    { column + 1, row }, // RECHTS
            };

            // * Falls eines der Nachbarfelder eine Schneefigur mit den richitgen Bedingungen enthält melt erneut auftufen. Erstellt dann wieder neue Nachbarn usw..... 

            double delay = wait + 0.2; // ! Delay muss für alle Nachbarn gleich sein

            for (int[] neighbor : neighbors) {

                int currentColumn = neighbor[0];
                int currentRow = neighbor[1];

                SnowPatch neighboringPacht = this.snowWorld.patchOnTile(currentColumn, currentRow);
                // # ist auf dem Feld eine Schneefigur? 
                if (neighboringPacht == null) { // ? klappt auch für Felder auserhalb der Welt ? 
                    continue;
                }

                if (neighboringPacht.isSnowFigure() &&
                        !neighboringPacht.willMeltSoon() &&
                        !neighboringPacht.isMelting()) {
                    this.melt(currentColumn, currentRow, delay);
                }
            }

        }
    }

    class Grinch implements FigureEventHandler {

        final GrinchGame currentGame;
        final ActingFigure actingFigure_1;

        public Grinch(GrinchGame currentGame) {
            this.currentGame = currentGame;
            this.actingFigure_1 = new ActingFigure(
                    this,
                    this.currentGame,
                    SnowWorld.AS_GRINCH);
            this.addActingFigureToWord(actingFigure_1);
        }

        public void addActingFigureToWord(ActingFigure currentActingFigure) {
            int[] randomPatch = getRandomPatch();
            currentActingFigure.addToTile(randomPatch[0], randomPatch[1]);
        }

        public int[] getRandomPatch() { // * nicht statisch, da sich der patch auf eine bestimmte Welt bezieht 
            int[] randomPatch = new int[2];
            randomPatch[0] = (int) (Math.random() * (currentGame.snowWorld.columns));
            randomPatch[1] = (int) (Math.random() * (currentGame.snowWorld.rows));
            return randomPatch; 
        }

        @Override
        public void didEnterTile(int column, int row) { // # Wird automatisch von der Spielwelt aufgerufen, wenn die Figur ein neues Feld betritt.
        }

        @Override
        public void didStopWalking(int column, int row) { // # Wird automatisch von der Spielwelt aufgerufen, wenn die Figur mit addToTile in die Spielwelt aufgenommen wurde oder das mit walkTo festgelegte Zielfeld erreicht hat.

        }
    }

}