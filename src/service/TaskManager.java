package service;

import exception.TaskNotFoundException;
import model.Priority;
import model.Status;
import model.Task;

import java.util.*;
import java.util.stream.Collectors;

public class TaskManager {

    private final List<Task> tasks;
    private int nextId;

    public TaskManager(){
        tasks = new ArrayList<>();
        nextId = 1;
    }

    public Task addTask(String title, Priority priority){
        Task task = new Task(this.nextId, title, priority);
        nextId++;
        tasks.add(task);
        return task;

    }

    public void removeTask(int id){
        Task task = findById(id);
        tasks.remove(task);
    }


    public Task findById(int id){
       return tasks.stream()
               .filter(task -> task.getId() == id)
               .findFirst()
               .orElseThrow(() -> new TaskNotFoundException("Задача с id " + id + " не найдена"));
    }

    public void completeTask(int id){
        Task task = findById(id);
        task.markDone();
    }

    public List<Task> getAllTasks(){
        return new ArrayList<>(tasks);
    }

    public List<Task> getByStatus(Status status){
        return tasks.stream()
                .filter( task -> task.getStatus() == status)
                .collect(Collectors.toList());
    }

    public List<Task> getByPriority(Priority priority){
        return tasks.stream()
                .filter( task -> task.getPriority() == priority)
                .collect(Collectors.toList());
    }

    public List<Task> getCompleted(){
        return getByStatus(Status.DONE);
    }

    public List<Task> getPending(){
        return getByStatus(Status.TODO);
    }

    public List<Task> sortedByPriority(){
        return tasks.stream()
                .sorted(Comparator.comparingInt((Task task) -> task.getPriority().getWeight()).reversed())
                .collect(Collectors.toList());
    }

    public List<Task> sortedByDate(){
        return tasks.stream()
                .sorted(Comparator.comparingLong((Task task) -> task.getCreatedAt()))
                .collect(Collectors.toList());
    }

    public void loadTasks(List<Task> loaded){
        tasks.addAll(loaded);
        nextId = tasks.stream().mapToInt(Task::getId).max().orElse(0) + 1;
    }


}
