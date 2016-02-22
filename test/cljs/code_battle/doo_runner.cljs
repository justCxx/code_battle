(ns code-battle.doo-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [code-battle.core-test]))

(doo-tests 'code-battle.core-test)

