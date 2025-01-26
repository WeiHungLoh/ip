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
