package duet.task;

import java.util.ArrayList;

/**
 * Represents a class to maintain task list.
 * It contains most methods found in the Task class.
 * 
 * @author: Loh Wei Hung
 */
public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks != null ? tasks : new ArrayList<>();
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public void remove(int idx) {
        tasks.remove(tasks.size() - 1);
    }

    public void markTask(int idx) {
        tasks.get(tasks.size() - 1).markAsDone();;
    }

    public void unmarkTask(int idx) {
        tasks.get(tasks.size() - 1).unmarkAsDone();
    }

    public int size() {
        return tasks.size();
    }

    public Task get(int idx) {
        return tasks.get(idx);
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
