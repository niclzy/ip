# gigglebytes.GiggleBytes User Guide

gigglebytes.GiggleBytes is a interactive, task manager style chatbot that is still under development

# Features
All Features and Sub Features

### Adding Different gigglebytes.task.Task Types

#### gigglebytes.task.Todo Tasks
Adds a simple task without any date/time attached.

**Command:** `todo [description]`

**Example:** `todo borrow book`
```
>>> todo borrow book
------------------------------------------------------------------------------
Got it. I've added this task:
  [T][ ] borrow book
Now you have 1 tasks in the list.
------------------------------------------------------------------------------
```

#### gigglebytes.task.Deadline Tasks
Adds a task that needs to be done before a specific date/time.

**Command:** `deadline [description] /by [date/time]`

**Example:** `deadline return book /by Sunday`
```
>>> deadline return book /by Sunday
------------------------------------------------------------------------------
Got it. I've added this task:
  [D][ ] return book (by: Sunday)
Now you have 6 tasks in the list.
------------------------------------------------------------------------------
```


#### gigglebytes.task.Event Tasks
Adds a task that starts and ends at specific times.

**Command:** `event [description] /from [start] /to [end]`

**Example:** `event project meeting /from Mon 2pm /to 4pm`
```
>>> event project meeting /from Mon 2pm /to 4pm
------------------------------------------------------------------------------
Got it. I've added this task:
  [E][ ] project meeting (from: Mon 2pm to: 4pm)
Now you have 7 tasks in the list.
------------------------------------------------------------------------------
```

### Listing Tasks
**Command:** `Shows all tasks with their completion status.`

**Example:** `list`
```
>>> list
------------------------------------------------------------------------------
Here are your tasks:
  1. [T][X] read book
  2. [D][ ] return book (by: June 6th)
  3. [E][ ] project meeting (from: Aug 6th 2pm to: 4pm)
  4. [T][X] join sports club
  5. [T][ ] borrow book
  6. [D][ ] return book (by: Sunday)
  7. [E][ ] project meeting (from: Mon 2pm to: 4pm)
Total tasks: 7
------------------------------------------------------------------------------
```

### Marking Tasks as Done
**Command:** `mark [number]: Marks the specified task as completed.`

**Example:** `mark 1`
```
>>> mark 1
------------------------------------------------------------------------------
Nice! >.< I've marked this task as done:
   [T][X] read book
------------------------------------------------------------------------------
```

### Unmarking Tasks as not Done
**Command:** `unmark [number]: Marks the specified task as not completed.`

**Example:** `unmark 3`
```
>>> unmark 3
------------------------------------------------------------------------------
OK ;-; , I've marked this task as not done yet:
   [E][ ] project meeting (from: Aug 6th 2pm to: 4pm)
------------------------------------------------------------------------------
```

### Exiting
**Command:** `exits and closes gigglebytes.GiggleBytes, showing a summary of completed tasks.`

**Example:** `bye`
```
>>> bye
------------------------------------------------------------------------------
Byte you later! Hope to see you again soon!
Tasks completed today: 1
------------------------------------------------------------------------------
```