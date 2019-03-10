import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;

import java.io.IOException;

public class ConsoleAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {

        //Get all the required data from data keys
        final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        CaretModel caretModel = editor.getCaretModel();
        String selectedText = caretModel.getCurrentCaret().getSelectedText();
        if(selectedText == null) {
            selectedText = "";
        }

        String [] lines = selectedText.split("\n");

        final Project project = e.getProject();
        //Access document, caret, and selection
        final Document document = editor.getDocument();
        final SelectionModel selectionModel = editor.getSelectionModel();

        final int start = selectionModel.getSelectionStart();
        final int end = selectionModel.getSelectionEnd();

        ObjectMapper mapper = new ObjectMapper();

        StringBuilder prettyLines = new StringBuilder();

        for(String line: lines) {
            String r;

            try {
                Object json = mapper.readValue(line, Object.class);
                r = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
            } catch (JsonProcessingException e1) {
                r = selectedText;
            } catch (IOException e1) {
                r = selectedText;
            }

            prettyLines.append('\n').append(r);
        }

        final String replacement = prettyLines.toString();

        //Making the replacement
        WriteCommandAction.runWriteCommandAction(project, () ->
                document.replaceString(start, end, replacement)
        );
        selectionModel.removeSelection();

    }
}
