package duet;

import org.junit.jupiter.api.Test;

import duet.exception.EmptyInputException;
import duet.exception.InvalidInputException;
import duet.task.Event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class EventTest {
    @Test
    public void createNewEvent_success() throws EmptyInputException, InvalidInputException {
        assertEquals("[E][ ] do homework (from: 2020-02-02 to: 2021-02-02)", 
                new Event("do homework", "2020-02-02", "2021-02-02").toString());
    }

    @Test
    public void createNewEvent_exceptionThrown() throws EmptyInputException {
           try {
            assertEquals("", new Event("do homework", "", ""));
            fail(); // test should not reach this line
        } catch (InvalidInputException e) {
            assertEquals("Duet encountered an error: Invalid event command.", e.getMessage());
        }
    }
}