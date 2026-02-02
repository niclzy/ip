package gigglebytes;

import java.util.Scanner;

/**
 * Handles all user interface interactions for the GiggleBytes application.
 * <p>
 * This class is responsible for displaying messages to the user,
 * reading user input, and managing the command-line interface.
 * </p>
 */
public class Ui {
    private Scanner scanner;

    /**
     * Constructs a new Ui instance with a Scanner for user input.
     */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    /**
     * Displays the welcome message and application logo.
     * Also shows available commands to help the user get started.
     */
    public void showWelcome() {
        String logo = "  ________.__              .__        __________          __                 \n"
                + " /  _____/|__| ____   ____ |  |   ____\\______   \\___.__._/  |_  ____   ______\n"
                + "/   \\  ___|  |/ ___\\ / ___\\|  | _/ __ \\|    |  _<   |  |\\   __\\/ __ \\ /  ___/\n"
                + "\\    \\_\\  \\  / /_/  > /_/  >  |_\\  ___/|    |   \\\\___  | |  | \\  ___/ \\___ \\ \n"
                + " \\______  /__\\___  /\\___  /|____/\\___  >______  // ____| |__|  \\___  >____  >\n"
                + "        \\/  /_____//_____/           \\/       \\/ \\/                \\/     \\/ \n";

        System.out.println("------------------------------------------------------------------------------");
        System.out.println(logo);
        System.out.println("------------------------------------------------------------------------------");
        System.out.println("Hello! o/ I'm GiggleBytes! Your personal digital task manager!");
        System.out.println("I can help you track and complete your tasks! >.<");
        System.out.println("------------------------------------------------------------------------------");
        showHelp();
    }

    /**
     * Displays a help message showing all available commands.
     */
    public void showHelp() {
        System.out.println("Available Commands (Non-Case Sensitive) : >.<");
        System.out.println("  - Add Todo: 'todo [description]'");
        System.out.println("  - Add Deadline: 'deadline [description] /by [date/time]'");
        System.out.println("  - Add Event: 'event [description] /from [start] /to [end]'");
        System.out.println("  - List tasks: Type 'list'");
        System.out.println("  - Find tasks: Type 'find [keyword]'");
        System.out.println("  - Mark task as done: Type 'mark [number]'");
        System.out.println("  - Mark task as not done: Type 'unmark [number]'");
        System.out.println("  - Delete task: Type 'delete [number]'");
        System.out.println("  - Exit: Type 'bye'");
        System.out.println("------------------------------------------------------------------------------");
    }

    /**
     * Reads a command from the user.
     *
     * @return The user's input as a trimmed string
     */
    public String readCommand() {
        System.out.print(">>> ");
        return scanner.nextLine().trim();
    }

    /**
     * Displays a horizontal line to separate different sections of output.
     */
    public void showLine() {
        System.out.println("------------------------------------------------------------------------------");
    }

    /**
     * Displays an error message to the user.
     *
     * @param message The error message to display
     */
    public void showError(String message) {
        System.out.println(message);
    }

    /**
     * Displays a general message to the user.
     *
     * @param message The message to display
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * Closes the scanner and releases system resources.
     * Should be called when the application is exiting.
     */
    public void close() {
        scanner.close();
    }
}