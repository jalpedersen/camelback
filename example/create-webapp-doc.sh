#!/bin/sh

curl -XPUT http://localhost:5984/webapps/example -H "Content-type: application/json" -d@webapp.json -uknownUser:myPassword
