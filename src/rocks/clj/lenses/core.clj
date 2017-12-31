(ns rocks.clj.lenses.core)

(defn lens-values [m]
  (let [{:keys [out]} (meta m)]
    (with-meta
      (vals m)
      {:out (conj out (fn [v]
                        (with-meta
                          (zipmap (keys m) v)
                          {:out out})))})))

(defn lens-keys [m]
  (let [{:keys [out]} (meta m)]
    (with-meta
      (keys m)
      {:out (conj out (fn [v]
                        (with-meta
                          (zipmap v (vals m))
                          {:out out})))})))

(defn in [m k]
  (let [{:keys [out]} (meta m)]
    (with-meta
      (get m k)
      {:out (conj out (fn [v]
                        (with-meta
                          (assoc m k v)
                          {:out out})))})))

(defn out [v]
  (let [{[last-out] :out} (meta v)]
    (if-not last-out
      v
      (last-out v))))

(defn out-all [v]
  (let [{[last-out] :out} (meta v)]
    (if-not last-out
      v
      (out-all (last-out v)))))

(defn map-lens [coll f]
  (with-meta
    (map f coll)
    (meta coll)))
