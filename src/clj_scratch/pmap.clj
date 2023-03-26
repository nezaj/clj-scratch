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

(comment
  (time (dorun (map string/lower-case rand-strings)))
  (time (dorun (pmap string/lower-case rand-strings))))
