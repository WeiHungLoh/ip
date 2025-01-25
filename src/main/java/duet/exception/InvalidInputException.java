package duet.exception;
/**
 * Represents an exception that is thrown when an invalid input is provided
 *
 * @author: Loh Wei Hung
 */
public class InvalidInputException extends DuetException {
    /** Description of error message */
    protected String description;

    /**
     * Creates an InvalidInputException with error message.
     * 
     * @param description A specified error message.
     */
    public InvalidInputException(String description) {
        super(description);
    }

    /**
     * Returns the error message when no input is provided.
     * 
     * @return A string consists of the specific error message.
     */
    @Override
    public String getMessage() {
        return super.getMessage();
    }
}