(ns example.dev
  (:use ring.adapter.jetty)
  (:use ring.middleware.reload)
  (:use example.core))


(defn- wrap-with-fake-user [handler]
  (let [fake-user (proxy [java.security.Principal][]
		    (getName [] "dev-user"))
	fake-request (proxy [javax.servlet.http.HttpServletRequest] []
		       (getUserPrincipal [] fake-user)
		       (isUserInRole [role] true))]
    (fn [request]
      (handler (assoc request :servlet-request fake-request)))))
      

(def dev-app
     (-> example-routes
	 (wrap-with-fake-user)
	 (wrap-reload '[example.core])))

(defonce server (run-jetty #'dev-app {:port 8080 :join? false}))

	


