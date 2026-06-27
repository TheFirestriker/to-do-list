import lombok.*;

@AllArgsConstructor
public class SearchByPriority implements ToDoPredicate {
    private int searchedprio;

    @Override
    public boolean test(ToDo element) {
        return element.getPriority() == searchedprio;
    }
}
