(ns code-battle.test.db.core
  (:require [code-battle.db.core :refer [*db*] :as db]
            [code-battle.db.migrations :as migrations]
            [clojure.test :refer :all]
            [clojure.java.jdbc :as jdbc]
            [config.core :refer [env]]
            [mount.core :as mount]))

(use-fixtures
  :once
  (fn [f]
    (mount/start #'code-battle.db.core/*db*)
    (migrations/migrate ["migrate"])
    (f)))

(deftest test-users
  (jdbc/with-db-transaction [t-conn *db*]
    (jdbc/db-set-rollback-only! t-conn)
    (is (= 1 (db/create-user!
               t-conn
               {:id         "1"
                :first_name "Sam"
                :last_name  "Smith"
                :email      "sam.smith@example.com"
                :pass       "pass"})))
    (is (= {:id         "1"
            :first_name "Sam"
            :last_name  "Smith"
            :email      "sam.smith@example.com"
            :pass       "pass"
            :admin      nil
            :last_login nil
            :is_active  nil}
           (db/get-user t-conn {:id "1"})))))
