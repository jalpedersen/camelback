#!/bin/sh -x

java -jar ../../joiner/joiner-1.0.0-SNAPSHOT-standalone.jar --op update --db messages --id example --file example.war
