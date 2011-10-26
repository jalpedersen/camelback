(defproject example "1.0.0-SNAPSHOT"
  :description "Example web app using single-sign-on with couchdb"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [org.clojure/data.json "0.1.1"]
                 [org.clojure/tools.logging "0.2.3"]
                 [org.slf4j/log4j-over-slf4j "1.6.1"]
                 [ch.qos.logback/logback-classic "0.9.28"]
		 [compojure "0.6.5"]
                 [org.signaut/ring.middleware.servlet-ext "0.4"]
		 [ring/ring-core "1.0.0-RC1"]
		 [ring/ring-servlet "1.0.0-RC1"]]
  :dev-dependencies [[swank-clojure "1.2.1"]
		     [ring/ring-devel "1.0.0-RC1"]
  		     [uk.org.alienscience/leiningen-war "0.0.12"]
                     [org.signaut/ring-jetty7-adapter "1.0.0-RC1"]]
  :war {:name "example.war"}
  :aot [example.servlet])
