(ns example.core
  (:gen-class :extends javax.servlet.http.HttpServlet)
  (:use compojure.core [ring.util.servlet])
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

 
(defroutes joiner-routes
  (ANY "/example/user/*" request
       (secure-page (:servlet-request request)))
  (ANY "/*" request
       (main-page request)))

(defservice joiner-routes)
