package io.github.brharrington.seqfile;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class SeqFilesTest {

  @Test
  public void readAndWrite() throws Exception {
    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try (SequenceFile.Writer writer = SeqFiles.writer(baos, Text.class, Text.class)) {
      writer.append(new Text("a"), new Text("0"));
      writer.append(new Text("b"), new Text("1"));
    }

    final byte[] data = baos.toByteArray();
    final ByteArrayInputStream bais = new ByteArrayInputStream(data);
    final Text key = new Text();
    final Text value = new Text();
    try (SequenceFile.Reader reader = SeqFiles.reader(bais, data.length)) {
      int i = 0;
      while (reader.next(key, value)) {
        final char k = (char) ('a' + i);
        Assert.assertEquals("" + k, key.toString());
        Assert.assertEquals("" + i, value.toString());
        ++i;
      }
      Assert.assertEquals(2, i);
    }
  }

  @Test
  public void readFileResource() throws Exception {
    final IntWritable key = new IntWritable();
    final Text value = new Text();
    try (SequenceFile.Reader reader = SeqFiles.reader("test.dat")) {
      int i = 0;
      while (reader.next(key, value)) {
        Assert.assertEquals(i, key.get());
        Assert.assertEquals(String.format("%020X", i), value.toString());
        ++i;
      }
      Assert.assertEquals(10000, i);
    }
  }

  @Test
  public void readJarResource() throws Exception {
    final IntWritable key = new IntWritable();
    final Text value = new Text();
    final URL jarUrl = Thread.currentThread().getContextClassLoader().getResource("test.jar");
    final URLClassLoader cl = new URLClassLoader(new URL[] { jarUrl });
    final URL url = cl.getResource("seq.dat");
    Assert.assertEquals("jar", url.getProtocol());
    try (SequenceFile.Reader reader = SeqFiles.reader(url)) {
      int i = 0;
      while (reader.next(key, value)) {
        Assert.assertEquals(i, key.get());
        Assert.assertEquals(String.format("%020X", i), value.toString());
        ++i;
      }
      Assert.assertEquals(10000, i);
    }
  }

  @Test
  @Ignore
  public void createSeqFile() throws Exception {
    final File output = new File("test.dat");
    try (SequenceFile.Writer writer = SeqFiles.writer(output, IntWritable.class, Text.class)) {
      final IntWritable k = new IntWritable();
      final Text v = new Text();
      for (int i = 0; i < 10000; ++i) {
        k.set(i);
        v.set(String.format("%020X", i));
        writer.append(k, v);
      }
    }
  }
}
