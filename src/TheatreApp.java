
import javax.swing.SwingUtilities;

public class TheatreApp {
    public static void main(String[] args) {
        // Carica i dati (senza thread per semplicità)
        TheatreManager tm = new TheatreManager();
        try {
            tm.loadTheatres("data/theatres.csv");
            tm.loadShows("data/shows.csv");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        // Avvia la GUI
        SwingUtilities.invokeLater(() -> new TheatreGUI(tm));
    }
}
