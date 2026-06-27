import lombok.*;

import java.time.*;
import java.util.Arrays;

@Getter
@AllArgsConstructor
public class ToDo {
    private @Setter boolean checked;
    private @Setter int priority;
    private LocalDate completionDate;
    private LocalDate creationDate;
    private String content;
    private String[] attributes;


    public ToDo(boolean checked, int priority, LocalDate creationDate, String content) {
        this.checked = checked;
        this.priority = priority;
        this.creationDate = creationDate;
        this.content = content;
    }

    public ToDo(boolean checked, int priority, LocalDate creationDate, String content, String... attribut) {
        this.checked = checked;
        this.priority = priority;
        this.creationDate = creationDate;
        this.content = content;
        this.attributes = attribut;
    }

    public void changeStatus() {
        this.checked = !this.checked;
        this.completionDate = LocalDate.now();
    }

    @Override
    public String toString() {
        if (checked) {
            if (attributes != null) {
                return priority + ". (x)" + " erstellt am: " + creationDate + " | erledigt am: " + completionDate + " | " + content + " +" + Arrays.toString(attributes);
            } else {
                return priority + ". (x)" + " erstellt am: " + creationDate + " | erledigt am: " + completionDate + " | " + content;
            }
        } else {
            if (attributes != null) {
                return priority + ". ( )" + " erstellt am: " + creationDate + " | " + content + " +" + Arrays.toString(attributes);
            } else {
                return priority + ". ( )" + " erstellt am: " + creationDate + " | " + content;
            }
        }
    }

}
