# GiggleBytes User Guide

GiggleBytes is a interactive, task manager style chatbot that is still under development

# Features
### Adding Tasks
Simply type your task description to add it to your list.

Example: `read book`
```
>>> read book
------------------------------------------------------------------------------
Added: read book
Now you have 1 task(s) in the list.
------------------------------------------------------------------------------
```

### Listing Tasks
Command - list: Shows all tasks with their completion status.

Example: `list`
```
>>> list
------------------------------------------------------------------------------
Here are your tasks:
  1. [ ] read book
  2. [ ] return book
Total tasks: 2
------------------------------------------------------------------------------
```

### Marking Tasks as Done
Command - mark [number]: Marks the specified task as completed.

Example: `mark 2`
```
>>> mark 2
------------------------------------------------------------------------------
Nice! I've marked this task as done:
   [X] return book
------------------------------------------------------------------------------
```

### Unmarking Tasks as not Done
Command - unmark [number]: Marks the specified task as not completed.

Example: `unmark 2`
```
>>> unmark 2
------------------------------------------------------------------------------
OK, I've marked this task as not done yet:
   [ ] return book
------------------------------------------------------------------------------
```

### Exiting
Command - bye: Exits and closes GiggleBytes, showing a summary of completed tasks.

Example: `bye`
```
>>> bye
------------------------------------------------------------------------------
Byte you later! Hope to see you again soon!
Tasks completed today: 1
------------------------------------------------------------------------------
```