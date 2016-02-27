(ns code-battle.handler
  (:require [clojure.tools.logging :as log]
            [code-battle.config :refer [defaults]]
            [code-battle.layout :refer [error-page]]
            [code-battle.middleware :as middleware]
            [code-battle.routes.home :refer [auth-routes home-routes]]
            [code-battle.routes.services :refer [service-routes]]
            [compojure.core :refer [routes wrap-routes]]
            [compojure.route :as route]
            [config.core :refer [env]]
            [luminus.logger :as logger]
            [mount.core :as mount]))

(defn init
  "init will be called once when
   app is deployed as a servlet on
   an app server such as Tomcat
   put any initialization code here"
  []
  (logger/init env)
  (doseq [component (:started (mount/start))]
    (log/info component "started"))
  ((:init defaults)))

(defn destroy
  "destroy will be called when your application
   shuts down, put any clean up code here"
  []
  (log/info "code_battle is shutting down...")
  (doseq [component (:stopped (mount/stop))]
    (log/info component "stopped"))
  (log/info "shutdown complete!"))

(def app-routes
  (routes
   #'service-routes
   #'auth-routes
   (wrap-routes #'home-routes middleware/wrap-csrf)
   (route/not-found
    (:body
     (error-page {:status 404
                  :title "page not found"})))))

(def app (middleware/wrap-base #'app-routes))
