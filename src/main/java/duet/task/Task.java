package duet.task;

import duet.exception.EmptyInputException;

/**
 * The Task class represents a task added by users through Duet chatbot.
 * It encapsulates description and completion status of task.
 *
 * @author: Loh Wei Hung
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Creates a new Task with a description.
     *
     * @param description The description of task.
     * @throws EmptyInputException If description is empty.
     */
    public Task(String description) throws EmptyInputException {
        if (description == "") {
            throw new EmptyInputException("The description cannot be empty");
        }

        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns completion status of task in the form of X or empty space.
     *
     * @return Status of task completion.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Marks completion status as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks completion status as not done.
     */
    public void unmarkAsDone() {
        this.isDone = false;
    }

    /**
     * Returns description of task.
     *
     * @return Task description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Return the string description of task.
     *
     * @return A string consists of description and completion status of task.
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + this.description;
    }
}
