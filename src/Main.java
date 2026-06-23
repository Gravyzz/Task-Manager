import exception.TaskNotFoundException;
import repository.*;
import model.*;
import service.TaskManager;
import java.util.*;
import ui.*;

public class Main {
    public static void main(String[] args) {

        TaskManager manager = new TaskManager();
        TaskRepository repository = new TaskRepository("tasks.txt");
        ConsoleUI ui = new ConsoleUI(manager, repository);
        ui.run();


    }

}
