(ns clojeil.consumer.node
  (:gen-class)
  (:require [langohr.core      :as rmq]
            [langohr.channel   :as lch]
            [langohr.queue     :as lq]
            [langohr.consumers :as lc]
            [langohr.basic     :as lb]))

(def ^{:const true}
  default-exchange-name "")

(def 
  qname "clojeil.consumer.node")

(def 
  default-ampq-uri (get System/getenv "AMPQ_URI" "amqp://guest:pwd@localhost:5672"))

(defn message-handler
  [ch {:keys [content-type delivery-tag type] :as meta} ^bytes payload]
  (println 
    (format "[consumer] Received a message: %s, delivery tag: %d, content-type: %s, type: %s"
    (String. payload "UTF-8") delivery-tag content-type type)))

(defn serve 
  "Starting point for publisher node"
  ([] (serve default-ampq-uri))
  ([uri] 
   (let [conn  (rmq/connect {:uri uri})
         ch    (lch/open conn)]
     (println (format "[main] Connected. Channel id: %d" (.getChannelNumber ch)))
     (lq/declare ch qname {:exclusive false :auto-delete true})
     (lc/subscribe ch qname message-handler {:auto-ack true})
     (lb/publish ch default-exchange-name qname "Hello!" {:content-type "text/plain" :type "greetings"})
     (Thread/sleep 2000)
     (println "[main]: Disconnecting...")
     (rmq/close ch)
     (rmq/close conn))))

(serve)
