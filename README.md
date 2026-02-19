# 🎯 GiggleBytes User Guide

**Your personal digital task manager with a smile!** >.<

![GiggleBytes Logo](/src/main/resources/images/GiggleBytes.png)

GiggleBytes is a desktop application that helps you track and manage your tasks efficiently.  
It combines the speed of a command-line interface with the friendliness of a graphical user interface.

---

## 📋 Table of Contents
- [🚀 Quick Start](#-quick-start)
- [✨ Features](#-features)
    - [Adding Tasks](#adding-tasks)
        - [Todo: `todo`](#-todo-todo)
        - [Deadline: `deadline`](#-deadline-deadline)
        - [Event: `event`](#-event-event)
    - [Viewing Tasks](#viewing-tasks)
        - [List all tasks: `list`](#-list-all-tasks-list)
        - [Find tasks: `find`](#-find-tasks-find)
        - [Sort tasks: `sort`](#-sort-tasks-sort)
    - [Managing Tasks](#managing-tasks)
        - [Mark as done: `mark`](#-mark-as-done-mark)
        - [Mark as not done: `unmark`](#-mark-as-not-done-unmark)
        - [Delete task: `delete`](#-delete-task-delete)
    - [Exiting the App](#exiting-the-app)
        - [Exit: `bye`](#-exit-bye)
- [📊 Command Summary](#-command-summary)
- [❓ FAQ](#-faq)
- [🔧 Troubleshooting](#-troubleshooting)
- [📝 Version History](#-version-history)
- [📬 Support](#-support)

---

## 🚀 Quick Start

1. **Ensure you have Java 17 or later installed**.
    - Check by running:
      ```bash
      java -version
      ```

2. **Download the latest `gigglebytes.jar`** from your repository’s releases page.

3. **Copy the JAR file** into an empty folder (recommended).

4. **Open a terminal/command prompt** in that folder.

5. **Run the application**:
   ```bash
   java -jar gigglebytes.jar
   ```

6. **Start typing commands!** See the features below to get started.

> 💡 **Tip:** The GUI version will open automatically.

---

# ✨ Features

## Adding Tasks

### 📝 Todo: `todo`
Adds a simple todo task with no date/time.

**Format**
```text
todo DESCRIPTION
```

**Examples**
```text
todo Buy groceries
todo Read book
todo Finish project report
```

**Expected output**
```text
Got it. I've added this task:
[T][ ] Buy groceries
Now you have 3 tasks in the list.
```

---

### ⏰ Deadline: `deadline`
Adds a task with a deadline (due date/time).

**Format**
```text
deadline DESCRIPTION /by DATE
```

**Examples**
```text
deadline Submit report /by 2024-12-31 2359
deadline Pay bills /by Friday 5pm
deadline Project submission /by 2024-11-30
```

**Expected output**
```text
Got it. I've added this task:
[D][ ] Submit report (by: 2024-12-31 2359)
Now you have 4 tasks in the list.
```

---

### 📅 Event: `event`
Adds an event with a start and end time.

**Format**
```text
event DESCRIPTION /from START /to END
```

**Examples**
```text
event Team meeting /from 2024-10-10 1400 /to 2024-10-10 1500
event Conference /from 2024-09-15 0900 /to 2024-09-17 1700
event Birthday party /from 2024-12-25 1900 /to 2024-12-25 2300
```

**Expected output**
```text
Got it. I've added this task:
[E][ ] Team meeting (from: 2024-10-10 1400 to: 2024-10-10 1500)
Now you have 5 tasks in the list.
```

---

## Viewing Tasks

### 📋 List all tasks: `list`
Displays all tasks in your list with their numbers and status.

**Format**
```text
list
```

**Expected output**
```text
Here are your tasks:
1.[T][ ] Buy groceries
2.[D][ ] Submit report (by: 2024-12-31 2359)
3.[E][ ] Team meeting (from: 2024-10-10 1400 to: 2024-10-10 1500)
4.[T][X] Read book
5.[D][X] Pay bills (by: Friday 5pm)
Total tasks: 5
```

---

### 🔍 Find tasks: `find`
Searches for tasks containing a specific keyword (case-insensitive).

**Format**
```text
find KEYWORD
```

**Examples**
```text
find book
find report
find meeting
```

**Expected output**
```text
Here are the matching tasks in your list:
1.[T][X] Read book
2.[D][ ] Submit report (by: 2024-12-31 2359)
Found 2 matching task(s).
```

---

### 🔄 Sort tasks: `sort`
Sorts tasks by various criteria to help you organise your list.

**Format**
```text
sort TYPE [reverse]
```

**Available sort types**

| Type | Description |
|------|-------------|
| `description` | Alphabetical order by task description |
| `date` | Chronological order (deadlines/events first, todos last) |
| `status` | Undone tasks first, then done tasks |
| `type` | Grouped by task type (Deadline, Event, Todo) |

Add `reverse` to sort in descending order.

**Examples**
```text
sort description
sort date reverse
sort status
sort type
```

**Expected output**
```text
Tasks sorted by date!
Here are your tasks:
1.[E][ ] Conference (from: 2024-09-15 0900 to: 2024-09-17 1700)
2.[E][ ] Team meeting (from: 2024-10-10 1400 to: 2024-10-10 1500)
3.[D][ ] Project deadline (by: 2024-11-30)
4.[D][X] Submit report (by: 2024-12-31 2359)
5.[E][ ] Birthday party (from: 2024-12-25 1900 to: 2024-12-25 2300)
6.[T][ ] Buy groceries
7.[T][X] Read book
Total tasks: 7
```

---

## Managing Tasks

### ✅ Mark as done: `mark`
Marks a task as completed.

**Format**
```text
mark TASK_NUMBER
```

**Example**
```text
mark 2
```

**Expected output**
```text
Nice! >.< I've marked this task as done:
[D][X] Submit report (by: 2024-12-31 2359)
```

---

### ❌ Mark as not done: `unmark`
Marks a completed task as not done.

**Format**
```text
unmark TASK_NUMBER
```

**Example**
```text
unmark 2
```

**Expected output**
```text
OK ;-; , I've marked this task as not done yet:
[D][ ] Submit report (by: 2024-12-31 2359)
```

---

### 🗑️ Delete task: `delete`
Removes a task from your list permanently.

**Format**
```text
delete TASK_NUMBER
```

**Example**
```text
delete 3
```

**Expected output**
```text
Noted! I've removed this task:
[E][ ] Team meeting (from: 2024-10-10 1400 to: 2024-10-10 1500)
Now you have 6 tasks in the list.
```

---

## Exiting the App

### 👋 Exit: `bye`
Saves your tasks and exits the application.

**Format**
```text
bye
```

**Expected output**
```text
Byte you later! Hope to see you again soon! >.<
Tasks completed today: 3 >.<
```

💡 Tip: Your tasks are automatically saved when you exit.

---

## 📊 Command Summary

| Command | Format | Example |
|--------|--------|---------|
| Todo | `todo DESCRIPTION` | `todo Buy groceries` |
| Deadline | `deadline DESCRIPTION /by DATE` | `deadline Report /by 2024-12-31 2359` |
| Event | `event DESCRIPTION /from START /to END` | `event Meeting /from 1400 /to 1500` |
| List | `list` | `list` |
| Find | `find KEYWORD` | `find book` |
| Sort | `sort TYPE [reverse]` | `sort date reverse` |
| Mark | `mark NUMBER` | `mark 2` |
| Unmark | `unmark NUMBER` | `unmark 2` |
| Delete | `delete NUMBER` | `delete 3` |
| Exit | `bye` | `bye` |

---

## ❓ FAQ

**Q: Where are my tasks saved?**  
A: Tasks are saved in a file called `gigglebytes.txt` in the `data` folder (created automatically in the same directory as the JAR file).

**Q: Can I use the app without the GUI?**  
A: Yes. The app works in both GUI and CLI modes.

**Q: What date formats are supported?**  
A: Examples include `2024-12-31 2359`, `Friday 5pm`, or `tomorrow` (depending on your date parser).

**Q: Can I edit a task after creating it?**  
A: Currently, delete and re-add to edit.

**Q: Is there a limit to how many tasks I can have?**  
A: GiggleBytes can handle up to 100 tasks efficiently for most use-cases.

---

## 🔧 Troubleshooting

**Problem: App won't start**  
Solution: Ensure you have Java 17 or later installed:
```bash
java -version
```

**Problem: Tasks not saving**  
Solution: Ensure the app has write permissions in the folder you placed the JAR in (it needs to create a `data` folder).

**Problem: GUI looks wrong**  
Solution: Ensure JavaFX dependencies are correctly included (your packaged app should include them).

**Problem: Command not recognized**  
Solution: Check your spelling and format. Commands are case-insensitive.
---
