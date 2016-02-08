(ns simple-search.core
  (:use simple-search.knapsack-examples.knapPI_11_20_1000
        simple-search.knapsack-examples.knapPI_13_20_1000
        simple-search.knapsack-examples.knapPI_16_20_1000))

;;; An answer will be a map with (at least) four entries:
;;;   * :instance
;;;   * :choices - a vector of 0's and 1's indicating whether
;;;        the corresponding item should be included
;;;   * :total-weight - the weight of the chosen items
;;;   * :total-value - the value of the chosen items

(defn included-items
  "Takes a sequences of items and a sequence of choices and
  returns the subsequence of items corresponding to the 1's
  in the choices sequence."
  [items choices]
  (map first
       (filter #(= 1 (second %))
               (map vector items choices))))

(defn random-answer
  "Construct a random answer for the given instance of the
  knapsack problem."
  [instance]
  (let [choices (repeatedly (count (:items instance))
                            #(rand-int 2))
        included (included-items (:items instance) choices)]
    {:instance instance
     :choices choices
     :total-weight (reduce + (map :weight included))
     :total-value (reduce + (map :value included))}))

;;; It might be cool to write a function that
;;; generates weighted proportions of 0's and 1's.

(defn score
  "Takes the total-weight of the given answer unless it's over capacity,
   in which case we return 0."
  [answer]
  (if (> (:total-weight answer)
         (:capacity (:instance answer)))
    0
    (:total-value answer)))

(defn add-score
  "Computes the score of an answer and inserts a new :score field
   to the given answer, returning the augmented answer."
  [answer]
  (assoc answer :score (score answer)))

(defn random-search
  [instance max-tries]
  (apply max-key :score
         (map add-score
              (repeatedly max-tries #(random-answer instance)))))

(time (random-search knapPI_16_20_1000_1 1000000))

;; Starts with a random answer, and then the value is penalized for being over-weight
(defn hill-climber
  [instance max-tries]
  (let [current (random-answer instance) score (:total-value current)]
       ;(assoc (vec :current :total-value) (punish current))
        (apply max-key :score
          (assoc current :score (punish current)))
            ;(repeat 5 (swap (rand-int (- (count :choices current) 1)) current))
            (let [climb (repeat 5 (swap (rand-int (- (count :choices current) 1)) current))]
            (if (< (:score current) (:score climb))
              (let [current climb]))
    )))

 ;; penalize over-weight values
 (defn punish
   [answer]
   (let [punishment (- (:capacity (:instance answer)) (:total-weight answer))]
      (if (< punishment 0)
        (+ (:total-value answer) punishment))
         (:total-value answer)))

 (defn swap
   [index answer]
  (let [coin-toss (rand-int 2) die-toss (rand-int 9)]
    (if (= (nth (:choices answer) index) 1
     (if (= coin-toss 0)
       ;(remove (:choices answer) index))
       (assoc (vec :choices answer)index 0)
           (update-in answer [:total-weight answer] - (nth (:weight (:items (:instance answer)) index)))
           (update-in answer [:total-value answer] - (nth (:value (:items (:instance answer)) index)))
           (assoc (:score answer) (punish(answer))))
       (assoc (vec :choices answer)index 1)))
     (if (= die-toss 0)
       (assoc (vec :choices answer)index 1)
         (update-in answer [:total-weight answer] + (nth (:weight (:items (:instance answer)) index)))
         (update-in answer [:total-value answer] + (nth (:value (:items (:instance answer)) index)))
          (assoc (:score answer) (punish(answer))))


       (assoc (vec :choices answer)index 0)))




 ;;; Calling the function
 (hill-climber knapPI_16_20_1000_1 10)
 ;(punish (random-answer knapPI_16_20_1000_1))

 ;;; EXPERIMENTATION SECTION
 ;(hill-climber knapPI_16_20_1000_1 3)
 ; (def arr [{:num 0}, {:num 1},{:num 2}, {:num 3}, {:num 4} {:num 5}])
 ; (remove even? arr)
 ; (defn
 ;   [list index]
 ;   (filter (not index)))
 ;(remove (subseq {:num 1}) arr)




