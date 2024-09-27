import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class QuickLinkShortenerGUI extends JFrame {
    private JTextField longUrlField;
    private JTextField shortUrlField;
    private JTextArea resultArea;
    private HashMap<String, String> urlMap;
    private FileStorage fileStorage;

    public QuickLinkShortenerGUI() {
        urlMap = new HashMap<>();
        fileStorage = new FileStorage("url_data.txt");

        // Load saved data
        urlMap = fileStorage.loadData();

        // Set up the frame
        setTitle("QuickLink Shortener");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create UI elements
        JLabel longUrlLabel = new JLabel("Enter Long URL:");
        longUrlField = new JTextField(20);
        JButton shortenButton = new JButton("Shorten URL");

        JLabel shortUrlLabel = new JLabel("Enter Short URL:");
        shortUrlField = new JTextField(20);
        JButton retrieveButton = new JButton("Retrieve Original URL");

        resultArea = new JTextArea(5, 30);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // Add action listeners
        shortenButton.addActionListener(new ShortenButtonListener());
        retrieveButton.addActionListener(new RetrieveButtonListener());

        // Set up layout
        JPanel panel = new JPanel();
        panel.add(longUrlLabel);
        panel.add(longUrlField);
        panel.add(shortenButton);
        panel.add(shortUrlLabel);
        panel.add(shortUrlField);
        panel.add(retrieveButton);
        panel.add(scrollPane);

        add(panel);

        // Display the frame
        setVisible(true);
    }

    // ActionListener for Shorten URL button
    private class ShortenButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String longUrl = longUrlField.getText();
            if (!longUrl.isEmpty()) {
                if (!urlMap.containsValue(longUrl)) {
                    String shortUrl = generateShortUrl();
                    urlMap.put(shortUrl, longUrl);
                    resultArea.setText("Shortened URL: " + shortUrl);
                    longUrlField.setText("");
                } else {
                    resultArea.setText("This URL has already been shortened.");
                }
            } else {
                resultArea.setText("Please enter a valid URL.");
            }
        }
    }

    // ActionListener for Retrieve URL button
    private class RetrieveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String shortUrl = shortUrlField.getText();
            if (urlMap.containsKey(shortUrl)) {
                resultArea.setText("Original URL: " + urlMap.get(shortUrl));
                shortUrlField.setText("");
            } else {
                resultArea.setText("Short URL not found.");
            }
        }
    }

    // Generate a unique short URL using UUID
    private String generateShortUrl() {
        return java.util.UUID.randomUUID().toString().substring(0, 8);  // Shorten UUID to 8 characters
    }

    public static void main(String[] args) {
        new QuickLinkShortenerGUI();
    }
}
