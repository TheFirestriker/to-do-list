import lombok.*;
import lombok.extern.java.Log;

import java.io.*;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Getter
@Setter
@Log
public class ToDoService {
    private final String filepath;
    private List<ToDo> toDoList = new ArrayList<>();

    public ToDoService(String filepath) {
        this.filepath = filepath;
    }

    public void loadToDoFromTxt() {
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                toDoList.add(parseToDo(line));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info(toDoList.size()+" To-Do(s) erfolgreich in ArrayList<ToDo> geladen");
    }

    /**
     * Die Nutzung von Substrings unter folgenden Quelle nachgelesen und angewendet:
     * https://www.w3schools.com/java/ref_string_substring.asp
     */
    public static ToDo parseToDo(String line) {

        int priority = Integer.parseInt(line.substring(0, line.indexOf(".")));
        boolean checked = line.contains("(x)");
        int startDate = line.indexOf("erstellt am: ") + "erstellt am: ".length();
        String creationDateString = line.substring(startDate, startDate + 10);
        LocalDate creationDate = LocalDate.parse(creationDateString);
        String content;
        String[] attributes = null;

        if (line.contains("+[")) {
            int contentStart = line.indexOf("|") + 2;
            int attributeStart = line.indexOf("+[");
            content = line.substring(contentStart, attributeStart).trim();
            String attrString = line.substring(attributeStart + 2, line.lastIndexOf("]"));
            attributes = attrString.split(", *");
        } else {
            content = line.substring(line.indexOf("|") + 2).trim();
        }

        if (attributes != null) return new ToDo(checked, priority, creationDate, content, attributes);

        return new ToDo(checked, priority, creationDate, content);
    }


    private void saveAllToDos() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filepath))) {
            for (ToDo todo : toDoList) {
                bw.write(todo.toString());
                bw.newLine();
            }
            log.info(toDoList.size() + " To-Do(s) in der Datei " + filepath + " enthalten.");
        } catch (Exception e) {
            log.severe("Fehler beim Speichern der To-Dos in die Datei: " + filepath);
            e.printStackTrace();
        }
    }

    public void addToDo(int priority, String description) {
        LocalDate now = LocalDate.now();
        ToDo todo = new ToDo(false, priority, now, description);
        toDoList.add(todo);
        log.info("To-Do (ohne Attribute) erstellt!");
        saveAllToDos();
    }

    public void addToDo(int priority, String description, String... attributes) {
        LocalDate now = LocalDate.now();
        ToDo todo = new ToDo(false, priority, now, description, attributes);
        toDoList.add(todo);
        log.info("To-Do (mit Attribute) erstellt!");
        saveAllToDos();
    }

    public void add(Scanner scanner) {
        System.out.print("To- Do: ");
        String content = scanner.nextLine();
        System.out.print("Geben Sie die Priorität ein (Zahl): ");
        int priority = Integer.parseInt(scanner.nextLine());
        System.out.println("Wollen Sie Attribute verteilen? (y/n) ");
        String ansAttribut = scanner.nextLine();
        if (ansAttribut.equalsIgnoreCase("y")) {
            System.out.println("Attribute eingeben mit + und , als Trennzeichen");
            String input = scanner.nextLine();
            String[] attribute = input.split("[+,]");
            for (int i = 0; i < attribute.length; i++) {
                attribute[i] = attribute[i].trim();
            }
            addToDo(priority, content, attribute);
        } else {
            addToDo(priority, content);
        }
        System.out.println("To-Do erfolgreich erstellt!");
    }

    public void getAllTodos() {
        if (!toDoList.isEmpty()) {
            for (ToDo todo : toDoList) {
                System.out.println(todo.toString());
            }
        } else {
            System.out.println("Keine To-Dos vorhanden.");
        }
    }

    public void getAllUncheckedTodos() {
        for (ToDo todo : toDoList) {
            if (!todo.isChecked()) {
                System.out.println(todo.toString());
            }
        }
    }

    public void changeStatus(Scanner scanner) throws IndexOutOfBoundsException {
        for (int i = 0; i < toDoList.size(); i++) {
            System.out.println(i + 1 + ")\t" + toDoList.get(i));
        }
        System.out.println("Bei welchem To Do willst du den Status wechseln? (checked <=> unchecked)");
        int res = Integer.parseInt(scanner.nextLine());
        if (res > 0 && res <= toDoList.size()) {
            toDoList.get(res - 1).changeStatus();
            log.info("Statusänderung für To-Do Element " + (res - 1) + " durchgeführt.");
            saveAllToDos();
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public void searchBy(ToDoPredicate predicate) {
        ArrayList<ToDo> res = new ArrayList<>();
        for (ToDo toDo : toDoList) {
            if (predicate.test(toDo)) {
                res.add(toDo);
                System.out.println(toDo.toString());
            }
        }
    }

    public ArrayList<ToDo> searchByGetList(ToDoPredicate predicate) {
        ArrayList<ToDo> res = new ArrayList<>();
        for (ToDo toDo : toDoList) {
            if (predicate.test(toDo)) {
                res.add(toDo);
            }
        }
        return res;
    }

    public void search(Scanner scanner) throws RuntimeException {
        System.out.println("--- Suchkriterium ---");
        System.out.println("1) Priorität");
        System.out.println("2) Content");
        System.out.println("3) Attribut");
        int searchCriteria = Integer.parseInt(scanner.nextLine());
        log.info("Suchvorgang gestartet: " + searchCriteria);

        switch (searchCriteria) {
            case 1:
                System.out.println("Welche Priorität suchst du?");
                int searchedPrio = Integer.parseInt(scanner.nextLine());
                System.out.println("\n--- To DO's der " + searchedPrio + ". Priorität ---");
                searchBy(new SearchByPriority(searchedPrio));
                break;
            case 2:
                System.out.println("Welchen Text suchst du?");
                String searchedContent = scanner.nextLine();
                System.out.println("\n--- Dieser Text ist in diesen To- Do(s) vorhanden: ---");
                searchBy(new SearchByContent(searchedContent));
                break;
            case 3:
                System.out.println("Welche Attribute suchst du?");
                String searchedAttribut = scanner.nextLine();
                System.out.println("\n--- Das Attribut wurde in diesen To- Dos vergeben ---");
                searchBy(new SearchByAttribute(searchedAttribut));
                break;
            default:
                log.warning("Ungültiges Suchkriterium eingegeben.");
                throw new RuntimeException("Ungültiges Suchkriterium eingegeben.");
        }
    }


    public void searchAndChangePrio(Scanner scanner) {
        System.out.println("Nach welchem Attribut soll gesucht werden?");
        String searchAttr = scanner.nextLine();
        ArrayList<ToDo> matches = searchByGetList(new SearchByAttribute(searchAttr));

        if (matches.isEmpty()) {
            log.info("Keine To-Dos mit dem Attribut '" + searchAttr + "' gefunden.");
            return;
        }
        System.out.println(matches.size() + " To-Do(s) gefunden. Welche neue Priorisierung soll vergeben werden?");
        int newPrio = Integer.parseInt(scanner.nextLine());
        for (ToDo todo : matches) {
            todo.setPriority(newPrio);
        }
        saveAllToDos();
        System.out.println("Statusänderung erfolgreich durchgeführt.");
        log.info("Massen-Statusänderung erfolgreich durchgeführt.");
    }
}
