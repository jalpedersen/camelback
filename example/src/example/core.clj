(ns example.core
  (:use compojure.core
        clojure.data.json
        ring.middleware.servlet-ext)
  (:require [clojure.tools.logging :as log]
            [ring.middleware.session :as session]
            [compojure.handler :as handler]))

(defn- main-page [request]
  (let [session (:session request)
        counter (:counter session 0)]
    (log/info (str "Hi there from " (:remote-addr request)))
    {:body (json-str {:status "ok"
               :counter counter
               :params (:params request)
               :session (str (.getSession (:servlet-request request) true))
               :request (str request)})
     :session (assoc session :counter (inc counter))
     :status 200}))

(defn- secure-page [request]
  (let [principal (.getUserPrincipal request)]
    (json-str {:status "validated"
               :user (if (nil? principal)
                       "anonymous"
                       (.getName principal))})))

(defroutes secure-routes
           (POST "/secret" {:keys [username]}
                 (str "You posted to a secret url, " username))
           (ANY "/*" {:keys [username]}
                (str "Hello " username)))

(def wrapped-secure-routes
    (-> #'secure-routes
          (wrap-userprincipal :allow-roles ["admin"])))

(defroutes example-routes
           (ANY "/ping" request "pong")
           (ANY "/user/*" request
                (secure-page (:servlet-request request)))
           (context "/admin" [] wrapped-secure-routes)
           (ANY "/*" request
                (main-page request)))

(def example-app
  (-> #'example-routes
    (session/wrap-session {:store (httpsession-store)})
    wrap-with-session
    wrap-userprincipal
    without-contextpath
    handler/api))
