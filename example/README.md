Example clojure web app demonstrating the single-sign-on feature of
jetty-couchdb


Setup:
======

    nginx -- /    ----> couchdb
      |----- /_j/ ----> camelback

See nginx/example.conf for how to setup nginx.


Deploying example webapp:
=========================
 - Start CouchDB

 - Start Camelback (java -jar camelback-1.0-SNAPSHOT.jar)

 - Build war-file:
   ./build-war.sh

 - Save document in database configured in camelback.json configuration file

   Example :

   { "_id": "example",
     "type": "webapp",
     "name":"example",
     "contextPath":"/example",
     "war":"example.war"
   }

   Also be sure to attach the example.war file to the document.

 - Camelback will detect the change and deploy the war file automatically

