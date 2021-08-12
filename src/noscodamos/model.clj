(ns noscodamos.model
  (:require [schema.core :as s]
            [clojure.string :as str]))

(use 'java-time)

(def Moeda (s/constrained s/Num #(>= % 0)))
(defn not-blank [string] (not (str/blank? string)))
(def StrNotBlank (s/constrained s/Str not-blank))

(def Cliente
  {:cpf   StrNotBlank
   :nome  StrNotBlank
   :email StrNotBlank})

(def Cartao
  {:cpf      StrNotBlank
   :numero   StrNotBlank
   :cvv      s/Num
   :validade StrNotBlank
   :limite   Moeda})

(def Compra
  {:numero-cartao   StrNotBlank
   :data            StrNotBlank
   :valor           Moeda
   :estabelecimento StrNotBlank
   :categoria       StrNotBlank})