(ns code-battle.config
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[code_battle started successfully]=-"))
   :middleware identity})
