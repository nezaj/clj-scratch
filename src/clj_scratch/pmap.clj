"Test script for comparing performance of map and pmap
 We create a large-ish list of large-ish strings and map over
 them. We can inspect the difference between map and pmap"
(ns clj-scratch.pmap
  (:require [clojure.string :as string]))

(def alphabet-length 26)
(def letters (mapv (comp str char (partial + 65)) (range alphabet-length)))

(defn rand-string
  "Returns a random string of specified length"
  [length]
  (apply str (take length (repeatedly #(rand-nth letters)))))

(defn rand-string-list
  [list-length string-length]
  (doall (take list-length (repeatedly (partial rand-string string-length)))))

(def rand-strings (rand-string-list 3000 7000))
(def rand-small-strings (rand-string-list 9000 200))

(defn ppmap
  "Partioned pmap. Groups several ops together to make
  pmap worthwhile"
  [grain-size f & colls]
  (apply concat
         (apply pmap
                (fn [& pgroups] (doall (apply map f pgroups)))
                (map (partial partition-all grain-size) colls))))

(comment
  ; pmap is faster for large strings
  (time (dorun (map string/lower-case rand-strings)))
  (time (dorun (pmap string/lower-case rand-strings)))

  ; pmap is slower for small strings
  ; but we can speed it up with ppmap
  (time (dorun (map string/lower-case rand-small-strings)))
  (time (dorun (pmap string/lower-case rand-small-strings)))
  (time (dorun (ppmap 1000 string/lower-case rand-small-strings))))
