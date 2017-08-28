package cs3500.music.provider.model;

import cs3500.music.model.Pitch;

/**
 * Represents a note.
 */
public interface INote {

  /**
   * Returns the octave of the note, as a number.
   *
   * @return the octave of the note.
   */
  int getOctave();

  /**
   * Returns the pitch of the note.
   *
   * @return the pitch of the note.
   */
  Pitch getPitch();

  /**
   * Returns the starting beat of the note.
   *d
   * @return the starting beat of the note.
   */
  int getStartBeat();

  /**
   * Returns the duration of the note.
   *
   * @return the duration of the note.
   */
  int getDuration();

  /**
   * Represents the note's octave and pitch as an integer (octave*12 + pitch).
   *
   * @return the note's octave and pitch as a single integer.
   */
  int getNoteNumber();

  /**
   * The following method were added for assignment 6, for the midi view.
   */

  /**
   * Returns the Instrument number for the note.
   *
   * @return the instrument number for the note.
   */
  int getInstrument();

  /**
   * Returns the volume of the note.
   *
   * @return the volume of the note.
   */
  int getVolume();
}
