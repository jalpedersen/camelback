#!/bin/sh -x
pid=$1
jmap -dump:format=b,file=leak $pid &&\
java -Xmx512m -jar /home/jalp/Java/jhat.jar leak

