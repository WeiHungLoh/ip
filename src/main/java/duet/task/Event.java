package duet.task;

/**
 * Encapsulates an event added by user through Duet chatbot.
 * 
 * @author: Loh Wei Hung
 */
public class Event extends Task {
    protected String from;
    protected String to;

    public Event(String description, String from, String to) {
        super(description);
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
