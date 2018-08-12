;;;; Author: Bas Straathof
;; Date of last revision: August 12, 2018


;; name space declaration
(ns bach-prelude-loop.core
  (:use
   [overtone.live]
   [overtone.inst.piano]))


;; broken chords of Bach's prelude in C major
(def a [0 4.5 7 12 16 7 12 16]); C major
(def b [0 2 9 14 17 9 14 17]); D minor 7
(def c [-1 2 7 14 17 7 14 17]); G major 7


;; hashmap of keys to integers
(def keys-seq ["A" "A#" "B" "C" "C#" "D" "D#" "E" "F" "F#" "G" "G#"])


(defn prelude
  "This function plays a key sequence which represents the first
  six bars of Bach's Prelude in C Major. It takes two integers
  as input arguments."
  ([key bpm]
   (dorun
    (map-indexed
      #(at ((metronome bpm) (+ ((metronome bpm)) (/ %1 2)))
          (piano (+ (+ 57 %2) key)))
      (->> [a a b b c c] (flatten))))))


(defn bach-print [speed key]
  "This function prints the key and the tempo of the prelude on
  each iteration of the loop."
  (println
    (str "This is Bach's Prelude in C major, but played in " (keys-seq key) ".
         with a tempo of "speed " bpm.")))


(defn bach-loop []
  "Function to loop through the prelude and arbitrarily choose
  a key and tempo (range 100-240 bpm) upon each new iteration."
  (let [time (now)]
   (def rand-speed (+ (rand-int 140) 100))
   (def rand-key (rand-int 12))
   (bach-print rand-speed rand-key)
   (at time (prelude rand-key rand-speed))
   (apply-at (+ (* (/ 60 rand-speed) 24000) time) bach-loop)))


;; play the prelude
(bach-loop)
(stop)
