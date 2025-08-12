package library.search.engine;

import java.util.Comparator;
import java.util.Scanner;

public class LibraryMenu {
    private final Library library;
    private final UserInteractionLogger logger;
    private final LibrarySerializer serializer;

    public LibraryMenu(Library library, UserInteractionLogger logger, LibrarySerializer serializer) {
        this.library = library;
        this.logger = logger;
        this.serializer = serializer;
    }

    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            showMenuOptions();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> handleViewBooks();
                case 2 -> handleSortByTitle();
                case 3 -> handleSortByAuthor();
                case 4 -> handleSortByYear();
                case 5 -> handleSearch(scanner);
                case 6 -> { handleExit(); return; }
                default -> handleInvalidChoice(choice);
            }
        }
    }

    private void showMenuOptions() {
        System.out.println("\n=== Main Menu ===\n");
        System.out.println("1. View All Books");
        System.out.println("2. Sort Books by Title");
        System.out.println("3. Sort Books by Author");
        System.out.println("4. Sort Books by Year");
        System.out.println("5. Search for a Book by a keyword (Title, Author name, or Year)");
        System.out.println("6. Exit");
    }

    private void handleViewBooks() {
        library.viewAllBooks();
        logger.logViewAllBooks();
    }

    private void handleSortByTitle() {
        SortUtil.bubbleSort(library.getBooks(), Comparator.comparing(Book::getTitle));
        displaySortedBooks("title");
    }

    private void handleSortByAuthor() {
        SortUtil.insertionSort(library.getBooks(), Comparator.comparing(Book::getAuthor));
        displaySortedBooks("author");
    }

    private void handleSortByYear() {
        SortUtil.quickSort(library.getBooks(), Comparator.comparingInt(Book::getPublicationYear), 0, library.getBooks().size() - 1);
        displaySortedBooks("year");
    }

    private void displaySortedBooks(String sortType) {
        System.out.println("Books sorted by " + sortType + ":");
        library.viewAllBooks();
        logger.logSort(sortType);
    }

    private void handleSearch(Scanner scanner) {
        System.out.print("Enter a keyword (title, author name, or year): ");
        String keyword = scanner.nextLine();
        Book foundBook = library.searchBookByKeyword(keyword);
        if (foundBook != null) {
            System.out.println("Book found: " + foundBook);
            logger.logSearch(keyword);
        } else {
            System.out.println("Book not found.");
            logger.logSearch(keyword + " - not found");
        }
    }

    private void handleExit() {
        serializer.saveLibrary(library.getBooks(), "resources/data/library.ser");
        System.out.println("Library saved successfully. Exiting...");
        logger.log("Library saved successfully. Exiting...");
    }

    private void handleInvalidChoice(int choice) {
        System.out.println("Invalid choice.");
        logger.log("Invalid menu choice: " + choice);
    }
}
