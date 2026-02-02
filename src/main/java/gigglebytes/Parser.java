package gigglebytes;

import gigglebytes.command.*;
import gigglebytes.exception.GiggleBytesException;

public class Parser {

    public static Command parse(String userInput) throws GiggleBytesException {
        String lowerInput = userInput.toLowerCase().trim();

        if (lowerInput.startsWith("bye")) {
            return new ExitCommand();
        } else if (lowerInput.startsWith("list")) {
            return new ListCommand();
        } else if (lowerInput.startsWith("mark")) {
            return parseMarkCommand(userInput, true);
        } else if (lowerInput.startsWith("unmark")) {
            return parseMarkCommand(userInput, false);
        } else if (lowerInput.startsWith("todo")) {
            return parseTodoCommand(userInput);
        } else if (lowerInput.startsWith("deadline")) {
            return parseDeadlineCommand(userInput);
        } else if (lowerInput.startsWith("event")) {
            return parseEventCommand(userInput);
        } else if (lowerInput.startsWith("delete")) {
            return parseDeleteCommand(userInput);
        } else if (userInput.isEmpty()) {
            throw new GiggleBytesException("GiggleBytes is listening... type something!");
        } else {
            throw new GiggleBytesException("I'm a bit confused! >.< I don't know what that means!");
        }
    }

    private static Command parseMarkCommand(String userInput, boolean markAsDone) throws GiggleBytesException {
        String rest = markAsDone ? userInput.substring(4).trim() : userInput.substring(5).trim();
        String action = markAsDone ? "mark" : "unmark";
        String actionText = markAsDone ? "mark as done" : "mark as not done";

        if (rest.isEmpty()) {
            throw new GiggleBytesException("Please specify which task to " + actionText + "!\nFormat: '" + action + " [number]'");
        }

        try {
            int taskNumber = Integer.parseInt(rest);
            return new MarkCommand(taskNumber, markAsDone);
        } catch (NumberFormatException e) {
            throw new GiggleBytesException("That doesn't look like a valid number! >.<\nPlease use the format: '" + action + " [number]'");
        }
    }

    private static Command parseTodoCommand(String userInput) throws GiggleBytesException {
        String description = userInput.substring(4).trim();

        if (description.isEmpty()) {
            throw new GiggleBytesException("Oopsies! The description of a todo cannot be empty. ;-;");
        }

        return new AddTodoCommand(description);
    }

    private static Command parseDeadlineCommand(String userInput) throws GiggleBytesException {
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

        return new AddDeadlineCommand(description, by);
    }

    private static Command parseEventCommand(String userInput) throws GiggleBytesException {
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

        return new AddEventCommand(description, from, to);
    }

    private static Command parseDeleteCommand(String userInput) throws GiggleBytesException {
        String rest = userInput.substring(6).trim();

        if (rest.isEmpty()) {
            throw new GiggleBytesException("Please specify which task to delete!\nFormat: 'delete [number]'");
        }

        try {
            int taskNumber = Integer.parseInt(rest);
            return new DeleteCommand(taskNumber);
        } catch (NumberFormatException e) {
            throw new GiggleBytesException("That doesn't look like a valid number! >.<\nPlease use the format: 'delete [number]'");
        }
    }
}