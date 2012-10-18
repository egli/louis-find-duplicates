(ns louis-find-duplicates.core
  (:gen-class :main true))

(defn get-always-opcodes [table]
  (map next (re-seq #"(?m)^always (\p{L}+) ([0-9-]+)" (slurp table))))

(defn duplicates [table]
  (for [[_ opcodes] (group-by first (get-always-opcodes table))
        :when (< 1 (count opcodes))] 
    opcodes))

(defn -main [& args]
  (doseq [table args
          dups (duplicates table)
          [word braille] dups]
    (println (str table ": " word ", " braille))))
