package duet.task;

import duet.exception.EmptyInputException;
import duet.exception.InvalidInputException;

/**
 * Encapsulates a deadline added by user through Duet chatbot.
 *
 * @author: Loh Wei Hung
 */
public class Deadline extends Task {
    protected String by;

    /**
     * Creates a deadline task with a description and by deadline time.
     *
     * @param description The description of deadline task.
     * @param by The end time of deadline.
     * @throws EmptyInputException If description is empty.
     * @throws InvalidInputException If by is empty.
     */
    public Deadline(String description, String by) throws EmptyInputException, InvalidInputException {
        super(description);

        if (description == "") {
            throw new EmptyInputException(("The description of the deadline cannot be empty."));
        }

        if (by == "") {
            throw new InvalidInputException("Invalid deadline command.");
        }

        this.by = by;
    }

    /**
     * Return the string representation of a deadline.
     *
     * @return A string consists of deadline description.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
