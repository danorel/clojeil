(ns clojeil.consumer.node
  (:gen-class)
  (:require [langohr.core            :as rmq]
            [langohr.channel         :as lch]
            [langohr.basic           :as lb]
            [langohr.queue           :as lq]
            [langohr.consumers       :as lc]
            [clojeil.common.config   :refer [default-ampq-uri 
                                             default-exchange-name 
                                             content-type
                                             publisher-channel
                                             qname]]))

(defn handle-counter-decrease
    [ch {:keys [delivery-tag]} ^bytes payload]
    (let [prev-counter (String. payload "UTF-8")
          next-counter (-> 
                         prev-counter 
                         (Integer/parseInt)
                         ((fn [x] (- x 1)))
                         (str))]
      (println 
        (format "[consumer]: Received an original counter %s, delivery tag: %d, content-type: %s"
                 prev-counter 
                 delivery-tag 
                 content-type))
      (lb/publish ch default-exchange-name qname next-counter {:content-type content-type 
                                                                :type publisher-channel})))

(defn -main 
  [& args]
  (let [conn (rmq/connect {:uri default-ampq-uri})
        ch   (lch/open conn)]
         (lq/declare ch qname {:exclusive false 
                               :auto-delete true})
         (println (format "[consumer] Connected. Channel id: %d" (.getChannelNumber ch)))
         (lc/subscribe ch qname handle-counter-decrease {:auto-ack true})
         (Thread/sleep 5000)
         (println "[consumer]: Disconnecting...")
         (rmq/close ch)
         (rmq/close conn)))
