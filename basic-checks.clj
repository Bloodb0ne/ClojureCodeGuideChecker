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