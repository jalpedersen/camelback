(ns example.core
  (:use compojure.core)
  (:use clojure.contrib.json))

(defn- main-page [request]
  (json-str {:status "ok"
	     :request (str request)}))

(defn- secure-page [request]
  (let [principal (.getUserPrincipal request)]
    (json-str {:status "validated"
	       :user (if (nil? principal)
		       "anonymous"
		       (.getName principal))})))

 
(defroutes example-routes
  (ANY "/example/user/*" request
       (secure-page (:servlet-request request)))
  (ANY "/*" request
       (main-page request)))

(def example-app
     (-> #'example-routes))
