package cs3500.music.provider.view;

import java.util.Set;

import cs3500.music.provider.model.INote;
import cs3500.music.provider.util.NoteOperations;

/**
 * Represents a textual view of the piece, on the console.
 */
public class ConsoleView implements IMusicEditorView {
  private String output;

  /**
   * Creates a ConsoleView.
   */
  public ConsoleView() {
    this.output = "";
  }

  @Override
  public void start() {
    System.out.print(output);
  }

  @Override
  public void createView(Set<INote> notes) {
    NoteOperations no = new NoteOperations(notes);
    output = no.toString();
  }
}
