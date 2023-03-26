(ns clj-scratch.http
  (:require [clj-http.client :as http]
            [cheshire.core :as json]))

(comment
  (http/get "https://finance.yahoo.com/quote/TSLA"))
