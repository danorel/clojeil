(ns clojeil.core
  (:gen-class)
  (:require [clojeil.publisher.node :as pb]
            [clojeil.consumer.node :as cn]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (apply pb/serve args)
  (apply cn/serve args))

(-main)
