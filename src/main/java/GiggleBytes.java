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
        String[] tasks = new String[100];
        int taskCount = 0;

        System.out.println("------------------------------------------------------------------------------");
        System.out.println(logo);
        System.out.println("------------------------------------------------------------------------------");
        System.out.println("Hello! o/ I'm " + cbName + "! Your personal task manager!");
        System.out.println("Commands: Type 'list' to see all tasks or 'bye' to exit!");
        System.out.println("What can I do for you?");
        System.out.println("Type what you want to add to your list! >.<");
        System.out.println("------------------------------------------------------------------------------");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print(">>> ");
            String userInput = scanner.nextLine().trim();

            System.out.println("------------------------------------------------------------------------------");

            if (userInput.equalsIgnoreCase("bye")) {
                System.out.println("Byte you later! Hope to see you again soon! >.<");
                break;
            } else if (userInput.equalsIgnoreCase("list")) {
                if (taskCount == 0) {
                    System.out.println("Your task list is empty! Add something first! >.<");
                } else {
                    System.out.println("Here are your tasks:");
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println("  " + (i + 1) + ". " + tasks[i]);
                    }
                    System.out.println("Total tasks: " + taskCount + " <.< ");
                }
            } else if (userInput.trim().isEmpty()) {
                System.out.println("GiggleBytes is listening... type something!");
            } else {
                if (taskCount < tasks.length) {
                    tasks[taskCount] = userInput;
                    taskCount++;
                    System.out.println("Added: " + userInput);
                    System.out.println("Now you have " + taskCount + " task(s) in the list.");
                } else {
                    System.out.println("⚠️  Task storage is full! Can't add more tasks.");
                }
            }
        }

        scanner.close();
        System.out.println("------------------------------------------------------------------------------");
    }
}
