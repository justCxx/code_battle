(ns code-battle.routes.home
  (:require [cemerick.friend :as friend]
            [clojure.java.io :as io]
            [code-battle.auth.github :as auth-github]
            [code-battle.layout :as layout]
            [compojure.core :refer [GET defroutes]]
            [ring.util.http-response :as response]))

(defn home-page []
  (layout/render "home.html"))

(defn oauth-callback [request]
  (response/ok {:request (str request)}))

(defroutes auth-routes
  (GET "/login" [] (friend/authenticate auth-routes auth-github/friend-config))
  (GET "/callback" [] oauth-callback))

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/docs" [] (response/ok (-> "docs/docs.md" io/resource slurp))))
