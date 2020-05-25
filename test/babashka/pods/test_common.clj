(ns babashka.pods.test-common
  (:require [clojure.java.io :as io]
            [clojure.test :refer [is]]))

(def test-program (slurp (io/file "test-resources" "test_program.clj")))

(defn assertions [out err ret]
  (doseq [[expected actual]
          (map vector '["pod.test-pod"
                        pod.test-pod
                        {:a 1, :b 2}
                        6
                        3
                        [1 2 3 4 5 6 7 8 9]
                        "Illegal arguments / {:args (1 2 3)}"
                        nil
                        3
                        #"cast"
                        {:args ["1" 2]}
                        true
                        9
                        [1 2 3]
                        [[1] [1]]
                        2]
               (concat ret (repeat ::nil)))]
    (if (instance? java.util.regex.Pattern expected)
      (is (re-find expected actual))
      (is (= expected actual))))
  (is (= "(\"hello\" \"print\" \"this\" \"debugging\" \"message\")\n:foo\n:foo\n" (str out)))
  (is (= "(\"hello\" \"print\" \"this\" \"error\")\n" (str err))))
