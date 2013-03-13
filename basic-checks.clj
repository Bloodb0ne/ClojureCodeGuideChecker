(defn has-letter [letter str] 
  (> (count (re-seq  (re-pattern letter) 
    str)) 
	    0))

(defn comma-check [xs]
 (map (fn [x] (has-letter "," x)) 
 	(re-seq #"\[[\:\]\[\w\p{Space}(?!\,)]+\]" xs)))