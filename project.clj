(defproject clojeil "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "https://github.com/danorel/clojeil"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [com.novemberain/langohr "5.1.0"]]
  :main ^:skip-aot clojeil.consumer.node
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
