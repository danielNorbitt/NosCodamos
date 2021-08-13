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

(def schema [
             ; Modelo Cliente
             {:db/ident       :cliente/cpf
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one}
             {:db/ident       :cliente/nome
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one}
             {:db/ident       :cliente/email
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one}

             ; Modelo Cartao
             {:db/ident       :cartao/cpf
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one}
             {:db/ident       :cartao/numero
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one}
             {:db/ident       :cartao/validade
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one}
             {:db/ident       :cartao/cvv
              :db/valueType   :db.type/long
              :db/cardinality :db.cardinality/one}
             {:db/ident       :cartao/limite
              :db/valueType   :db.type/double
              :db/cardinality :db.cardinality/one}

             ; Modelo Compras
             {:db/ident       :compra/numero-cartao
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one}
             {:db/ident       :compra/data
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one}
             {:db/ident       :compra/valor
              :db/valueType   :db.type/double
              :db/cardinality :db.cardinality/one}
             {:db/ident       :compra/estabelecimento
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one}
             {:db/ident       :compra/categoria
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one}
             ])

