package io.github.brharrington.seqfile;

import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Writable;

import java.io.File;

/**
 * Command line utility for basic testing that we can read a sequence file in and write it back
 * out as a valid sequence file.
 */
public class Main {

  @SuppressWarnings("unchecked")
  private static Class<? extends Writable> forName(String cls) throws ClassNotFoundException {
    return (Class<? extends Writable>) Class.forName(cls);
  }

  public static void main(String[] args) throws Exception {
    if (args.length != 4) {
      System.err.println("Usage: seqfile <input-file> <key-class> <value-class> <output-file>");
      System.exit(1);
    }

    final File input = new File(args[0]);
    final Class<? extends Writable> keyClass = forName(args[1]);
    final Class<? extends Writable> valueClass = forName(args[2]);
    final File output = new File(args[3]);

    try (SequenceFile.Reader reader = SeqFiles.reader(input);
         SequenceFile.Writer writer = SeqFiles.writer(output, keyClass, valueClass)) {
      final Writable k = keyClass.newInstance();
      final Writable v = valueClass.newInstance();
      for (int i = 1; reader.next(k, v); ++i) {
        System.out.printf("%10d: key [%s], value [%s]%n", i, k.toString(), v.toString());
        writer.append(k, v);
      }
    }
  }
}
