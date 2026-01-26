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
        System.out.println("  - Delete task: Type 'delete [number]'");
        System.out.println("  - Exit: Type 'bye'");
        System.out.println("------------------------------------------------------------------------------");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print(">>> ");
            String userInput = scanner.nextLine().trim();

            System.out.println("------------------------------------------------------------------------------");

            String lowerInput = userInput.toLowerCase();

            try {
                if (lowerInput.startsWith("bye")) {
                    System.out.println("Byte you later! Hope to see you again soon! >.<");
                    System.out.println("Tasks completed today: " + countCompletedTasks(taskList) + " >.<");
                    break;
                } else if (lowerInput.startsWith("list")) {
                    taskList.printAllItems();
                } else if (lowerInput.startsWith("mark")) {
                    handleMarkCommand(userInput, taskList, true);
                } else if (lowerInput.startsWith("unmark")) {
                    handleMarkCommand(userInput, taskList, false);
                } else if (lowerInput.startsWith("todo")) {
                    handleTodoCommand(userInput, taskList);
                } else if (lowerInput.startsWith("deadline")) {
                    handleDeadlineCommand(userInput, taskList);
                } else if (lowerInput.startsWith("event")) {
                    handleEventCommand(userInput, taskList);
                } else if (userInput.isEmpty()) {
                    System.out.println("GiggleBytes is listening... type something!");
                } else if (lowerInput.startsWith("delete")) {
                    handleDeleteCommand(userInput, taskList);
                } else {
                    throw new GiggleBytesException("I'm a bit confused! >.< I don't know what that means!");
                }
            } catch (GiggleBytesException e) {
                System.out.println(e.getMessage());
            }

            System.out.println("------------------------------------------------------------------------------");
        }
        scanner.close();
    }

    private static void handleTodoCommand(String userInput, TaskList taskList) throws GiggleBytesException {
        String description = userInput.substring(4).trim();

        if (description.isEmpty()) {
            throw new GiggleBytesException("Oopsie! The description of a todo cannot be empty. ;-;");
        }

        if (!taskList.addTodo(description)) {
            throw new GiggleBytesException("Task storage is full! Can't add more tasks. ;-;");
        }

        System.out.println("Got it. I've added this task:");
        System.out.println("  " + taskList.getTask(taskList.getItemCount()));
        System.out.println("Now you have " + taskList.getItemCount() + " tasks in the list.");
    }

    private static void handleDeadlineCommand(String userInput, TaskList taskList) throws GiggleBytesException {
        String rest = userInput.substring(8).trim();

        if (rest.isEmpty()) {
            throw new GiggleBytesException("Hmm... Please provide description and deadline!\nFormat: deadline [description] /by [date/time]");
        }

        String[] parts = rest.split(" /by ");

        if (parts.length < 2) {
            if (!rest.contains("/by")) {
                throw new GiggleBytesException("Missing '/by' parameter!\nFormat: deadline [description] /by [date/time]");
            } else {
                throw new GiggleBytesException("Oops! Both description and deadline time are required!\nInvalid Format! Please use: deadline [description] /by [date/time]");
            }
        }

        String description = parts[0].trim();
        String by = parts[1].trim();

        if (!taskList.addDeadline(description, by)) {
            throw new GiggleBytesException("Task storage is full! Can't add more tasks. ;-;");
        }

        System.out.println("Got it. I've added this task:");
        System.out.println("  " + taskList.getTask(taskList.getItemCount()));
        System.out.println("Now you have " + taskList.getItemCount() + " tasks in the list.");
    }

    private static void handleEventCommand(String userInput, TaskList taskList) throws GiggleBytesException {
        String rest = userInput.substring(5).trim();

        if (rest.isEmpty()) {
            throw new GiggleBytesException("Hmm... Please use the format: event [description] /from [start] /to [end]");
        }

        if (rest.contains(" /to ") && rest.contains(" /from ") &&
                rest.indexOf(" /to ") < rest.indexOf(" /from ")) {
            throw new GiggleBytesException("/from must come before /to!\nFormat: event [description] /from [start] /to [end]");
        }

        String[] parts = rest.split(" /from | /to ");

        if (parts.length < 3) {
            if (!rest.contains("/from") && !rest.contains("/to")) {
                throw new GiggleBytesException("Missing both /from and /to parameters!\nFormat: event [description] /from [start] /to [end]");
            } else if (!rest.contains("/from")) {
                throw new GiggleBytesException("Missing /from parameter!\nFormat: event [description] /from [start] /to [end]");
            } else if (!rest.contains("/to")) {
                throw new GiggleBytesException("Missing /to parameter!\nFormat: event [description] /from [start] /to [end]");
            } else {
                throw new GiggleBytesException("Whoops! Description, start time, and end time are all required!\nInvalid format! Please use: event [description] /from [start] /to [end]");
            }
        }

        String description = parts[0].trim();
        String from = parts[1].trim();
        String to = parts[2].trim();

        if (!taskList.addEvent(description, from, to)) {
            throw new GiggleBytesException("Task storage is full! Can't add more tasks. ;-;");
        }

        System.out.println("Got it. I've added this task:");
        System.out.println("  " + taskList.getTask(taskList.getItemCount()));
        System.out.println("Now you have " + taskList.getItemCount() + " tasks in the list.");
    }

    private static void handleMarkCommand(String userInput, TaskList taskList, boolean markAsDone) throws GiggleBytesException {
        String rest = markAsDone ? userInput.substring(4).trim() : userInput.substring(5).trim();
        String command = markAsDone ? "mark " : "unmark ";
        String action = markAsDone ? "mark" : "unmark";
        String actionText = markAsDone ? "mark as done" : "mark as not done";

        if (rest.isEmpty()) {
            throw new GiggleBytesException("Please specify which task to " + actionText + "!\nFormat: '" + action + " [number]'");
        }

        try {
            int taskNumber = Integer.parseInt(userInput.substring(command.length()).trim());
            Task task = taskList.getTask(taskNumber);

            if (task == null) {
                System.out.println("Task number " + taskNumber + " doesn't exist! ;-;");
                System.out.println("Please choose a number between 1 and " + taskList.getItemCount());
                return;
            }

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
        } catch (NumberFormatException e) {
            System.out.println("That doesn't look like a valid number! >.<");
            System.out.println("Please use the format: '" + action + " [number]'");
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Please specify which task to " + actionText + "!");
            System.out.println("Format: '" + action + " [number]'");
        }
    }

    private static void handleDeleteCommand(String userInput, TaskList taskList) throws GiggleBytesException {
        String rest = userInput.substring(6).trim();
        String action = "delete";
        String actionText = "delete";

        if (rest.isEmpty()) {
            throw new GiggleBytesException("Please specify which task to " + actionText + "!\nFormat: '" + action + " [number]'");
        }

        try {
            int taskNumber = Integer.parseInt(rest);

            // First check if the list is empty
            if (taskList.getItemCount() == 0) {
                System.out.println("Your task list is empty! There's nothing to delete! ;-;");
                return;
            }

            // Then check if the task number is valid
            if (taskNumber < 1 || taskNumber > taskList.getItemCount()) {
                System.out.println("Task number " + taskNumber + " doesn't exist! ;-;");
                System.out.println("Please choose a number between 1 and " + taskList.getItemCount());
                return;
            }

            Task task = taskList.deleteTask(taskNumber);

            if (task != null) {
                System.out.println("Noted. I've removed this task:");
                System.out.println("  " + task);
                System.out.println("Now you have " + taskList.getItemCount() + " tasks in the list.");
            } else {
                System.out.println("Could not delete task " + taskNumber + "! ;-;");
            }
        } catch (NumberFormatException e) {
            System.out.println("That doesn't look like a valid number! >.<");
            System.out.println("Please use the format: '" + action + " [number]'");
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
