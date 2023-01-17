(ns clojeil.publisher.node
  (:require [clojure.string :as str]
            [langohr.core      :as rmq]
            [langohr.channel   :as lch]
            [langohr.basic     :as lb]
            [compojure.core    :refer [defroutes GET]]))

(defn serve
  "Starting point for publisher node"
  ([] (serve 'localhost 8080))
  ([host port & rest] (str/join [host ":" port rest])))

(def 
  qname "clojeil.consumer.node")

(def 
  default-ampq-uri (get System/getenv "AMPQ_URI" "amqp://guest:pwd@localhost:5672"))

(def ^{:const true}
  default-exchange-name "")

(defroutes app
  (GET "/" []
       (let [conn (rmq/connect {:uri default-ampq-uri})
             ch   (lch/open conn)]
         (lb/publish ch default-exchange-name qname "Hello!" {:content-type "text/plain" :type "greetings"})
         (rmq/close ch)
         (rmq/close conn)
         {:status 200
          :headers {"Content-Type" "text/plain"}
          :body "Published message!"})))

(serve)
