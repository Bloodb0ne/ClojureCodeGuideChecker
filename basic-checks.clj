(defn has-letter [letter str] 
  (> (count (re-seq  (re-pattern letter) 
    str)) 
	    0))

(defn comma-check [xs]
 (into (map (fn [x] (has-letter "," x)) 
 	   		(re-seq #"\[[\:\]\[\w\p{Space}\,]+\]" xs))
       (map (fn [x] (has-letter "," x)) 
 	   		(re-seq #"\([\:\]\)\w\p{Space}\,]+\)" xs))))

;Defn matching
;can be applied to other functions too
(def test-string-def ";; good
(defn foo
  [x]
  (bar x))

;; good
(defn foo [x]
  (bar x))
(derper? foo
  (bar x))
;; bad
(defn foo
  [x] (bar x))")

(def def-test 
	(re-seq #"\(defn[\:\w\[\]\(\p{Space})]+" test-string-def))

(rest def-test)


;test 2 
(defn get-lines [str]
  "Returns a vector containing the lines split by \newline"
  (vec (clojure.string/split str #"\n")))

(defn get-line-n [coll,line-num]
  (get coll line-num))

(def indent-test (get-lines test-string-def))

(get-line-n indent-test 2)

(defn parse-code [coll]
  (loop [x 0]
  	(do 
  		(if (= x (count coll))
  			nil
  		(if (= (count (re-seq #"\(" (get-line-n coll x))) 1)
  				(println "Found beginin")
  				(println "Comment or new line")
  			))
  	(recur (inc x))
  	)))

(parse-code indent-test)
(defn count-lbrace [str]
  (count #"\("))
(defn count-rbrace [str]
  (count #"\)"))