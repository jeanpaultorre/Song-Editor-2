package cs3500.music.adapter;

import cs3500.music.model.Note;
import cs3500.music.model.Pitch;
import cs3500.music.provider.model.INote;

/**
 * Created by jeanpaul on 4/13/2017.
 */

/**
 * Adapter for the Notes. Implements the provider's INote.
 */
public class NoteAdapterImpl implements INote {
  private Note note;

  /**
   * Constructor for the Adapter. Takes in a Note (my note class).
   *
   * @param note the Note representation from my model.
   */
  public NoteAdapterImpl(Note note) {
    this.note = note;
  }

  /**
   * gets the octave of this Note.
   *
   * @return octave.
   */
  @Override
  public int getOctave() {
    return note.getOctave();
  }

  /**
   * gets the Pitch of this Note.
   *
   * @return Pitch.
   */
  @Override
  public Pitch getPitch() {
    return note.getPitch();
  }

  /**
   * gets the startBeat of this Note.
   *
   * @return startBeat..
   */
  @Override
  public int getStartBeat() {
    return note.getStartBeat();
  }

  /**
   * gets the Duration of this Note.
   *
   * @return Duration.
   */
  @Override
  public int getDuration() {
    return note.getDuration();
  }

  /**
   * gets the NoteNumber of this Note.
   *
   * @return the midiValue equivalent.
   */
  @Override
  public int getNoteNumber() {
    return note.midiValue();
  }

  /**
   * gets the Instrument of this Note.
   */
  @Override
  public int getInstrument() {
    return note.getInstrument();
  }

  /**
   * gets the Volume of this Note.
   *
   * @return Volume.
   */
  @Override
  public int getVolume() {
    return note.getVolume();
  }

  /**
   * returns this Note.
   *
   * @return this Note.
   */
  public Note getNote() {
    return note;
  }
}

