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

;; update-choices: takes an instance and choices,
;; returns a list where in the instance the choices match to '1'
;; calculates total weight & value
(defn update-totals
  [instance choices]
  (let [included (included-items (:items instance) choices)]
    {:instance instance
     :choices choices
     :total-weight (reduce + (map :weight included))
     :total-value (reduce + (map :value included))})
  )

(defn random-answer
  "Construct a random answer for the given instance of the
  knapsack problem."
  [instance]
  (let [choices (repeatedly (count (:items instance))
                            #(rand-int 2))]
    (update-totals instance choices)))

;;; It might be cool to write a function that
;;; generates weighted proportions of 0's and 1's.

(defn score
  "Takes the total-weight of the given answer unless it's over capacity,
   in which case we return 0."
  [answer]
  (if (> (:total-weight answer)
         (:capacity (:instance answer)))

    (- (:capacity (:instance answer)) (:total-weight answer))
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

;(time (random-search knapPI_16_20_1000_1 100000))


"___________________________________________###############_______________________________________"
"########################################### OUR FUNCTIONS #######################################"
"*******************************************###############***************************************"

;; flip-choices: takes a list of choices and randomly changes x specified ammount of them
(defn flip-choices [binary times]
    (loop [bin binary x times]
      (if (zero? x) (into () bin)
        (recur (assoc (vec bin) (rand-int (count bin)) (rand-int 2)) (dec x))))
)


;; best: takes a parent and a child and returns the best of the two
(defn get-best
  [parent child]
  (let [parent-score (:score parent)
        child-score (:score child)]
  (if (< parent-score child-score) child
       parent)
    )
)

;; hill-climber: takes an instance, a mutating function, and numbers of tries you want the mutation
;; to be attempted (done recursively)
;; current: initial randomly generated answer
;; child: child of current with difference choices (only difference between this and parent is choices list)
;; updated-child: proper-child (total values are actually calculated)
(defn hill-climber
  [instance mutator max-tries]
  (loop [inst instance
         mut mutator
         x max-tries]
    (let [current (add-score (if (= x max-tries) (random-answer inst) inst))
          mut-count (Math/ceil (* (count (current :choices)) 0.25))
          child (assoc current :choices (mut (:choices current) (+ (rand-int mut-count) 1)))
          updated-child (add-score (update-totals (:instance child) (:choices child)))
          best (get-best current updated-child)
         ]
    (if (zero? x) inst
    (recur best mut (dec x)))))
)
;(Math/ceil ( * (count ((random-answer knapPI_13_20_1000_4) :choices)) 0.25))

;(hill-climber knapPI_11_20_1000_1 flip-choices 10000)
;(hill-climber knapPI_13_20_1000_1 flip-choices 1000)
;(hill-climber knapPI_16_20_1000_1 flip-choices 1000)

