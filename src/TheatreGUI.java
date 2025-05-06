
// TheatreGUI.java – interfaccia completa

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class TheatreGUI extends JFrame {
    private TheatreManager manager;
    private JTable showTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> theatreBox;
    private JComboBox<String> genreFilter;
    private JTextField priceFilter;
    private JLabel earningsLabel;

    public TheatreGUI(TheatreManager manager) {
        this.manager = manager;
        setTitle("Theatre Manager");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top controls
        JPanel topPanel = new JPanel();

        theatreBox = new JComboBox<>();
        for (Theatre t : manager.getTheatres())
            theatreBox.addItem(t.getId() + " - " + t.getName());

        JButton loadBtn = new JButton("Load Shows");
        loadBtn.addActionListener(e -> loadShows());

        JButton buyBtn = new JButton("Buy Ticket");
        buyBtn.addActionListener(e -> buyTicket());

        JButton addShowBtn = new JButton("Add Show");
        addShowBtn.addActionListener(e -> addShow());

        JButton addTheatreBtn = new JButton("Add Theatre");
        addTheatreBtn.addActionListener(e -> addTheatre());

        JButton allShowsBtn = new JButton("All Shows");
        allShowsBtn.addActionListener(e -> showAllShows());
        topPanel.add(allShowsBtn);

        genreFilter = new JComboBox<>(new String[] { "All", "Concert", "Opera", "Drama", "Musical" });
        priceFilter = new JTextField(5);
        JButton filterBtn = new JButton("Filter");
        filterBtn.addActionListener(e -> filterShows());

        topPanel.add(theatreBox);
        topPanel.add(loadBtn);
        topPanel.add(buyBtn);
        topPanel.add(addShowBtn);
        topPanel.add(addTheatreBtn);
        topPanel.add(new JLabel("Genre:"));
        topPanel.add(genreFilter);
        topPanel.add(new JLabel("Max Price:"));
        topPanel.add(priceFilter);
        topPanel.add(filterBtn);

        add(topPanel, BorderLayout.NORTH);

        // Table
        tableModel = new DefaultTableModel(new String[] { "Theatre", "Title", "Genre", "Price", "Date", "Available" },
                0);

        showTable = new JTable(tableModel);
        add(new JScrollPane(showTable), BorderLayout.CENTER);

        earningsLabel = new JLabel("Select a theatre to view earnings.");
        add(earningsLabel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private Theatre getSelectedTheatre() {
        int idx = theatreBox.getSelectedIndex();
        return idx >= 0 ? manager.getTheatres().get(idx) : null;
    }

    private void loadShows() {
        Theatre t = getSelectedTheatre();
        if (t == null)
            return;
        tableModel.setRowCount(0);
        for (Show s : t.getShows()) {
            tableModel.addRow(new Object[] {
                    s.getTitle(), s.getGenre(), s.getPrice(),
                    s.getDateOfShow().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), s.getAvailableTickets()
            });
        }
        earningsLabel.setText("Earnings: €" + String.format("%.2f", t.getTotalEarnings()));
    }

    private void buyTicket() {
        int row = showTable.getSelectedRow();
        if (row == -1)
            return;
        String title = (String) tableModel.getValueAt(row, 0);
        Theatre t = getSelectedTheatre();
        if (t.sellTicket(title)) {
            JOptionPane.showMessageDialog(this, "Ticket bought for " + title);
            loadShows();
        } else {
            JOptionPane.showMessageDialog(this, "No tickets available for " + title);
        }
    }

    private void filterShows() {
        Theatre t = getSelectedTheatre();
        if (t == null)
            return;
        String genre = genreFilter.getSelectedItem().toString();
        double maxPrice = Double.MAX_VALUE;
        try {
            if (!priceFilter.getText().isEmpty())
                maxPrice = Double.parseDouble(priceFilter.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid price");
            return;
        }

        tableModel.setRowCount(0);
        for (Show s : t.getShows()) {
            if ((genre.equals("All") || s.getGenre().equalsIgnoreCase(genre)) && s.getPrice() <= maxPrice) {
                tableModel.addRow(new Object[] {
                        s.getTitle(), s.getGenre(), s.getPrice(),
                        s.getDateOfShow().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                        s.getAvailableTickets()
                });
            }
        }
    }

    private void showAllShows() {
        tableModel.setRowCount(0);
        for (Theatre t : manager.getTheatres()) {
            for (Show s : t.getShows()) {
                tableModel.addRow(new Object[] {
                        t.getName(), // nome del teatro
                        s.getTitle(),
                        s.getGenre(),
                        s.getPrice(),
                        s.getDateOfShow().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                        s.getAvailableTickets()
                });
            }
        }
        earningsLabel.setText("Showing all shows from all theatres");
    }

    private void addShow() {
        JTextField title = new JTextField();
        JTextField genre = new JTextField();
        JTextField price = new JTextField();
        JTextField date = new JTextField();
        JTextField avail = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Title:"));
        panel.add(title);
        panel.add(new JLabel("Genre:"));
        panel.add(genre);
        panel.add(new JLabel("Price:"));
        panel.add(price);
        panel.add(new JLabel("Date (yyyy-MM-ddTHH:mm):"));
        panel.add(date);
        panel.add(new JLabel("Available Tickets:"));
        panel.add(avail);

        int res = JOptionPane.showConfirmDialog(this, panel, "Add Show", JOptionPane.OK_CANCEL_OPTION);
        if (res == JOptionPane.OK_OPTION) {
            try {
                Show s = new Show(title.getText(), genre.getText(), Double.parseDouble(price.getText()),
                        date.getText(), Integer.parseInt(avail.getText()));
                manager.writeShowToFile("data/shows.csv", s, getSelectedTheatre().getId());
                manager.loadShows("data/shows.csv");
                loadShows();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }

    private void addTheatre() {
        JTextField id = new JTextField();
        JTextField name = new JTextField();
        JTextField city = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("ID:"));
        panel.add(id);
        panel.add(new JLabel("Name:"));
        panel.add(name);
        panel.add(new JLabel("City:"));
        panel.add(city);

        int res = JOptionPane.showConfirmDialog(this, panel, "Add Theatre", JOptionPane.OK_CANCEL_OPTION);
        if (res == JOptionPane.OK_OPTION) {
            try {
                Theatre t = new Theatre(id.getText(), name.getText(), city.getText());
                manager.writeTheatreToFile("data/theatres.csv", t);
                manager.loadTheatres("data/theatres.csv");
                theatreBox.addItem(t.getId() + " - " + t.getName());
                JOptionPane.showMessageDialog(this, "Theatre added");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }
}
