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
					(println (re-seq #"\([\w]+" (get-line-n coll x)))
					(println "\n")
				))
		(recur (inc x))
		)))

;(parse-code indent-test)
(def test-string-fun "(defn foo
  [x] (bar (food x))))")

;;Reformat this function 
(defn read-function [x string]
  (loop [ind x leftb 1 rightb 0 value '[\(]]
    (let [character (get string ind)]
    (cond
      (= leftb rightb) {:start x :end ind :val (apply str value)}
      (= character \() (recur (inc ind) (inc leftb) rightb (conj value character)) ;left brace
      (= character \)) (recur (inc ind) leftb (inc rightb) (conj value character)) ;right brace 
      :else (recur (inc ind) leftb rightb (conj value character))
  ))))

(read-function 1 test-string-fun)

(defn parse-code-ver2 [string]
	(loop [x 0]
		(if (= x (count string))
			:End
			(do
      (if (= (get string x) \( ) 
        (do (prn (:val (read-function (+ x 1) string)))
          (recur (:end (read-function (+ x 1) string)))) 
           (recur (inc x))
      )))))

(defn count-lbrace [str]
	(count #"\("))
(defn count-rbrace [str]
	(count #"\)"))



(parse-code-ver2 test-string-def)
;(if (= (get str x) \; ) (prn "Brace" x) (println "a"))