

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellij.execution.filters.Filter;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class JSONFilter implements Filter {

    @Nullable
    @Override
    public Result applyFilter(String line, int entireLength) {

        String selectedText = line;

        ObjectMapper mapper = new ObjectMapper();

        String r;

        try {
            Object json = mapper.readValue(line, Object.class);
            r = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
        } catch (JsonProcessingException e1) {
            r = selectedText;
        } catch (IOException e1) {
            r = selectedText;
        }

        System.out.println(r);

        Result result = new Result();

        return null;
    }
}
