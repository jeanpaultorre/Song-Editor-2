package cs3500.music.provider.model;

import java.util.Set;

/**
 * Representation of a single musical piece.
 */
public interface IMusicModel {

  /**
   * Returns the set of notes in the piece.
   */
  Set<INote> getNotes();

  /**
   * Adds the given note to the piece.
   *
   * @param note the note to be added.
   */
  void addNote(INote note);

  /**
   * If the given note is in the piece, remove it. Otherwise, do nothing.
   *
   * @param note the note to be removed.
   */
  void removeNote(INote note);

  /**
   * If the first note is in the piece, replace it with the second one. Otherwise, do nothing.
   *
   * @param oldNote the note to be replaced.
   * @param newNote the note to replace it with.
   */
  void replaceNote(INote oldNote, INote newNote);

  /**
   * Combines this piece with the given piece, so that they play at the same time.
   *
   * @param piece the piece to combine with this piece.
   */
  void addPieceOver(IMusicModel piece);

  /**
   * Appends a given piece to the end of this one, so that they play consecutively.
   *
   * @param piece the piece to play after this one.
   */
  void addPieceAfter(IMusicModel piece);

  /**
   * Sets the tempo of the piece to the given number.
   * @param tempo the tempo of the piece.
   */
  void setTempo(int tempo);

  /**
   * Returns the tempo of the piece.
   * @return the tempo.
   */
  int getTempo();
}
