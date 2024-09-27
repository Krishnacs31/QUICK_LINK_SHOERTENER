import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;

public class QuickLinkShortener {
    private static HashMap<String, String> urlMap = new HashMap<>(); // Store short and long URLs
    private static FileStorage fileStorage = new FileStorage("url_data.txt");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        // Load URLs from file (optional file storage)
        urlMap = fileStorage.loadData();

        while (!exit) {
            System.out.println("1. Shorten URL");
            System.out.println("2. Retrieve Original URL");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter the long URL: ");
                    String longUrl = scanner.nextLine();
                    shortenURL(longUrl);
                    break;
                case 2:
                    System.out.print("Enter the short URL: ");
                    String shortUrl = scanner.nextLine();
                    retrieveOriginalURL(shortUrl);
                    break;
                case 3:
                    exit = true;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }

        // Save URLs to file (optional file storage)
        fileStorage.saveData(urlMap);
    }

    // Function to shorten URL
    public static void shortenURL(String longUrl) {
        if (!urlMap.containsValue(longUrl)) {
            String shortUrl = generateShortUrl();
            urlMap.put(shortUrl, longUrl);
            System.out.println("Shortened URL: " + shortUrl);
        } else {
            System.out.println("This URL has already been shortened.");
        }
    }

    // Function to retrieve the original URL
    public static void retrieveOriginalURL(String shortUrl) {
        if (urlMap.containsKey(shortUrl)) {
            System.out.println("Original URL: " + urlMap.get(shortUrl));
        } else {
            System.out.println("Short URL not found!");
        }
    }

    // Generate a unique short URL using UUID
    private static String generateShortUrl() {
        return UUID.randomUUID().toString().substring(0, 8);  // Shorten UUID to 8 characters
    }
}
