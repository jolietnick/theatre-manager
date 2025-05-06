
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Show {
    private String title;
    private String genre;
    private double price;
    private LocalDateTime dateOfShow;
    private int maxTickets;
    private int availableTickets;


    public static LocalDateTime convertFromString(String date) {
        date = date.trim().replace('T', ' ');
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(date, fmt);
    }

    public Show(String title, String genre, double price, String dateOfShow, int maxTickets) {
        this.title = title;
        this.genre = genre;
        this.price = price;
        this.dateOfShow = convertFromString(dateOfShow);
        this.maxTickets = maxTickets;
        this.availableTickets = maxTickets;
    }



    // Getter e Setter
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getMaxTickets() {
        return maxTickets;
    }

    public void setMaxTickets(int maxTickets) {
        this.maxTickets = maxTickets;
    }

    public LocalDateTime getDateOfShow() {
        return dateOfShow;
    }

    public void setDateOfShow(LocalDateTime dateOfShow) {
        this.dateOfShow = dateOfShow;
    }

    public int getAvailableTickets() {
        return availableTickets;
    }

    public boolean sellTicket() {
        if (availableTickets > 0) {
            availableTickets--;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Show [title=" + title + ", genre=" + genre + ", price=" + price + ", dateOfShow=" + dateOfShow
                + ", maxTickets=" + maxTickets + "]";
    }

}
