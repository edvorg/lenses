(ns rocks.clj.lenses.core)

(def ^:dynamic *backtrack* (list))

(defn lens-values [m]
  (swap! *backtrack* conj (fn [v]
                            (zipmap (keys m) v)))
  (vals m))

(defn lens-keys [m]
  (swap! *backtrack* conj (fn [v]
                            (zipmap v (vals m))))
  (keys m))

(defn in [m k]
  (swap! *backtrack* conj (fn [v]
                            (assoc m k v)))
  (get m k))

(defn out [v]
  (let [[last-out] @*backtrack*]
    (if-not last-out
      v
      (do
        (swap! *backtrack* rest)
        (last-out v)))))

(defn out-all [v]
  (let [[last-out] @*backtrack*]
    (if-not last-out
      v
      (do
        (swap! *backtrack* rest)
        (out-all (last-out v))))))

(defn map-lens [coll f]
  (map f coll))

(defmacro -> [& forms]
  `(binding [*backtrack* (atom ())]
     (clojure.core/-> ~@forms)))

(defmacro ->> [& forms]
  `(binding [*backtrack* (atom ())]
     (clojure.core/->> ~@forms)))
