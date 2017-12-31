# lenses

Simple proof-of-concept lenses implementation in Clojure

[![Clojars Project](https://img.shields.io/clojars/v/rocks.clj/lenses.svg)](https://clojars.org/rocks.clj/lenses)

## Usage

```clojure
(ns ...
  (:require [rocks.clj.lenses.core :refer [lens-values
                                           lens-keys
                                           in
                                           out
                                           out-all
                                           map-lens]]))

(-> {:data {:foo [1 2 3]
            :bar {:baz 1
                  :qux 2}}}

    (in :data) ;; lens into data

    (in :foo) ;; lens into :foo
    (conj 4) ;; add 4 to [1 2 3]
    out ;; lens out

    (in :bar) ;; lens into :bar
    (assoc :tux 3) ;; add :tux -> 3 mapping
    lens-values ;; lens into values
    (map-lens inc) ;; increment all values
    out ;; lens out

    lens-keys ;; lens in keys
    (map-lens name) ;; convert all keywords to strings
    out-all ;; lens outmost
    )

    => {:data {:foo [1 2 3 4]
        :bar  {"baz" 2
               "qux" 3
               "tux" 4}}}
```

## License

Copyright © 2018 Eduard Knyshov

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
