(ns code-battle.config
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [code-battle.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[code_battle started successfully using the development profile]=-"))
   :middleware wrap-dev})
