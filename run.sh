#!/bin/sh

ant build
CLASSPATH=.
jars=`ls ./lib`
for jar in $jars
do
    CLASSPATH="$CLASSPATH:./lib/$jar"
done
echo $CLASSPATH

export LD_LIBRARY_PATH=/usr/lib/hadoop/lib/native/Linux-amd64-64/
cd output/
java -server -Xms1024M -Xmx2048M -cp $(hadoop classpath):. com.general.datagen.Gen RecordNumber=10 WorkDir=../ Verb=true
