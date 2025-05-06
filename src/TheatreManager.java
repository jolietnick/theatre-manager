
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TheatreManager extends Thread {
    private List<Theatre> theatres;

    public TheatreManager() {
        this.theatres = new ArrayList<>();
    }

    public void loadTheatres(String filePath) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(filePath));

        while (true) {
            String line = br.readLine();
            if (line == null)
                break;
            String[] array = line.split(",");
            if (array.length < 3)
                continue;
            Theatre t = new Theatre(array[0], array[1], array[2]);
            theatres.add(t);
        }

        br.close();
    }

    public void loadShows(String filePath) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(filePath));

        while (true) {
            String line = br.readLine();
            if (line == null)
                break;
            String[] array = line.split(",");
            if (array.length < 6)
                continue;
            String theatreId = array[0];
            String title = array[1];
            String genre = array[2];
            double price = Double.parseDouble(array[3]);
            String dateOfShow = array[4];
            int maxTickets = Integer.parseInt(array[5]);

            Theatre theatre = findTheatreById(theatreId);
            if (theatre != null) {
                Show s = new Show(title, genre, price, dateOfShow, maxTickets);
                theatre.addShow(s);
            }
        }

        br.close();
    }

    public void writeTheatreToFile(String filePath, Theatre theatre) throws IOException {

        List<String> lines = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }
        br.close();

        boolean found = false;
        for (int i = 0; i < lines.size(); i++) {
            String[] array = lines.get(i).split(",");
            if (array[0].equals(theatre.getId())) {

                lines.set(i, theatre.getId() + "," + theatre.getName() + "," + theatre.getCity());
                found = true;
                break;
            }
        }

        if (!found) {
            lines.add(theatre.getId() + "," + theatre.getName() + "," + theatre.getCity());
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
        for (String l : lines) {
            bw.write(l);
            bw.newLine();
        }
        bw.close();
    }

    public void writeShowToFile(String filePath, Show show, String theatreId) throws IOException {

        List<String> lines = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }
        br.close();

        boolean theatreExists = false;
        for (String existingLine : lines) {
            String[] array = existingLine.split(",");
            if (array[0].equals(theatreId)) {
                theatreExists = true;
                break;
            }
        }

        if (theatreExists) {

            lines.add(theatreId + "," + show.getTitle() + "," + show.getGenre() + "," +
                    show.getPrice() + "," + show.getDateOfShow() + "," + show.getMaxTickets());
        } else {
            System.out.println("Errore: Il teatro con ID " + theatreId + " non esiste.");
            return;
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
        for (String l : lines) {
            bw.write(l);
            bw.newLine();
        }
        bw.close();
    }

    public Theatre findTheatreById(String theatreId) {
        for (Theatre theatre : theatres) {
            if (theatre.getId().equalsIgnoreCase(theatreId)) {
                return theatre;
            }
        }
        return null;
    }

    public void listAllTheatres() {
        for (Theatre theatre : theatres) {
            System.out.println("Teatro: " + theatre);
        }
    }

    public List<Theatre> getTheatres() {
        return theatres;
    }

    @Override
    public void run() {
        try {
            loadTheatres("data/theatres.csv");
            loadShows("data/shows.csv");
        } catch (Exception e) {

            e.printStackTrace();
        }

    }
}
