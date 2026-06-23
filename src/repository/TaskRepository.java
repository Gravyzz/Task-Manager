package repository;
import model.*;
import java.nio.file.*;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;



public class TaskRepository {

    private final String fileName;

    public TaskRepository(String fileName){
        this.fileName = fileName;
    }

    public void save(List<Task> tasks){
        List<String> lines = tasks.stream()
                .map(task -> task.getId() + ";" + task.getTitle() + ";"
                        + task.getPriority().name() + ";" + task.getStatus().name()
                        + ";" + task.getCreatedAt())
                .collect(Collectors.toList());

        try {
            Files.write(Paths.get(fileName), lines);
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении: " + e.getMessage());
        }
    }

    public List<Task> load(){
        List<Task> tasks = new ArrayList<>();
        if (!Files.exists(Paths.get(fileName))) {
            return tasks;
        }

        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            for (String line : lines) {
                String[] parts = line.split(";");
                int id = Integer.parseInt(parts[0]);
                String title = parts[1];
                Priority priority = Priority.valueOf(parts[2]);
                Status status = Status.valueOf(parts[3]);
                long createdAt = Long.parseLong(parts[4]);

                Task task = new Task(id, title, priority, status, createdAt);  // ВТОРОЙ конструктор!
                tasks.add(task);
            }

        } catch (IOException e) {
            System.out.println("Ошибка при загрузке: " + e.getMessage());
        }



        return tasks;
    }

}
