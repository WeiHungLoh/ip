package duet.task;

import duet.exception.EmptyInputException;
import duet.exception.InvalidInputException;

/**
 * Encapsulates an event added by user through Duet chatbot.
 *
 * @author: Loh Wei Hung
 */
public class Event extends Task {
    protected String from;
    protected String to;

    /**
     * Creates an event with a description, start time and end time.
     *
     * @param description The description of event.
     * @param from The start time of event.
     * @param to The end time of event.
     * @throws EmptyInputException If description is empty.
     * @throws InvalidInputException If start or end time is empty.
     */
    public Event(String description, String from, String to) throws EmptyInputException, InvalidInputException {
        super(description);

        if (description == "") {
            throw new EmptyInputException("The description cannot be empty.");
        }

        if (from == "" || to == "") {
            throw new InvalidInputException("Invalid event command.");
        }

        this.from = from;
        this.to = to;
    }

    /**
     * Return the string representation of this event.
     *
     * @return A string consists of description, dates and time of from and to.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
