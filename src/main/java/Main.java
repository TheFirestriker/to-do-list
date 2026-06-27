import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    /*
     * 1. alle To-dos ausgeben,
     * 2. nur offe To-dos ausgeben,
     * 3. nach To-dos suchen (im Text oder in den Attributen des To-dos) und diese ausgeben,
     * 4. den Status von einem To-do setzen (z. B. auf ”fertig“),
     * 5. nach To-dos suchen und allen gefundenen To-dos einen Status geben (z. B. alle To-dos mit dem Attribut ”AS“ denStatus ”dringend“ geben) sowie
     * 6. ein neues To-do erstellen.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ToDoService service = new ToDoService("src/main/Todo.txt");
        boolean status = true;
        boolean loaded = false;

        while (status) {
            System.out.println("\n--- Hauptmenü: ---");
            System.out.println("0. Alle To-Dos aus .txt Datei laden");
            System.out.println("1. Alle To-Dos ausgeben");
            System.out.println("2. Alle offene To-Dos ausgeben");
            System.out.println("3. Nach To-Dos suchen");
            System.out.println("4. To-Do Status ändern");
            System.out.println("5. Prioritätsänderung nach Attributen");
            System.out.println("6. Neues To-Do");
            System.out.println("7. Programm beenden");
            System.out.println("\nWählen Sie eine Option von 0 bis 7:");
            byte answer = 0;
            try {
                answer = Byte.parseByte(scanner.nextLine());
                switch (answer) {
                    case 0:
                        if (!loaded){
                            service.loadToDoFromTxt();
                            loaded = true;
                        } else {
                            System.out.println("To-Dos bereits geladen!");
                        }
                        break;
                    case 1:
                        System.out.println("--- Alle To-DOs ---");
                        service.getAllTodos();
                        break;
                    case 2:
                        System.out.println("--- Alle offenen To-DOs ---");
                        service.getAllUncheckedTodos();
                        break;
                    case 3:
                        service.search(scanner);
                        break;
                    case 4:
                        service.changeStatus(scanner);
                        break;
                    case 5:
                        service.searchAndChangePrio(scanner);
                        break;
                    case 6:
                        service.add(scanner);
                        break;
                    case 7:
                        status = false;
                        break;
                    default:
                        throw new RuntimeException();
                }
            } catch (NumberFormatException | InputMismatchException | IndexOutOfBoundsException e) {
                System.err.println("Gebe bitte eine gültige Wert ein!!!");
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        scanner.close();
    }
}
