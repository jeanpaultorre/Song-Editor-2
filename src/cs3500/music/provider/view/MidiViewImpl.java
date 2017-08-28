package cs3500.music.provider.view;


import java.util.Set;

import javax.sound.midi.Instrument;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;

import cs3500.music.provider.model.INote;
import cs3500.music.provider.util.NoteOperations;

/**
 * MIDI playback.
 */
public class MidiViewImpl implements IMusicEditorView {
  private Synthesizer synth;
  private Receiver receiver;
  private Instrument[] instruments;
  private MidiChannel[] channels;
  private NoteOperations no;

  /**
   * Creates a midi view with a new synthesizer and receiver, and sets up instruments and channels.
   */
  public MidiViewImpl() {
    try {
      this.synth = MidiSystem.getSynthesizer();
      this.receiver = synth.getReceiver();
      this.synth.open();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
    instruments  = synth.getDefaultSoundbank().getInstruments();
    channels = synth.getChannels();
  }

  /**
   * Relevant classes and methods from the javax.sound.midi library:
   * <ul>
   *  <li>{@link MidiSystem#getSynthesizer()}</li>
   *  <li>{@link Synthesizer}
   *    <ul>
   *      <li>{@link Synthesizer#open()}</li>
   *      <li>{@link Synthesizer#getReceiver()}</li>
   *      <li>{@link Synthesizer#getChannels()}</li>
   *    </ul>
   *  </li>
   *  <li>{@link Receiver}
   *    <ul>
   *      <li>{@link Receiver#send(MidiMessage, long)}</li>
   *      <li>{@link Receiver#close()}</li>
   *    </ul>
   *  </li>
   *  <li>{@link MidiMessage}</li>
   *  <li>{@link ShortMessage}</li>
   *  <li>{@link MidiChannel}
   *    <ul>
   *      <li>{@link MidiChannel#getProgram()}</li>
   *      <li>{@link MidiChannel#programChange(int)}</li>
   *    </ul>
   *  </li>
   * </ul>
   * @see <a href="https://en.wikipedia.org/wiki/General_MIDI">
   *   https://en.wikipedia.org/wiki/General_MIDI
   *   </a>
   */

  public void playNote() throws InvalidMidiDataException {

    for (int ii = 0; ii < no.pieceLength(); ii++) {
      for (INote n : no.getNotes()) {
        if (ii == n.getStartBeat()) {
          this.channels[0].noteOn(n.getNoteNumber(), n.getVolume());
        }
        if (ii == n.getStartBeat() + n.getDuration()) {
          this.channels[0].noteOff(n.getNoteNumber());
        }
      }
      try {
        Thread.sleep(100); // wait time in milliseconds to control duration
      } catch (InterruptedException e) {

      }
    }
    this.receiver.close(); // Only call this once you're done playing *all* notes

  }

  @Override
  public void start() {
    try {
      this.playNote();
    } catch (InvalidMidiDataException e) {
      System.out.print("Invalid Midi Data");
    }

  }

  @Override
  public void createView(Set<INote> notes) {
    synth.loadInstrument(instruments[0]);
    this.no = new NoteOperations(notes);
  }
}
