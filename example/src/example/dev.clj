(ns example.dev
  (:use ring.adapter.jetty7
        ring.middleware.reload
        ring-userprincipal-middleware.dev
        example.core))


(def dev-app
     (-> example-routes
	 (wrap-with-fake-user "monty" #{"user admin"})
	 (wrap-reload '[example.core])))

(defonce server (run-jetty #'dev-app {:port 9090 :join? false}))

