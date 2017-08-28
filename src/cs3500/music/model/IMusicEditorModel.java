package cs3500.music.model;

import java.util.List;

import cs3500.music.model.repeat.Repeat;

/**
 * Created by jeanpaul on 3/1/2017.
 */

/**
 * Interface for the Music Model used in the Model.
 */
public interface IMusicEditorModel {

  /**
   * adds a new note to this model.
   */
  void addNote(Note n);

  /**
   * Removes a note from this model.
   *
   * @param note a Note to be removed.
   */
  void removeNote(Note note);

  /**
   * Gets the current State of the model.
   *
   * @return a String version of the state of the model.
   */
  String getState();

  /**
   * Combines two Model's List of Notes simultaneously.
   *
   * @param model a MusicEditorModelImpl.
   */
  void combineS(MusicEditorModelImpl model);


  /**
   * Combines two Model's List of Notes consecutively.
   *
   * @param model a MusicEditorModelImpl.
   */
  void combineC(MusicEditorModelImpl model);

  /**
   * Gets this LoNote.
   *
   * @return a List of Notes.
   */
  List<Note> getLoNote();

  /**
   * Returns length of beat.
   *
   * @return the Length of the beat.
   */
  int beatLength();

  /**
   * @return A list of String that signify the range in the Notes.
   */
  List<String> range();

  /**
   * The tempo.
   *
   * @return the tempo of the model.
   */
  int getTempo();

  /**
   * Gets the Notes that start at the given Note location in the Composition.
   * @param currentNote the currentNote that is playing.
   * @return a List of Notes that start at the given Note Location.
   */
  List<Note> getNotesThatStartAt(int currentNote);

  /**
   * Gets the current beat of the composition.
   * @return the current beat.
   */
  int getCurrentBeat();

  /**
   * Gets the currentNotes based on the currentBeat.
   * @param currentBeat the currentBeat of the model.
   * @return list of notes that are at the current Beat.
   */
  List<Note> getCurrentNotes(int currentBeat);

  /**
   * Updates the tempo of the model.
   * @param change the amount of change that the tempo will intake.
   */
    void updateTempo(int change);

  /**
   * Gets the list of Repeats.
   * @return a list of Repeats.
   */
  List<Repeat> loRepeat();

  List<Repeat> loEndings();
  }

