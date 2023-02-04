(ns clojeil.common.config)

(def 
  qname "clojeil")

(def 
  content-type "text/plain")

(def 
  publisher-channel "publisher-channel")

(def
  consumer-channel "consumer-channel")

(def ^{:const true}
  default-exchange-name "")

(def 
  default-ampq-uri (or (System/getenv "AMPQ_URI") "amqp://guest:pass@localhost:5672"))
