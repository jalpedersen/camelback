(defproject example "1.0.0-SNAPSHOT"
            :description "Example web app using single-sign-on with couchdb"
            :dependencies [[org.clojure/clojure "1.4.0"]
                           [org.clojure/data.json "0.1.2"]
                           [org.clojure/tools.logging "0.2.3"]
                           [org.slf4j/log4j-over-slf4j "1.6.4"]
                           [ch.qos.logback/logback-classic "1.0.0"]
                           [compojure "1.1.3"]
                           [org.signaut/ring.middleware.servlet-ext "0.9"]
                           [ring/ring-core "1.1.6" :exclusions [javax.servlet/servlet-api]]
                           [ring/ring-servlet "1.1.6" :exclusions [javax.servlet/servlet-api]]]
            :ring {:handler example.core/example-app}
            :profiles {:dev
                       {:dependencies
                        [[org.signaut/ring-jetty8-adapter "1.1.6"]
                         [ring/ring-devel "1.1.6"]]}}
            :plugins [[lein-ring "0.7.5"]]
            :aot [example.servlet])
