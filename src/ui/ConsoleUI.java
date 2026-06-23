package ui;
import exception.*;
import repository.*;
import model.*;
import service.TaskManager;
import java.util.*;

public class ConsoleUI {
    private final TaskManager manager;
    private final TaskRepository repository;
    private final Scanner scanner;

    public ConsoleUI(TaskManager manager, TaskRepository repository){
        this.manager = manager;
        this.repository = repository;
        this.scanner = new Scanner(System.in);
    }


    public void run(){
        List<Task> loaded = repository.load();
        manager.loadTasks(loaded);
        System.out.println("Загружено задач: " + loaded.size());

        while (true) {
            printMenu();   // показать меню
            String command = scanner.nextLine();

            switch (command) {
                case "1" -> addTask();        // добавить
                case "2" -> showAllTasks();   // показать все
                case "3" -> completeTask();   // выполнить
                case "4" -> removeTask();     // удалить
                case "5" -> showByPriority(); // сортировка по приоритету
                case "0" -> {
                    save();                   // сохранить перед выходом
                    System.out.println("До свидания!");
                    return;                   // выход из run
                }
                default -> System.out.println("Неизвестная команда");
            }
        }
    }


    private void printMenu(){
        System.out.println("\n=== Менеджер задач ===");
        System.out.println("1 - Добавить задачу");
        System.out.println("2 - Показать все задачи");
        System.out.println("3 - Отметить выполненной");
        System.out.println("4 - Удалить задачу");
        System.out.println("5 - Показать по приоритету");
        System.out.println("0 - Выход");
        System.out.print("Выберите действие: ");
    }

    private void addTask(){
        System.out.print("Название задачи: ");
        String title = scanner.nextLine();

        System.out.print("Приоритет (LOW/MEDIUM/HIGH): ");
        String priorityInput = scanner.nextLine();

        try {
            Priority priority = Priority.valueOf(priorityInput.toUpperCase());
            Task task = manager.addTask(title, priority);
            System.out.println("Добавлена: " + task);
        } catch (InvalidTaskException e) {
            System.out.println("Ошибка: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Неверный приоритет. Используйте LOW, MEDIUM или HIGH");
        }
    }

    private void showAllTasks(){
        List<Task> tasks = manager.getAllTasks();
        if (tasks.isEmpty()) {
            System.out.println("Задач пока нет");
        } else {
            tasks.forEach(System.out::println);
        }
    }


    private void completeTask(){
        System.out.print("ID задачи для выполнения: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            manager.completeTask(id);
            System.out.println("Задача отмечена выполненной");
        } catch (NumberFormatException e) {
            System.out.println("ID должен быть числом");
        } catch (TaskNotFoundException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void removeTask(){
        System.out.print("ID задачи для удаления: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            manager.removeTask(id);
            System.out.println("Задача удалена");
        } catch (NumberFormatException e) {
            System.out.println("ID должен быть числом");
        } catch (TaskNotFoundException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }



    private void showByPriority(){
        List<Task> sorted = manager.sortedByPriority();
        if (sorted.isEmpty()) {
            System.out.println("Задач пока нет");
        } else {
            sorted.forEach(System.out::println);
        }
    }

    private void save(){
        repository.save(manager.getAllTasks());
        System.out.println("Задачи сохранены");
    }

}
