(defproject example "1.0.0-SNAPSHOT"
  :description "Example web app using single-sign-on with couchdb"
  :dependencies [[org.clojure/clojure "1.2.1"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [org.slf4j/log4j-over-slf4j "1.6.1"]
                 [ch.qos.logback/logback-classic "0.9.28"]
		 [compojure "0.6.4"]
                 [org.signaut/ring.middleware.servlet-ext "0.3"]
		 [ring/ring-core "0.3.11"]
		 [ring/ring-servlet "0.3.11"]]
  :dev-dependencies [[swank-clojure "1.2.1"]
		     [ring/ring-devel "0.3.11"]
  		     [uk.org.alienscience/leiningen-war "0.0.12"]
                     [org.signaut/ring-jetty7-adapter "0.3.11.4"]]
  :war {:name "example.war"}
  :aot [example.servlet])
