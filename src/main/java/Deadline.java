/**
 * Encapsulates a deadline added by user through Duet chatbot.
 * 
 * @author: Loh Wei Hung
 */
public class Deadline extends Task {
    protected String by;

    public Deadline(String description, String by) {
        super(description);
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
