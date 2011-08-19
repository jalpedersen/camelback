#!/bin/sh -x
# To create the initial webapp document
# curl -XPUT admin@password@localhost:5984/webapps/example -d @webapp.json
joiner_jar=../../joiner/joiner-0.8.1-standalone.jar 

java -jar $joiner_jar --op update --db webapps --id example --file example.war
