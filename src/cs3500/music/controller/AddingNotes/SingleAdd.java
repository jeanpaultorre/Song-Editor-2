package cs3500.music.controller.AddingNotes;


import java.awt.*;
import java.util.TimerTask;

import javax.sound.midi.InvalidMidiDataException;

import cs3500.music.model.MusicEditorModelImpl;
import cs3500.music.model.Note;
import cs3500.music.view.CompositeView;

/**
 * Created by jeanpaul on 4/22/2017.
 */
public class SingleAdd extends TimerTask implements IAdd {
  private boolean playing;
  private CompositeView view;
  private MusicEditorModelImpl model;

  public SingleAdd(boolean playing, CompositeView view, MusicEditorModelImpl model) {
    this.playing = playing;
    this.view = view;
    this.model = model;
  }
  @Override
  public void addNotes() {
    if (!playing) {
      Point mouseLoc;
      mouseLoc = view.currentMousePosition();
      Note temp = view.currentNoteAtPoint(mouseLoc);
      Note n = new Note(1, 1, 64, temp.getPitch(), temp.getOctave(),
              temp.getStartBeat());
      model.addNote(n);
      try {
        view.render();
      } catch (InvalidMidiDataException e) {
        e.printStackTrace();
      }
      view.updateRed(20);
    } else {
      throw new IllegalArgumentException("pause please");
    }
  }

  @Override
  public void run() {
    addNotes();
  }
}
