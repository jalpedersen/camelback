#!/bin/sh

java $JAVA_OPTS -jar camelback-1.0-SNAPSHOT.jar 2> camelback.err 1> camelback.out < /dev/null &
