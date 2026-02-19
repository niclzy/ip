package gigglebytes;

import gigglebytes.command.AddDeadlineCommand;
import gigglebytes.command.AddEventCommand;
import gigglebytes.command.AddTodoCommand;
import gigglebytes.command.Command;
import gigglebytes.command.DeleteCommand;
import gigglebytes.command.ExitCommand;
import gigglebytes.command.FindCommand;
import gigglebytes.command.ListCommand;
import gigglebytes.command.MarkCommand;
import gigglebytes.exception.GiggleBytesException;
import gigglebytes.util.Messages;

import static gigglebytes.command.CommandWords.*;

/**
 * Parses user input strings into corresponding Command objects.
 */
public class Parser {
    private static final int TODO_PREFIX_LENGTH = 4;
    private static final int DEADLINE_PREFIX_LENGTH = 8;
    private static final int EVENT_PREFIX_LENGTH = 5;
    private static final int FIND_PREFIX_LENGTH = 4;
    private static final int MARK_PREFIX_LENGTH = 4;
    private static final int UNMARK_PREFIX_LENGTH = 6;
    private static final int DELETE_PREFIX_LENGTH = 6;

    /**
     * Parses a user input string and returns the corresponding Command.
     *
     * @param userInput The raw input string from the user
     * @return A Command object corresponding to the user's input
     * @throws GiggleBytesException If the input is empty, invalid, or malformed
     */
    public static Command parse(String userInput) throws GiggleBytesException {
        assert userInput != null : "User input cannot be null";

        String lowerInput = userInput.toLowerCase().trim();

        if (lowerInput.startsWith(BYE)) {
            return new ExitCommand();
        } else if (lowerInput.startsWith(LIST)) {
            return new ListCommand();
        } else if (lowerInput.startsWith(FIND)) {
            return parseFindCommand(userInput);
        } else if (lowerInput.startsWith(MARK)) {
            return parseMarkCommand(userInput, true);
        } else if (lowerInput.startsWith(UNMARK)) {
            return parseMarkCommand(userInput, false);
        } else if (lowerInput.startsWith(TODO)) {
            return parseTodoCommand(userInput);
        } else if (lowerInput.startsWith(DEADLINE)) {
            return parseDeadlineCommand(userInput);
        } else if (lowerInput.startsWith(EVENT)) {
            return parseEventCommand(userInput);
        } else if (lowerInput.startsWith(DELETE)) {
            return parseDeleteCommand(userInput);
        } else if (userInput.isEmpty()) {
            throw new GiggleBytesException(Messages.EMPTY_INPUT);
        } else {
            throw new GiggleBytesException(Messages.UNKNOWN_COMMAND);
        }
    }

    private static Command parseFindCommand(String userInput) throws GiggleBytesException {
        assert userInput != null : "User input cannot be null";

        if (userInput.length() <= FIND_PREFIX_LENGTH) {
            throw new GiggleBytesException(Messages.MISSING_KEYWORD);
        }

        String keyword = userInput.substring(FIND_PREFIX_LENGTH).trim();

        if (keyword.isEmpty()) {
            throw new GiggleBytesException(Messages.MISSING_KEYWORD);
        }

        assert !keyword.isEmpty() : "Keyword should not be empty after validation";
        return new FindCommand(keyword);
    }

    private static Command parseMarkCommand(String userInput, boolean isMarkAsDone) throws GiggleBytesException {
        assert userInput != null : "User input cannot be null";

        int prefixLength = isMarkAsDone ? MARK_PREFIX_LENGTH : UNMARK_PREFIX_LENGTH;
        String action = isMarkAsDone ? MARK : UNMARK;

        if (userInput.length() <= prefixLength) {
            String actionText = isMarkAsDone ? "mark as done" : "mark as not done";
            throw new GiggleBytesException(String.format(Messages.MISSING_TASK_NUMBER, actionText, action));
        }

        String rest = userInput.substring(prefixLength).trim();

        if (rest.isEmpty()) {
            String actionText = isMarkAsDone ? "mark as done" : "mark as not done";
            throw new GiggleBytesException(String.format(Messages.MISSING_TASK_NUMBER, actionText, action));
        }

        try {
            int taskNumber = Integer.parseInt(rest);
            if (taskNumber <= 0) {
                throw new GiggleBytesException(String.format(Messages.TASK_NUMBER_POSITIVE, action));
            }

            assert taskNumber > 0 : "Task number should be positive after validation";
            return new MarkCommand(taskNumber, isMarkAsDone);
        } catch (NumberFormatException e) {
            throw new GiggleBytesException(String.format(Messages.INVALID_NUMBER, action));
        }
    }

    private static Command parseTodoCommand(String userInput) throws GiggleBytesException {
        assert userInput != null : "User input cannot be null";

        if (userInput.length() <= TODO_PREFIX_LENGTH) {
            throw new GiggleBytesException(Messages.TODO_EMPTY);
        }

        String description = userInput.substring(TODO_PREFIX_LENGTH).trim();

        if (description.isEmpty()) {
            throw new GiggleBytesException(Messages.TODO_EMPTY);
        }

        assert !description.isEmpty() : "Description should not be empty after validation";
        return new AddTodoCommand(description);
    }

    private static Command parseDeadlineCommand(String userInput) throws GiggleBytesException {
        assert userInput != null : "User input cannot be null";

        if (userInput.length() <= DEADLINE_PREFIX_LENGTH) {
            throw new GiggleBytesException(Messages.MISSING_DEADLINE_FORMAT);
        }

        String rest = userInput.substring(DEADLINE_PREFIX_LENGTH).trim();

        if (rest.isEmpty()) {
            throw new GiggleBytesException(Messages.MISSING_DEADLINE_FORMAT);
        }

        String[] parts = rest.split(" /by ");

        if (parts.length < 2) {
            if (!rest.contains("/by")) {
                throw new GiggleBytesException(Messages.MISSING_DEADLINE_BY);
            } else {
                throw new GiggleBytesException(Messages.INVALID_DEADLINE_FORMAT);
            }
        }

        String description = parts[0].trim();
        String by = parts[1].trim();

        assert description != null && !description.isEmpty() : "Description should be valid";
        assert by != null && !by.isEmpty() : "By date should be valid";

        return new AddDeadlineCommand(description, by);
    }

    private static Command parseEventCommand(String userInput) throws GiggleBytesException {
        assert userInput != null : "User input cannot be null";

        String rest = validateEventInput(userInput);
        validateEventOrder(rest);
        String[] parts = extractEventParts(rest);

        return createEventCommand(parts);
    }

    private static String validateEventInput(String userInput) throws GiggleBytesException {
        if (userInput.length() <= EVENT_PREFIX_LENGTH) {
            throw new GiggleBytesException(Messages.MISSING_EVENT_FORMAT);
        }
        String rest = userInput.substring(EVENT_PREFIX_LENGTH).trim();
        if (rest.isEmpty()) {
            throw new GiggleBytesException(Messages.MISSING_EVENT_FORMAT);
        }
        return rest;
    }

    private static void validateEventOrder(String rest) throws GiggleBytesException {
        if (rest.contains(" /to ") && rest.contains(" /from ") &&
                rest.indexOf(" /to ") < rest.indexOf(" /from ")) {
            throw new GiggleBytesException(Messages.EVENT_ORDER_ERROR);
        }
    }

    private static String[] extractEventParts(String rest) throws GiggleBytesException {
        String[] parts = rest.split(" /from | /to ");
        if (parts.length < 3) {
            String error = getEventErrorMessage(rest);
            throw new GiggleBytesException(error);
        }
        return parts;
    }

    private static String getEventErrorMessage(String rest) {
        if (!rest.contains("/from") && !rest.contains("/to")) {
            return Messages.MISSING_EVENT_BOTH;
        } else if (!rest.contains("/from")) {
            return Messages.MISSING_EVENT_FROM;
        } else if (!rest.contains("/to")) {
            return Messages.MISSING_EVENT_TO;
        } else {
            return Messages.INVALID_EVENT_FORMAT;
        }
    }

    private static Command createEventCommand(String[] parts) {
        String description = parts[0].trim();
        String from = parts[1].trim();
        String to = parts[2].trim();

        assert description != null && !description.isEmpty() : "Description should be valid";
        assert from != null && !from.isEmpty() : "From date should be valid";
        assert to != null && !to.isEmpty() : "To date should be valid";

        return new AddEventCommand(description, from, to);
    }

    private static Command parseDeleteCommand(String userInput) throws GiggleBytesException {
        assert userInput != null : "User input cannot be null";

        if (userInput.length() <= DELETE_PREFIX_LENGTH) {
            throw new GiggleBytesException(String.format(Messages.MISSING_TASK_NUMBER, "delete", DELETE));
        }

        String rest = userInput.substring(DELETE_PREFIX_LENGTH).trim();

        if (rest.isEmpty()) {
            throw new GiggleBytesException(String.format(Messages.MISSING_TASK_NUMBER, "delete", DELETE));
        }

        try {
            int taskNumber = Integer.parseInt(rest);
            if (taskNumber <= 0) {
                throw new GiggleBytesException(String.format(Messages.TASK_NUMBER_POSITIVE, DELETE));
            }

            assert taskNumber > 0 : "Task number should be positive after validation";
            return new DeleteCommand(taskNumber);
        } catch (NumberFormatException e) {
            throw new GiggleBytesException(String.format(Messages.INVALID_NUMBER, DELETE));
        }
    }
}