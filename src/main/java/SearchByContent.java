import lombok.*;

import java.util.Objects;

@AllArgsConstructor
public class SearchByContent implements ToDoPredicate{
    private String searchedContent;
    @Override
    public boolean test(ToDo element) {
        return element.getContent().toLowerCase().contains(searchedContent.toLowerCase());
    }
}
