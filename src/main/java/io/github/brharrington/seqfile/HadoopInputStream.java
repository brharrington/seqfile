package io.github.brharrington.seqfile;

import org.apache.hadoop.fs.FSInputStream;

import java.io.IOException;
import java.io.InputStream;

/**
 * Wraps an arbitrary input stream with the FSInputStream interface needed by hadoop. Note that
 * you will need to know the length of the input stream or expect an EOF exception.
 */
public class HadoopInputStream extends FSInputStream {

  private final InputStream input;
  private long position;

  public HadoopInputStream(InputStream input) {
    this.input = input;
    this.position = 0L;
  }

  @Override public void seek(long loc) throws IOException {
    if (loc > position) {
      input.skip(loc - position);
      position = loc;
    } else if (loc < position) {
      throw new UnsupportedOperationException();
    }
  }

  @Override public long getPos() throws IOException {
    return position;
  }

  @Override public boolean seekToNewSource(long targetPos) throws IOException {
    return false;
  }

  @Override public int read() throws IOException {
    final int b = input.read();
    if (b >= 0) {
      ++position;
    }
    return b;
  }

  @Override public int read(byte[] buffer, int offset, int length) throws IOException {
    final int amountRead = input.read(buffer, offset, length);
    if (amountRead > 0) {
      position += amountRead;
    }
    return amountRead;
  }

  @Override public void close() throws IOException {
    input.close();
  }
}
