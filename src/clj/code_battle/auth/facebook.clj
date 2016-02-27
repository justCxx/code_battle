(ns code-battle.auth.facebook
  (:require [friend-oauth2.util :refer [format-config-uri
                                        get-access-token-from-params]]
            [friend-oauth2.workflow :as oauth2]))

(def client-config
  {:client-id ""
   :client-secret ""
   :callback {:domain "" :path ""}})

(def uri-config
  {:authentication-uri {:url "https://www.facebook.com/dialog/oauth"
                        :query {:client_id (:client-id client-config)
                                :redirect_uri (format-config-uri client-config)}}

   :access-token-uri {:url "https://graph.facebook.com/oauth/access_token"
                      :query {:client_id (:client-id client-config)
                              :client_secret (:client-secret client-config)
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
