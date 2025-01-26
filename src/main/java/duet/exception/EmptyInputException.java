package duet.exception;

/**
 * Represents an exception that is thrown when no input is provided.
 *
 * @author: Loh Wei Hung
 */
public class EmptyInputException extends DuetException {
    /** Description of error message */
    protected String description;

    /**
     * Creates an EmptyInputException with error message.
     * 
     * @param description A specified error message.
     */
    public EmptyInputException(String description) {
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