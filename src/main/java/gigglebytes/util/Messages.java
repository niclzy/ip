package gigglebytes.util;

/**
 * Constants for user-facing messages throughout the application.
 */
public final class Messages {

    // Error messages
    public static final String TODO_EMPTY = "Oopsies! The description of a todo cannot be empty. ;-;";
    public static final String MISSING_KEYWORD = "Please specify a keyword to search for!\nFormat: 'find [keyword]'";
    public static final String MISSING_TASK_NUMBER = "Please specify which task to %s!\nFormat: '%s [number]'";
    public static final String TASK_NUMBER_POSITIVE = "Task number must be positive! >.<\nPlease use the format: '%s [number]' where number is 1 or higher";
    public static final String INVALID_NUMBER = "That doesn't look like a valid number! >.<\nPlease use the format: '%s [number]'";
    public static final String MISSING_DEADLINE_FORMAT = "Hmm... Please provide description and deadline!\nFormat: deadline [description] /by [date/time]";
    public static final String MISSING_DEADLINE_BY = "Missing '/by' parameter!\nFormat: deadline [description] /by [date/time]";
    public static final String INVALID_DEADLINE_FORMAT = "Oops! Both description and deadline time are required!\nInvalid Format! Please use: deadline [description] /by [date/time]";
    public static final String MISSING_EVENT_FORMAT = "Hmm... Please use the format: event [description] /from [start] /to [end]";
    public static final String EVENT_ORDER_ERROR = "/from must come before /to!\nFormat: event [description] /from [start] /to [end]";
    public static final String MISSING_EVENT_BOTH = "Missing both /from and /to parameters!\nFormat: event [description] /from [start] /to [end]";
    public static final String MISSING_EVENT_FROM = "Missing /from parameter!\nFormat: event [description] /from [start] /to [end]";
    public static final String MISSING_EVENT_TO = "Missing /to parameter!\nFormat: event [description] /from [start] /to [end]";
    public static final String INVALID_EVENT_FORMAT = "Whoops! Description, start time, and end time are all required!\nInvalid format! Please use: event [description] /from [start] /to [end]";
    public static final String STORAGE_FULL = "Task storage is full! Can't add more tasks. ;-;";
    public static final String EMPTY_LIST = "Your list is empty! Add something first!";
    public static final String TASK_NOT_FOUND = "Task number %d doesn't exist! ;-;";
    public static final String CHOOSE_VALID_NUMBER = "Please choose a number between 1 and %d";
    public static final String EMPTY_INPUT = "GiggleBytes is listening... type something!";
    public static final String UNKNOWN_COMMAND = "I'm a bit confused! >.< I don't know what that means!";
    public static final String COULD_NOT_DELETE = "Could not delete task %d! ;-;";
    public static final String EMPTY_LIST_SORT = "Your list is empty! Nothing to sort.";

    // Success messages
    public static final String TASK_ADDED = "Got it. I've added this task:";
    public static final String TASK_REMOVED = "Noted! I've removed this task:";
    public static final String TASK_COUNT = "Now you have %d tasks in the list.";
    public static final String MARK_DONE = "Nice! >.< I've marked this task as done:";
    public static final String MARK_NOT_DONE = "OK ;-; , I've marked this task as not done yet:";
    public static final String ALREADY_DONE = "This task was already marked as done!  0-0";
    public static final String ALREADY_NOT_DONE = "This task was already marked as not done! <.<";
    public static final String GOODBYE = "Byte you later! Hope to see you again soon! >.<";
    public static final String TASKS_COMPLETED = "Tasks completed today: %d >.<";
    public static final String LOADED_TASKS = "Loaded %d tasks from save file! >.<";
    public static final String NO_SAVED_DATA = "No saved data found. Starting fresh! >.<";
    public static final String SORTED = "Tasks sorted by %s!";

    // List messages
    public static final String LIST_HEADER = "Here are your tasks:";
    public static final String LIST_TOTAL = "Total tasks: %d";
    public static final String MATCHING_TASKS = "Here are the matching tasks in your list:";
    public static final String NO_MATCHES = "No tasks found containing: %s";
    public static final String FOUND_MATCHES = "Found %d matching task(s).";

    private Messages() {} // Prevents instantiation
}