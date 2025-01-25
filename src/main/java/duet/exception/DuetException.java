package duet.exception;
/**
 * Represents an exception specifically for Duet chatbot.
 * This exception handles errors that occur when a user gives an invalid command.  
 * 
 * @author: Loh Wei Hung
 */
public class DuetException extends Exception {
    /** Description of error message */
    protected String description;

    /**
     * Creates a new DukeException with a particular error description.
     * 
     * @param description The error description for cause of error.
     */
    public DuetException(String description) {
        super(description);
    }

    /**
     * Returns a string representation of cause of error.
     * 
     * @return A string consists of error message.
     */
    @Override
    public String getMessage() {
        return "Duet encountered an error: " + super.getMessage();
    }
}
