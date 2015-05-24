package io.github.brharrington.seqfile;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Writable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Helpers for creating a sequence file reader or writer.
 */
public class SeqFiles {

  private SeqFiles() {
  }

  private static final Configuration CONFIG = new Configuration();

  /**
   * Create a reader for a content in a file.
   *
   * @param file
   *     File on the disk containing a hadoop sequence file.
   * @return
   *     Reader to process the sequence file.
   */
  public static SequenceFile.Reader reader(File file) throws IOException {
    return reader(new FileInputStream(file), file.length());
  }

  /**
   * Create a reader for content in a resource from the classpath.
   *
   * @param resource
   *     File on the classpath containing a hadoop sequence file.
   * @return
   *     Reader to process the sequence file.
   */
  public static SequenceFile.Reader reader(String resource) throws IOException {
    final ClassLoader cl = Thread.currentThread().getContextClassLoader();
    final URL url = cl.getResource(resource);
    if (url == null) {
      throw new FileNotFoundException("could not find resource: " + resource);
    }

    final URLConnection con = url.openConnection();
    final long size = con.getContentLengthLong();
    return reader(con.getInputStream(), size);
  }

  /**
   * Create a reader for URL.
   *
   * @param url
   *     URL for a hadoop sequence file.
   * @return
   *     Reader to process the sequence file.
   */
  public static SequenceFile.Reader reader(URL url) throws IOException {
    final URLConnection con = url.openConnection();
    final long size = con.getContentLengthLong();
    return reader(con.getInputStream(), size);
  }

  /**
   * Create a reader for an input stream. If the length is not known, then {@link Long#MAX_VALUE}
   * can be used and an {@link java.io.EOFException} will be thrown when the end of the stream
   * is reached.
   *
   * @param in
   *     Input stream for the sequence file data.
   * @param len
   *     Length in bytes for the input.
   * @return
   *     Reader to process the sequence file.
   */
  public static SequenceFile.Reader reader(InputStream in, long len) throws IOException {
    final FSDataInputStream dataIn = new FSDataInputStream(new HadoopInputStream(in));
    final SequenceFile.Reader.Option input = SequenceFile.Reader.stream(dataIn);
    final SequenceFile.Reader.Option length = SequenceFile.Reader.length(len);
    return new SequenceFile.Reader(CONFIG, input, length);
  }

  /**
   * Create a writer to output a sequence file to a file on the disk.
   *
   * @param file
   *     Destination to write the data.
   * @param keyClass
   *     Writable type to use for the keys.
   * @param valueClass
   *     Writable type to use for the values.
   * @return
   *     Writer to append data to the sequence file.
   */
  public static SequenceFile.Writer writer(
      File file,
      Class<? extends Writable> keyClass,
      Class<? extends Writable> valueClass) throws IOException {
    return writer(new FileOutputStream(file), keyClass, valueClass);
  }

  /**
   * Create a writer to output a sequence file to an arbitrary output stream.
   *
   * @param out
   *     Destination to write the data.
   * @param keyClass
   *     Writable type to use for the keys.
   * @param valueClass
   *     Writable type to use for the values.
   * @return
   *     Writer to append data to the sequence file.
   */
  public static SequenceFile.Writer writer(
      OutputStream out,
      Class<? extends Writable> keyClass,
      Class<? extends Writable> valueClass) throws IOException {
    final FSDataOutputStream dataOut = new FSDataOutputStream(out, null);
    final SequenceFile.Writer.Option output = SequenceFile.Writer.stream(dataOut);
    final SequenceFile.Writer.Option key = SequenceFile.Writer.keyClass(keyClass);
    final SequenceFile.Writer.Option value = SequenceFile.Writer.valueClass(valueClass);
    return SequenceFile.createWriter(CONFIG, output, key, value);
  }
}
