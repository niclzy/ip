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
        System.out.println("  - Add Todo: 'todo [description]'");
        System.out.println("  - Add Deadline: 'deadline [description] /by [date/time]'");
        System.out.println("  - Add Event: 'event [description] /from [start] /to [end]'");
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

            String lowerInput = userInput.toLowerCase();

            if (lowerInput.equals("bye")) {
                System.out.println("Byte you later! Hope to see you again soon! >.<");
                System.out.println("Tasks completed today: " + countCompletedTasks(taskList) + " >.<");
                break;
            } else if (lowerInput.equals("list")) {
                taskList.printAllItems();
            } else if (lowerInput.startsWith("mark ")) {
                handleMarkCommand(userInput, taskList, true);
            } else if (lowerInput.startsWith("unmark ")) {
                handleMarkCommand(userInput, taskList, false);
            } else if (lowerInput.startsWith("todo ")) {
                handleTodoCommand(userInput, taskList);
            } else if (lowerInput.startsWith("deadline ")) {
                handleDeadlineCommand(userInput, taskList);
            } else if (lowerInput.startsWith("event ")) {
                handleEventCommand(userInput, taskList);
            } else if (userInput.trim().isEmpty()) {
                System.out.println("GiggleBytes is listening... type something!");
            } else {
                System.out.println("I don't understand that command! >.<");
                System.out.println("Use 'todo', 'deadline', or 'event' to add tasks!");
            }
            System.out.println("------------------------------------------------------------------------------");
        }
        scanner.close();
    }

    private static void handleTodoCommand(String userInput, TaskList taskList) {
        String description = userInput.substring(5).trim();
        if (description.isEmpty()) {
            System.out.println("Please provide a description for your todo!");
            return;
        }

        if (taskList.addTodo(description)) {
            System.out.println("Got it. I've added this task:");
            System.out.println("  " + taskList.getTask(taskList.getItemCount()));
            System.out.println("Now you have " + taskList.getItemCount() + " tasks in the list.");
        } else {
            System.out.println("Task storage is full! Can't add more tasks. ;-;");
        }
    }

    private static void handleDeadlineCommand(String userInput, TaskList taskList) {
        String rest = userInput.substring(9).trim();
        String[] parts = rest.split(" /by ");

        if (parts.length < 2) {
            System.out.println("Please use the format: deadline [description] /by [date/time]");
            return;
        }

        String description = parts[0].trim();
        String by = parts[1].trim();

        if (description.isEmpty() || by.isEmpty()) {
            System.out.println("Both description and deadline time are required!");
            return;
        }

        if (taskList.addDeadline(description, by)) {
            System.out.println("Got it. I've added this task:");
            System.out.println("  " + taskList.getTask(taskList.getItemCount()));
            System.out.println("Now you have " + taskList.getItemCount() + " tasks in the list.");
        } else {
            System.out.println("Task storage is full! Can't add more tasks. ;-;");
        }
    }

    private static void handleEventCommand(String userInput, TaskList taskList) {
        String rest = userInput.substring(6).trim();
        String[] parts = rest.split(" /from | /to ");

        if (parts.length < 3) {
            System.out.println("Please use the format: event [description] /from [start] /to [end]");
            return;
        }

        String description = parts[0].trim();
        String from = parts[1].trim();
        String to = parts[2].trim();

        if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
            System.out.println("Description, start time, and end time are all required!");
            return;
        }

        if (taskList.addEvent(description, from, to)) {
            System.out.println("Got it. I've added this task:");
            System.out.println("  " + taskList.getTask(taskList.getItemCount()));
            System.out.println("Now you have " + taskList.getItemCount() + " tasks in the list.");
        } else {
            System.out.println("Task storage is full! Can't add more tasks. ;-;");
        }
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
