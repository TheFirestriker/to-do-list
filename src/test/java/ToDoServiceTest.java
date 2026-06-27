import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class ToDoServiceTest {
    private ToDoService service;
    private final String testPath = "src/test/Todo_test.txt";

    @BeforeEach
    public void setUp() {
        service = new ToDoService(testPath);
        service.getToDoList().clear();
    }

    // Standardfälle
    @Test
    public void testAddAndSearchByContent() {
        service.addToDo(1, "Hausaufgabe fertigstellen", "Uni", "AS");
        ArrayList<ToDo> result = service.searchByGetList(new SearchByContent("Hausaufgabe"));

        assertEquals(1, result.size(), "Es sollte genau ein To-Do gefunden werden.");
        assertEquals("Hausaufgabe fertigstellen", result.get(0).getContent());
    }

    @Test
    public void testSearchByAttribute() {
        service.addToDo(2, "Hausaufgabe 1", "AS");
        service.addToDo(1, "Einkaufen", "Privat");
        ArrayList<ToDo> result = service.searchByGetList(new SearchByAttribute("AS"));

        assertEquals(1, result.size());
        assertEquals("Hausaufgabe 1", result.get(0).getContent());
    }

    @Test
    void testChangeStatus() {
        service.addToDo(1, "Laufen gehen");
        ToDo todo = service.getToDoList().get(0);

        assertFalse(todo.isChecked(), "Zu Beginn sollte das To-Do offen sein.");
        assertNull(todo.getCompletionDate(), "Kein Datum, weil noch nicht erldedigt");

        todo.changeStatus();
        assertTrue(todo.isChecked(), "Nach dem Ändern muss es gechecked sein.");
        assertNotNull(todo.getCompletionDate(), "Das Erledigt-Datum darf nicht null sein.");
    }

    // Grenzfälle
    @Test
    public void testSearchByAttribute_NoExistingAttribute() {
        // Grenzfall: Attribut existiert in keinem To-Do
        service.addToDo(3, "hallo", "begruessung");
        ArrayList<ToDo> result = service.searchByGetList(new SearchByAttribute("ExistiertNicht"));

        assertTrue(result.isEmpty(), "Die Liste müsste leer sein, weil nach einem nicht existierendem Attribut gesucht wird.");
    }

    @Test
    public void testSearchByAttribute_EmptyList() {
        // Grenzfall: Suche auf einer leeren To-Do-Liste ausführen
        ArrayList<ToDo> result = service.searchByGetList(new SearchByAttribute("AS"));

        assertTrue(result.isEmpty(), "Die Suche auf einer leeren Liste muss eine leere Ergebnisliste liefern.");
    }
}