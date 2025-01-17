/**
 * The Task class represents a task added by users through Duet's chatbot.
 * It encapsulates description and completion status of task.
 * 
 * @author: Loh Wei Hung
 */
public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns completion status of task in the form of X or empty space.
     * 
     * @return Status of task completion.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Marks completion status as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks completion status as not done.
     */
    public void unmarkAsDone() {
        this.isDone = false;
    }

    /**
     * Returns description of task.
     * 
     * @return Task description.
     */
    public String getDescription() {
        return this.description;
    }
}
