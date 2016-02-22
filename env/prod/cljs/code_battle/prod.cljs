(ns code-battle.app
  (:require [code-battle.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
