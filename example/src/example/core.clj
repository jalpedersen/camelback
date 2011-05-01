(ns example.core
  (:use compojure.core clojure.contrib.json)
  (:require [clojure.contrib.logging :as log]
            [compojure.handler :as handler]))

(defn- main-page [request]
  (let [session (.getSession (:servlet-request request))
        old-val (.getAttribute session "testing")]
    (.setAttribute session "testing" (str "blah " (:query-string request)))
    (log/info "Hi there")
    (json-str {:status "ok"
               :old-val old-val
               :params (:params request)
               :new-val (.getAttribute session "testing")
               :session (str (.getSession (:servlet-request request) true))
               :request (str request)})))

(defn- secure-page [request]
  (let [principal (.getUserPrincipal request)]
    (json-str {:status "validated"
	       :user (if (nil? principal)
		       "anonymous"
		       (.getName principal))})))


(defroutes example-routes
  (ANY "/example/ping" request "pong")
  (ANY "/example/user/*" request
       (secure-page (:servlet-request request)))
  (ANY "/*" request
       (main-page request)))

(def example-app
  (-> #'example-routes
      handler/api))
