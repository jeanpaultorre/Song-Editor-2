package cs3500.music.adapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.MusicEditorModelImpl;
import cs3500.music.model.Note;
import cs3500.music.model.Pitch;
import cs3500.music.provider.model.IMusicModel;
import cs3500.music.provider.model.INote;

/**
 * Created by jeanpaul on 4/13/2017.
 */

/**
 * The adapter for the provider's code.
 * Implements the provider's Model Interface.
 */
public class AdapterImpl implements IMusicModel {
  private IMusicEditorModel model;

  /**
   * Constructor for the Adapter. It takes in a field of my Model.
   *
   * @param model my model.
   */
  public AdapterImpl(IMusicEditorModel model) {
    this.model = model;
  }

  /**
   * gets the Set of Notes from the composition.
   * I had a seimilar method in my model. The only difference is that the provider wanted a return
   * type of Set and mine outputed a List.
   * I first called the method from my model. I then traversed the list and convert my Note to an
   * INote and add this note to the Set.
   */
  @Override
  public Set<INote> getNotes() {
    List<Note> loNote = model.getLoNote();
    Set<INote> setNote = new HashSet<>();
    for (int i = 0; i < loNote.size(); i++) {
      INote note = new NoteAdapterImpl(loNote.get(i));
      setNote.add(note);
    }
    return setNote;
  }


  /**
   * Adds a note to the model.
   * The parameter in this method is an INote so i had to convert the INote to a Note that would
   * work with my model.
   *
   * @param note the note to be added.
   */
  @Override
  public void addNote(INote note) {
    int duration = note.getDuration();
    int octave = note.getOctave();
    int start = note.getStartBeat();
    int instrument = note.getInstrument();
    int volume = note.getVolume();
    Pitch pitch = note.getPitch();
    model.addNote(new Note(duration, instrument, volume, pitch, octave, start));
  }

  /**
   * Removes a note to the model.
   * The parameter in this method is an INote so i had to convert the INote to a Note that would
   * work with my model.
   *
   * @param note the note to be removed.
   */
  @Override
  public void removeNote(INote note) {
    int duration = note.getDuration();
    int octave = note.getOctave();
    int start = note.getStartBeat();
    int instrument = note.getInstrument();
    int volume = note.getVolume();
    Pitch pitch = note.getPitch();
    model.removeNote(new Note(duration, instrument, volume, pitch, octave, start));
  }

  /**
   * @param oldNote the note to be replaced.
   * @param newNote the note to replace it with.
   */
  @Override
  public void replaceNote(INote oldNote, INote newNote) {
    throw new IllegalArgumentException("Not a method that was required for the assignments " +
            "therefore I do not have an equivalent method in my model");
  }

  /**
   * I had a similar method in my model. Had to convert it in a way so that it would work on my
   * model as well.
   *
   * @param piece the piece to combine with this piece.
   */
  @Override
  public void addPieceOver(IMusicModel piece) {
    int tempo = piece.getTempo();
    List<Note> loNote = convertToListNote(piece);
    model.combineS(new MusicEditorModelImpl(loNote, tempo));
  }

  /**
   * I had a similar method in my model. Had to convert it in a way so that it would work on my
   * model as well.
   *
   * @param piece the piece to play after this one.
   */
  @Override
  public void addPieceAfter(IMusicModel piece) {
    int tempo = piece.getTempo();
    List<Note> loNote = convertToListNote(piece);
    model.combineC(new MusicEditorModelImpl(loNote, tempo));
  }

  /**
   * I did not need this in my model.
   *
   * @param tempo the tempo of the piece.
   */
  @Override
  public void setTempo(int tempo) {
    throw new IllegalArgumentException("Do not need");
  }

  /**
   * gets the tempo of the model. Similar method in my model.
   *
   * @return the current tempo.
   */
  @Override
  public int getTempo() {
    return model.getTempo();
  }

  /**
   * Converts the provider's INote to one that is compatible with my model. Then creates a list
   * of Notes from the provider's set of INote.
   *
   * @param piece the provider's model interface.
   * @return a List of Notes.
   */
  private List<Note> convertToListNote(IMusicModel piece) {
    Set<INote> notes = piece.getNotes();
    List<INote> setNotes = new ArrayList<>(); //List of INote //Need to convert to Note
    setNotes.addAll(notes);
    List<Note> loNote = new ArrayList<>();
    for (int i = 0; i < setNotes.size(); i++) {
      int duration = setNotes.get(i).getDuration();
      int octave = setNotes.get(i).getOctave();
      int start = setNotes.get(i).getStartBeat();
      int instrument = setNotes.get(i).getInstrument();
      int volume = setNotes.get(i).getVolume();
      Pitch pitch = setNotes.get(i).getPitch();
      loNote.add(new Note(duration, instrument, volume, pitch, octave, start));
    }
    return loNote;
  }
}


