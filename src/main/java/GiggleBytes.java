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
        TaskList taskList = new TaskList(100);

        System.out.println("------------------------------------------------------------------------------");
        System.out.println(logo);
        System.out.println("------------------------------------------------------------------------------");
        System.out.println("Hello! o/ I'm " + cbName + "! Your personal digital task manager!");
        System.out.println("I can help you track and complete your tasks! >.<");
        System.out.println("------------------------------------------------------------------------------");
        System.out.println("Available Commands (Non-Case Sensitive) : >.<");
        System.out.println("  - Add a task: Just type your task!");
        System.out.println("  - List tasks: Type 'list'");
        System.out.println("  - Mark task as done: Type 'mark [number]'");
        System.out.println("  - Mark task as not done: Type 'unmark [number]'");
        System.out.println("  - Exit: Type 'bye'");
        System.out.println("------------------------------------------------------------------------------");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print(">>> ");
            String userInput = scanner.nextLine().trim();

            System.out.println("------------------------------------------------------------------------------");

            if (userInput.equalsIgnoreCase("bye")) {
                System.out.println("Byte you later! Hope to see you again soon! >.<");
                System.out.println("Tasks completed today: " + countCompletedTasks(taskList) + " >.<");
                break;
            } else if (userInput.equalsIgnoreCase("list")) {
                taskList.printAllItems();
            } else if (userInput.toLowerCase().startsWith("mark ")) {
                handleMarkCommand(userInput, taskList, true);
            } else if (userInput.toLowerCase().startsWith("unmark ")) {
                handleMarkCommand(userInput, taskList, false);
            } else if (userInput.trim().isEmpty()) {
                System.out.println("GiggleBytes is listening... type something!");
            } else {
                if (taskList.addTask(userInput)) {
                    System.out.println(">.< Added: " + userInput);
                    System.out.println("Now you have " + taskList.getItemCount() + " task(s) in the list.");
                } else {
                    System.out.println("Task storage is full! Can't add more tasks. ;-;");
                }
            }
        }

        scanner.close();
        System.out.println("------------------------------------------------------------------------------");
    }

    private static void handleMarkCommand(String userInput, TaskList taskList, boolean markAsDone) {
        String command = markAsDone ? "mark " : "unmark ";
        String action = markAsDone ? "mark" : "unmark";

        try {
            int taskNumber = Integer.parseInt(userInput.substring(command.length()).trim());
            Task task = taskList.getTask(taskNumber);

            if (task != null) {
                if (markAsDone) {
                    if (!task.isDone()) {
                        task.markAsDone();
                        System.out.println("Nice! >.< I've marked this task as done:");
                    } else {
                        System.out.println("This task was already marked as done!  0-0");
                    }
                } else {
                    if (task.isDone()) {
                        task.markAsNotDone();
                        System.out.println("OK ;-; , I've marked this task as not done yet:");
                    } else {
                        System.out.println("This task was already marked as not done! <.<");
                    }
                }
                System.out.println("   " + task);
            } else {
                System.out.println("Invalid task number! Please choose between 1 and " + taskList.getItemCount());
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid task number! Format: '" + action + " [number]'");
        }
    }

    private static int countCompletedTasks(TaskList taskList) {
        int completed = 0;
        for (int i = 1; i <= taskList.getItemCount(); i++) {
            Task task = taskList.getTask(i);
            if (task != null && task.isDone()) {
                completed++;
            }
        }
        return completed;
    }
}
