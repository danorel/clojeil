(defproject clojeil "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "https://github.com/danorel/clojeil"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [org.clojure/tools.logging "0.3.1"]
                 [com.novemberain/langohr "5.1.0"]]
  :target-path "target/%s"
  :plugins [[lein-exec "0.3.7"]]
  :aliases {"consumer"  ["with-profile" "consumer"  "run"]
            "publisher" ["with-profile" "publisher" "run"]}
  :profiles {:uberjar   {:aot :all}
             :consumer  {:main clojeil.consumer.node}
             :publisher {:main clojeil.publisher.node}})
