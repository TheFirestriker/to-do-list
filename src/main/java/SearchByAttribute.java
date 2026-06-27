import lombok.*;

import java.util.Objects;

@AllArgsConstructor
public class SearchByAttribute implements ToDoPredicate {
    private String searchedAttribute;

    @Override
    public boolean test(ToDo element) {
        if (element.getAttributes() != null) {
            for (String attribut : element.getAttributes()) {
                if (attribut.equalsIgnoreCase(searchedAttribute)) {
                    return true;
                }
            }
        }
        return false;
    }
}
