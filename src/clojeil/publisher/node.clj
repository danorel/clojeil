(ns clojeil.publisher.node
  (:gen-class)
  (:require [langohr.core          :as rmq]
            [langohr.channel       :as lch]
            [langohr.basic         :as lb]
            [langohr.queue         :as lq]
            [langohr.consumers     :as lc]
            [clojeil.common.config :refer [default-ampq-uri 
                                           default-exchange-name
                                           content-type
                                           consumer-channel
                                           qname]]))

(defn handle-counter-return
  "Return back counter value"
  [ch {:keys [delivery-tag]} payload]
  (let [counter (-> 
                  payload
                  ((fn [x] (String. x "UTF-8"))))]
    (if (= counter "0")
      ((println (format "[publisher]: Finished!")))
      ((println 
         (format "[publisher]: Received a decreased counter value %s, delivery tag: %d, content-type: %s"
                 counter 
                 delivery-tag 
                 content-type))
       (lb/publish ch default-exchange-name qname counter {:content-type content-type 
                                                           :type consumer-channel})))))

(defn -main 
  [& args]
  (let [conn    (rmq/connect {:uri default-ampq-uri})
        ch      (lch/open conn)
        message (str 100)]
    (lq/declare ch qname {:exclusive false :auto-delete true})
    (println (format "[publisher] Connected. Channel id: %d" (.getChannelNumber ch)))
    (lb/publish ch default-exchange-name qname message {:content-type content-type 
                                                        :type consumer-channel})
    (println 
      (format "[publisher] Published a message: %s, content-type: %s"
              message 
              content-type))
    (lc/subscribe ch qname handle-counter-return {:auto-ack true})
    (Thread/sleep 5000)
    (println "[publisher]: Disconnecting...")
    (rmq/close ch)
    (rmq/close conn)))
