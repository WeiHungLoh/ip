package duet.task;

import duet.exception.EmptyInputException;

/**
 * Encapsulates a ToDo task added by user through Duet chatbot.
 *
 * @author: Loh Wei Hung
 */
public class ToDo extends Task {
    /**
     * Creates a todo task with a description.
     *
     * @param description The description of task.
     * @throws EmptyInputException If description is empty.
     */
    public ToDo(String description) throws EmptyInputException {
        super(description);

        if (description == "") {
            throw new EmptyInputException(description);
        }
    }

    /**
     * Return the string representation of a Todo task.
     *
     * @return A string consists of ToDo description.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
