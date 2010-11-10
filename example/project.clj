(defproject example "1.0.0-SNAPSHOT"
  :description "Example web app using single-sign-on with couchdb"
  :dependencies [[org.clojure/clojure "1.2.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
		 [compojure "0.5.2"]
		 [ring/ring-core "0.3.3"]
		 [ring/ring-servlet "0.3.3"]]
  :dev-dependencies [[swank-clojure "1.2.1"]]
  :aot [example.core]
  )
