#!/bin/sh -x
# To create the initial webapp document
# curl -XPUT admin@password@localhost:5984/webapps/example -d @webapp.json

java -jar ../../joiner/joiner-0.8-standalone.jar --op update --db webapps --id example --file example.war
