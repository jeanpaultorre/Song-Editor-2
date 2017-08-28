package cs3500.music.provider.view;

import java.util.Set;

import cs3500.music.provider.model.INote;

/**
 * Represents a way to view a music editor.
 */
public interface IMusicEditorView {
  /**
   * Displays the music.
   */
  void start();

  /**
   * Constructs the view using the given notes.
   */
  void createView(Set<INote> notes);
}
