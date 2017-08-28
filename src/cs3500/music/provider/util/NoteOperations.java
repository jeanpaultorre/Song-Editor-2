package cs3500.music.provider.util;


import java.util.Set;

import cs3500.music.provider.model.INote;
import cs3500.music.provider.model.Pitch;

/**
 * Useful functions for a set of notes (highest, lowest, last, etc).
 */
public class NoteOperations {
  private final Set<INote> notes;

  /**
   * Creates a NoteOperations object with the given notes.
   *
   * @param notes the notes on which to perform operations.
   */
  public NoteOperations(Set<INote> notes) {
    this.notes = notes;
  }

  /**
   * returns the set of notes.
   *
   * @return the set of notes.
   */
  public Set<INote> getNotes() {
    return notes;
  }

  /**
   * Returns the note in the piece that ends last.
   *
   * @return the note in the piece that ends last, or null if there are no notes.
   */
  public INote lastNote() {
    if (notes.size() == 0) {
      return null;
    }

    INote lastNote = null;
    for (INote n: notes) {
      lastNote = n;
    }
    for (INote n: notes) {
      if (n.getStartBeat() + n.getDuration() > lastNote.getStartBeat() + lastNote.getDuration()) {
        lastNote = n;
      }
    }
    return lastNote;
  }

  /**
   * Returns the total length of the piece in beats.
   *
   * @return the total number of beats in the piece.
   */
  public int pieceLength() {
    return lastNote().getStartBeat() + lastNote().getDuration();
  }

  /**
   * Pads the beats so that they're right justified with the same width as the largest beat.
   *
   * @param beat the beat to pad.
   * @return     the padded beat
   */
  public String beatPadding(int beat) {
    String s = Integer.toString(beat);
    while (s.length() < Integer.toString(pieceLength()).length()) {
      s = " " + s;
    }
    return s;
  }

  /**
   * Returns the note in the piece with the highest octave and pitch.
   *
   * @return the highest note, or null if there are no notes.
   */
  public INote highestNote() {
    if (notes.size() == 0) {
      return null;
    }

    INote highest = null;
    for (INote n : notes) {
      highest = n;
    }
    for (INote n : notes) {
      if (n.getNoteNumber() > highest.getNoteNumber()) {
        highest = n;
      }
    }
    return highest;
  }

  /**
   * Returns the note in the piece with the lowest octave and pitch.
   *
   * @return the lowest note, or null if there are no notes.
   */
  public INote lowestNote() {
    if (notes.size() == 0) {
      return null;
    }

    INote lowest = null;
    for (INote n : notes) {
      lowest = n;
    }
    for (INote n : notes) {
      if (n.getNoteNumber() < lowest.getNoteNumber()) {
        lowest = n;
      }
    }
    return lowest;
  }

  /**
   * Returns true if there exists in the piece a note with the given noteNumber,
   * which starts at the given beat. If not, returns false.
   *
   * @param noteNumber the pitch of the note to look for.
   * @param beat       the beat at which the note must start.
   * @return true if a note starts at that pitch and beat, false if not.
   */
  public boolean noteStarts(int noteNumber, int beat) {
    for (INote n : notes) {
      if (n.getNoteNumber() == noteNumber && n.getStartBeat() == beat) {
        return true;
      }
    }
    return false;
  }

  /**
   * Returns true if a note is playing with the given noteNumber, at the given beat. False if not.
   *
   * @param noteNumber The pitch to find a note at.
   * @param beat       The beat the note must be playing during.
   * @return           True if a note is playing at the given beat and pitch, false if not.
   */
  public boolean noteDuring(int noteNumber, int beat) {
    for (INote n : notes) {
      if (n.getNoteNumber() == noteNumber
              && n.getStartBeat() <= beat
              && n.getStartBeat() + n.getDuration() > beat) {
        return true;
      }
    }
    return false;
  }

  @Override
  public String toString() {
    // the string to return
    String result = "";

    if (notes.size() == 0) {
      return result;
    }
    else {
      //padding on the left hand side of the header
      String leftPadding = "";
      for (int ii = 0; ii < Integer.toString(this.pieceLength()).length(); ii++) {
        leftPadding += " ";
      }
      result += leftPadding;

      //column headers
      for (int ii = this.lowestNote().getNoteNumber();
           ii <= this.highestNote().getNoteNumber();
           ii++) {

        String pitchOctave;
        if (ii < 0 || ii % 12 == 0) {
          pitchOctave = Pitch.fromNumber(ii % 12).toString()
                  + (((ii - (ii % 12)) / 12) - 1);
        } else {
          pitchOctave = Pitch.fromNumber(ii % 12).toString()
                  + ((ii - (ii % 12)) / 12);
        }

        switch (pitchOctave.length()) {
          case 2:
            result += "  " + pitchOctave + " ";
            break;
          case 3:
            result += " " + pitchOctave + " ";
            break;
          case 4:
            result += " " + pitchOctave;
            break;
          default:
            result += pitchOctave;
        }
      }

      // write each line
      for (int ii = 0; ii < this.pieceLength(); ii++) {
        result += "\n";
        result += this.beatPadding(ii);

        // for each column, if a note starts put an X, if a note continues put a |, otherwise space.
        for (int jj = this.lowestNote().getNoteNumber();
             jj <= this.highestNote().getNoteNumber();
             jj++) {
          if (this.noteStarts(jj, ii)) {
            result += "  X  ";
          } else if (this.noteDuring(jj, ii)) {
            result += "  |  ";
          } else {
            result += "     ";
          }
        }

      }
      return result;
    }
  }
}
