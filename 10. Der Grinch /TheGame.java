public class TheGame {
    public static void main(String[] args) {

    }

    class GrinchGame implements WorldProvider, GameEventHandler {

        final private SnowWorld snowWorld; // # Speicheradresse für SnowWorld-Instanz

        public GrinchGame(String[] args) { // ! Konstruktor 
            this.snowWorld = new SnowWorld(args, this); // * snowWorld erwartet als DatenTyp GameEnventHandler. Geht, da GameEventHanderl in GrinchGame implementiert ist! 
        }

        @Override
        public SnowWorld getSnowWorld() {
            if (this.snowWorld != null) { // # Erst checken ob SnowWorld Instanz korrekt erstellt wurde 
                return this.snowWorld;
            } else {
                return null;
            }
        }

        @Override // * Für mich als Hinweis "Das ist eine zu einem interface gehörende Methode!"
        public void onClick(int column, int row) {
            if (this.snowWorld.patchOnTile(column, row) == null) {
                this.snowWorld.createSnowPatchAt(column, row);
            }
        }
    }

}