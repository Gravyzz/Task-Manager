package model;
import exception.InvalidTaskException;

import model.Priority;
import model.Status;

import java.util.Objects;

public class Task {

    private final int id;
    private final String title;
    private final Priority priority;
    private final long createdAt;

    private Status status;

    public Task(int id, String title, Priority priority){
        this(id, title, priority, Status.TODO, System.currentTimeMillis());

    }

    public Task(int id, String title, Priority priority, Status status, long createdAt) {
        if (title == null || title.isBlank()) throw new InvalidTaskException("Title cannot be empty");
        if (priority == null) throw new InvalidTaskException("Priority cannot be null");
        this.id = id;
        this.title = title;
        this.priority = priority;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Priority getPriority() {
        return priority;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public Status getStatus() {
        return status;
    }

    public void markDone(){
        this.status = Status.DONE;
    }

    public boolean isDone(){
        return this.status == Status.DONE;
    }

    @Override
    public String toString(){
        return "[#" + id + "] " + title +
                " | Приоритет: " + priority.getDescription() +
                " | " + status.getDescription();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Task task)) return false;
        return getId() == task.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
