(ns example.servlet
  (:gen-class :extends javax.servlet.http.HttpServlet)
  (:use example.core)
  (:use compojure.core [ring.util.servlet]))

(defservice example-app)

