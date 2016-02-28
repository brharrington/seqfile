[![Build Status](https://travis-ci.org/brharrington/seqfile.svg)](https://travis-ci.org/brharrington/seqfile/builds)

Attempt to get a minimal library for reading and writing a sequence file. The
hadoop SequenceFile classes are part of hadoop-common which brings in a ton of
dependencies including jetty, tomcat, zookeeper, multiple http clients, and
several different logging libraries.

This setup uses proguard to remove unnecessary classes and restrict the exposed
classes to just `org.apache.hadoop.io.**`.

## Usage

The library can is available via jcenter, for gradle use the following:

```
compile 'io.github.brharrington:seqfile:0.13.0'
```

Simple example of copying a sequence file:

```java
try (SequenceFile.Reader reader = SeqFiles.reader(input);
     SequenceFile.Writer writer = SeqFiles.writer(output, keyClass, valueClass)) {
  final Writable k = keyClass.newInstance();
  final Writable v = valueClass.newInstance();
  for (int i = 1; reader.next(k, v); ++i) {
    System.out.printf("%10d: key [%s], value [%s]%n", i, k.toString(), v.toString());
    writer.append(k, v);
  }
}
```

See [test cases](https://github.com/brharrington/seqfile/blob/master/src/test/java/io/github/brharrington/seqfile/SeqFilesTest.java)
for further examples.

## Building

To create a new copy of the jar:

```bash
$ ./gradlew clean build
```

This will automatically run proguard and create the uber jar. The clean task is
needed or else the proguard task will fail on subsequent runs.

## Verify Jar

To verify the jar is working properly after running through proguard run:

```bash
$ ./testJar.sh 
$ echo $?
0
```

It will show diffs and return a non-zero exit code if broken.
