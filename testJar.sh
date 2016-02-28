#!/bin/bash

JAR=$(find build/libs -name '*.jar' | grep -vE 'javadoc|original|sources')

java -jar $JAR \
  src/test/resources/test.dat \
  org.apache.hadoop.io.IntWritable \
  org.apache.hadoop.io.Text \
  build/output1.dat > build/output1.txt

java -jar $JAR \
  build/output1.dat \
  org.apache.hadoop.io.IntWritable \
  org.apache.hadoop.io.Text \
  build/output2.dat > build/output2.txt

diff build/output1.txt build/output2.txt
