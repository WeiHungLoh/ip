# Duet Chatbot :+1:
A ~smart~ super *duper* smart chatbot with task management capabilities, such as creating To-Do, Deadline and Event tasks. 

## How it works:
1. Deadline, todo, event commands:
- To add a deadline task, type "deadline \<task name> /by <YYYY-MM-DD>"
- To add a todo task, type "todo \<task name>"
- To add an event task, type "event \<task name> /from <YYYY-MM-DD> /to <YYYY-MM-DD>"

2. List command:
- To view the list of tasks you have added, type "list"

3. Mark, unmark, and delete commands:
- To mark task as done, type "mark 1" where "1" is the index of task
- To unmark task, type "unmark 1" where "1" is the index of task
- To delete a task, type "delete 1" where "1" is the index of task
- To mark, unmark or delete more than 1 tasks at a time, type "mark 1,2,3", "unmark 1,2,3", "delete 1,2,3" where "1,2,3" refer to the indices of existing tasks

> For all commands above, please type in lowercase. Do include a space in between command type, task index, and task name. Failure to do so will cause Duet Chatbot to be unable to recognise your command
> Do type "list" to check the tasks you have. Marking, unmarking or deleting any tasks that you do not have will cause an error

## Examples
### Valid Commands
🙆‍♂️ `todo do homework`  
🙆‍♂️ `deadline submit homework /by 2025-05-30`  
🙆‍♂️ `event family vacation /from 2025-06-01 /to 2025-06-15`  
🙆‍♂️ `mark 1,5,6`  
🙆‍♂️ `delete 3,2,1`  

### Invalid Commands
🙅‍♂️ `TODO readbook` (uppercase not allowed)  
🙅‍♂️ `deadlinedohomework /by 2025-02-28` (no space in between command type and task name)  
🙅‍♂️ `event go overseas /from 2025-02-01` (no end date)  
🙅‍♂️ `deadline do tutorial` (no due date)  
🙅‍♂️ `event go overseas /to 2025-02-29` (no start date)  

## Quick References
📝 `todo <task name>`
⏰ `deadline <task name> /by <YYYY-MM-DD>`
📆 `event <task name> /by <YYYY-MM-DD> /to <YYYY-MM-DD>
📁 `list`
❎ `mark \<task index>`
⚪ `unmark \<task index>`
🗑️ `delete \<task index>`

## Common Error Messages
If you receive an error message:
1. Check that all commands are in lowercase
2. Verify spaces between all components
3. Ensure dates are in YYYY-MM-DD format
4. Confirm task indices exist in your list


