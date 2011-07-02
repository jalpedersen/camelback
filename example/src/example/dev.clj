(ns example.dev
  (:use ring.adapter.jetty7
        ring.middleware.reload
        ring.middleware.servlet-ext.dev
        example.core))


(def dev-app
     (-> example-app
	 (wrap-with-fake-user "monty" #{"user admin"})
	 (wrap-reload '[example.core])))

(defonce server (run-jetty #'dev-app {:port 9090 :join? false}))

