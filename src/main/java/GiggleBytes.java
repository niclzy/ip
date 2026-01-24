import java.util.Scanner;

public class GiggleBytes {
    public static void main(String[] args) {
        String logo = "  ________.__              .__        __________          __                 \n"
                + " /  _____/|__| ____   ____ |  |   ____\\______   \\___.__._/  |_  ____   ______\n"
                + "/   \\  ___|  |/ ___\\ / ___\\|  | _/ __ \\|    |  _<   |  |\\   __\\/ __ \\ /  ___/\n"
                + "\\    \\_\\  \\  / /_/  > /_/  >  |_\\  ___/|    |   \\\\___  | |  | \\  ___/ \\___ \\ \n"
                + " \\______  /__\\___  /\\___  /|____/\\___  >______  // ____| |__|  \\___  >____  >\n"
                + "        \\/  /_____//_____/           \\/       \\/ \\/                \\/     \\/ \n";
        String cbName = "GiggleBytes";
        System.out.println("------------------------------------------------------------------------------");
        System.out.println(logo);
        System.out.println("------------------------------------------------------------------------------");
        System.out.println("Hello! o/ I'm " + cbName);
        System.out.println("What can I do for you?");
        System.out.println("------------------------------------------------------------------------------");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print(">>> ");
            String userInput = scanner.nextLine();

            System.out.println("------------------------------------------------------------------------------");

            if (userInput.equalsIgnoreCase("bye")) {
                System.out.println("Byte you later! Hope to see you again soon! >.<");
                break;
            } else if (userInput.trim().isEmpty()) {
                System.out.println("GiggleBytes is listening... type something!");
            } else {
                System.out.println("GiggleBytes heard: " + userInput);
            }
        }

        scanner.close();
        System.out.println("------------------------------------------------------------------------------");
    }
}
