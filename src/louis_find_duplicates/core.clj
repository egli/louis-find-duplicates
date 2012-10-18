(ns louis-find-duplicates.core
  (:gen-class :main true))

(defn get-always-opcodes [table]
  (map (comp (partial zipmap [:word :braille]) next) 
       (re-seq #"(?m)^always (\p{L}+) ([0-9-]+)" (slurp table))))

(defn duplicates [table]
  (for [[word opcodes] (group-by :word (get-always-opcodes table))
        :when (< 1 (count opcodes))] 
    [word (set (map :braille opcodes))]))

(defn -main [& args]
  (doseq [table args
          dups (duplicates table)]
    (let [[word braille] dups]
      (when (< 1 (count braille))
        (print "Conflicting Duplicate: "))
      (println (str table ": " word ", " (apply str (interpose \, braille)))))))
