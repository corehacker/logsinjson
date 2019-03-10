import com.intellij.execution.filters.ConsoleDependentFilterProvider;
import com.intellij.execution.filters.Filter;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.project.Project;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NotNull;

public class Filters extends ConsoleDependentFilterProvider {

    @NotNull
    @Override
    public Filter[] getDefaultFilters(@NotNull ConsoleView consoleView,
                                      @NotNull Project project,
                                      @NotNull GlobalSearchScope globalSearchScope) {

        return new Filter[]{ new JSONFilter() };

    }

}