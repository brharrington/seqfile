[![Build Status](https://travis-ci.org/brharrington/seqfile.svg)](https://travis-ci.org/brharrington/seqfile/builds)

Attempt to get a minimal library for reading and writing a sequence file. The
hadoop SequenceFile classes are part of hadoop-common which brings in a ton of
dependencies including jetty, tomcat, zookeeper, multiple http clients, and
several different logging libraries.

This setup uses proguard to remove unnecessary classes and restrict the exposed
classes to just `org.apache.hadoop.io.**`.

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
