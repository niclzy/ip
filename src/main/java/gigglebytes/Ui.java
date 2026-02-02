package gigglebytes;

import java.util.Scanner;

public class Ui {
    private Scanner scanner;

    public Ui() {
        scanner = new Scanner(System.in);
    }

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

    public void showHelp() {
        System.out.println("Available Commands (Non-Case Sensitive) : >.<");
        System.out.println("  - Add Todo: 'todo [description]'");
        System.out.println("  - Add Deadline: 'deadline [description] /by [date/time]'");
        System.out.println("  - Add Event: 'event [description] /from [start] /to [end]'");
        System.out.println("  - List tasks: Type 'list'");
        System.out.println("  - Mark task as done: Type 'mark [number]'");
        System.out.println("  - Mark task as not done: Type 'unmark [number]'");
        System.out.println("  - Delete task: Type 'delete [number]'");
        System.out.println("  - Exit: Type 'bye'");
        System.out.println("------------------------------------------------------------------------------");
    }

    public String readCommand() {
        System.out.print(">>> ");
        return scanner.nextLine().trim();
    }

    public void showLine() {
        System.out.println("------------------------------------------------------------------------------");
    }

    public void showError(String message) {
        System.out.println(message);
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void close() {
        scanner.close();
    }
}