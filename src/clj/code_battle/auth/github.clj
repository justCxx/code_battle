(ns code-battle.auth.github
  (:require [config.core :refer [env]]
            [friend-oauth2.util :refer [format-config-uri
                                        get-access-token-from-params]]
            [friend-oauth2.workflow :as oauth2]))

(def client-config
  {:client-id (env :github-client-id)
   :client-secret (env :github-client-secret)
   :callback {:domain (str "http://" (env :github-callback-domain) ":" (env :port))
              :path "/callback"}})

(def uri-config
  {:authentication-uri {:url "https://github.com/login/oauth/authorize"
                        :query {:client_id (:client-id client-config)
                                :response_type "code"
                                :redirect_uri (format-config-uri client-config)
                                :scope "email"}}

   :access-token-uri {:url "https://github.com/login/oauth/access_token"
                      :query {:client_id (:client-id client-config)
                              :client_secret (:client-secret client-config)
                              :grant_type "authorization_code"
                              :redirect_uri (format-config-uri client-config)}}})

(defn credential-fn
  [token]
  ; lookup token in DB or whatever to fetch appropriate :roles
  {:identity token
   :roles #{::user}})

(def friend-config
  {:allow-annon? false
   :workflows [(oauth2/workflow
                {:client-config client-config
                 :uri-config uri-config
                 :access-token-parsefn get-access-token-from-params
                 :credential-fn credential-fn})]})
