(ns clj-scratch.seven-concurrency-models.transfer)

(def attempts (atom 0))
(def transfers (agent 0))

(defn transfer [from to amount]
  (dosync
   (swap! attempts inc)  ; swap! is not transaction safe, since swap! will be called even if the transaction fails
   (send transfers inc)  ; send is transaction safe, since send will execute iff the transaction succeeds
   (alter from - amount)
   (alter to + amount)))

(def checking (ref 10000))
(def savings (ref 20000))

(defn stress-thread [from to iterations amount]
  (Thread. #(dotimes [_ iterations] (transfer from to amount))))

(defn main []
  (println "Before: Checking =" @checking " Savings =" @savings)
  (let [t1 (stress-thread checking savings 100 100)
        t2 (stress-thread savings checking 200 100)]
    (.start t1)
    (.start t2)
    (.join t1)
    (.join t2))
  (await transfers)
  (println "Attempts: " @attempts)
  (println "Transfers: " @transfers)
  (println "After: Checking =" @checking " Savings =" @savings))

(comment
  (main))
