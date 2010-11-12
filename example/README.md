Example clojure web app demonstrating the single-sign-on feature of
jetty-couchdb.

Here we use nginx as proxy - maybe the proposed proxy
feature (https://github.com/davisp/couchdb/tree/new_externals) will
make this redundant.

The reason we need to mess around with proxies is to get around the
same host, same port requirement of XmlHttp requests. Furthermore the
SSO based on the cookie set by CouchDB.


In in all this means:

 - You can create web apps using CouchApp

 - But if your app require something that cannot be implemented in CouchDB,
  you have the option to implement it as a Java web app (or clojure as in
  this example).



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


Usage 
===== 

Now your couchapps can access your java webapp through the
"/_j/example/*" URI. The nifty thing is that the currently logged-in
CouchDB user is mapped to a java.security.Principal object, including
any roles the CouchDB user might have. This means that the webapp does
not have to known anything about CouchDB.

Try for instance to do this (couchdb_user is assumed to be a valid couchdb user):

    curl valid_couchdb_user:password@localhost/_j/example/user/testing

You should see something like this in response:

    {"status":"validated","request":"couchdb_user"}
