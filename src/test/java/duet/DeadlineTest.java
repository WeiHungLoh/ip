package duet;

import org.junit.jupiter.api.Test;

import duet.exception.EmptyInputException;
import duet.exception.InvalidInputException;
import duet.task.Deadline;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class DeadlineTest {
    @Test
    public void createNewDeadline_exceptionThrown() throws EmptyInputException {
        try {
            assertEquals("", new Deadline("do homework", ""));
            fail(); // test should not reach this line
        } catch (InvalidInputException e) {
            assertEquals("Duet encountered an error: Invalid deadline command.", e.getMessage());
        }
    }

    @Test
    public void createNewDeadline_success() throws EmptyInputException, InvalidInputException {
        assertEquals("[D][ ] do homework (by: 2020-02-02)", 
                new Deadline("do homework", "2020-02-02").toString());
    }
}
