#!/bin/sh -x
# To create the initial webapp document
# curl -XPUT admin@password@localhost:5984/webapps/example -d @webapp.json
# Get couch-joiner from: https://github.com/jalpersen/couch-joiner
joiner_jar=../../couch-joiner/couch-joiner.jar

java -jar $joiner_jar -m update -db webapps -id example -f target/example.war
