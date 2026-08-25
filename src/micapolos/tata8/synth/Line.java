package micapolos.tata8.synth;

import javax.sound.sampled.*;
import java.util.Arrays;

public abstract class Line {
  public abstract void reset();
  public abstract float step();

  public final void write(byte[] arr, int off, int len) {
    while (len != 0) {
      float value = step();
      arr[off] = (byte) (value * 127f);
      off++;
      len--;
    }
  }

  public final void println(String label, int count) {
    IO.print(label + ": ");
    byte[] arr = new byte[count];
    write(arr, 0, arr.length);
    IO.println(Arrays.toString(arr));
  }

  public final void stream(SourceDataLine line, Runnable sync) {
    int bufferSize = 256;
    byte[] buffer = new byte[bufferSize];
    while (true) {
      if (Thread.interrupted()) {
        return;
      }

      sync.run();
      while (line.getBufferSize() - line.available() > bufferSize) {
        try {
          Thread.sleep(1);
        } catch (InterruptedException e) {
          return;
        }
      }

      write(buffer, 0, buffer.length);
      line.write(buffer, 0, buffer.length);
    }
  }

  public final Runnable listen() {
    return listen(() -> {});
  }

  public final Runnable listen(Runnable sync) {
    try {
      AudioFormat audioFormat = new AudioFormat(22050, 8, 1, true, false);
      DataLine.Info lineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
      SourceDataLine line = (SourceDataLine) AudioSystem.getLine(lineInfo);
      line.open();
      line.start();

      Thread thread = new Thread("Audio") {
        @Override
        public void run() {
          stream(line, sync);
        }
      };
      thread.start();
      return () -> {
        thread.interrupt();
        line.stop();
        line.close();
      };
    } catch (LineUnavailableException e) {
      throw new RuntimeException(e);
    }
  }
}
