import java.util.List;
import java.util.ArrayList;

public class Theatre {
    private String id;
    private String name;
    private String city;
    private List<Show> shows;
    private double totalEarnings;

    public Theatre(String id, String name, String city) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.shows = new ArrayList<>();
        this.totalEarnings = 0.0;
    }

    public void addShow(Show show) {
        shows.add(show);
    }

    public void listShows() {
        if (shows.isEmpty()) {
            System.out.println("No shows available at " + name);
        } else {
            System.out.println("Shows at " + name + " (" + city + "):");
            for (Show s : shows) {
                System.out.println("  " + s);
            }
        }
    }

    public void searchShowsByPrice(double maxPrice) {
        boolean found = false;
        System.out.println("Shows with price lower than " + maxPrice + ":");
        for (Show s : shows) {
            if (s.getPrice() < maxPrice) {
                System.out.println(s);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No shows with price lower than " + maxPrice);
        }
    }

    public void searchShowsByGenre(String genre) {
        boolean found = false;
        System.out.println("Shows with genre " + genre + ":");
        for (Show s : shows) {
            if (s.getGenre().equalsIgnoreCase(genre)) {
                System.out.println(s);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No shows found with genre " + genre);
        }
    }

    public boolean sellTicket(String title) {
        for (Show s : shows) {
            if (s.getTitle().equalsIgnoreCase(title)) {
                if (s.sellTicket()) {
                    totalEarnings += s.getPrice();
                    return true;
                }
            }
        }
        return false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Show> getShows() {
        return shows;
    }

    public void setShows(List<Show> shows) {
        this.shows = shows;
    }

    public double getTotalEarnings() {
        return totalEarnings;
    }

    @Override
    public String toString() {
        return id + " - " + name + " (" + city + ") - Total Earnings: €" + String.format("%.2f", totalEarnings);
    }
}
