/**
 * Encapsulates a ToDo task added by user through Duet chatbot
 * 
 * @author: Loh Wei Hung
 */
public class ToDo extends Task{
    public ToDo(String description) {
        super(description);
    }

    /**
     * Return the string representation of a Todo task
     * 
     * @return A string consists of ToDo description
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
