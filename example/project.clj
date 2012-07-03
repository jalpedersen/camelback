(defproject example "1.0.0-SNAPSHOT"
  :description "Example web app using single-sign-on with couchdb"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.clojure/data.json "0.1.2"]
                 [org.clojure/tools.logging "0.2.3"]
                 [org.slf4j/log4j-over-slf4j "1.6.4"]
                 [ch.qos.logback/logback-classic "1.0.0"]
                 [compojure "1.1.0"]
                 [org.signaut/ring.middleware.servlet-ext "0.6"]
                 [ring/ring-core "1.1.1"]
                 [ring/ring-servlet "1.1.1"]]
  :dev-dependencies [[swank-clojure "1.2.1"]
                     [ring/ring-devel "1.1.1"]
                     [uk.org.alienscience/leiningen-war "0.0.12"]
                     [org.signaut/ring-jetty8-adapter "1.1.1"]]
  :war {:name "example.war"}
  :aot [example.servlet])
