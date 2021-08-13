(ns noscodamos.database
  (:require [datomic.api :as d]
            [noscodamos.model :as m]))

(def db-uri "datomic:dev://localhost:4334/nubank")

(defn abre-conexao []
  (d/create-database db-uri)
  (d/connect db-uri))

(defn define-schema
  [conexao]
  (d/transact conexao m/schema))

(defn apaga-banco []
  (d/delete-database db-uri))

(defn cria-cliente!
  [cliente conexao]
  (d/transact conexao [cliente]))

(defn salvar-cartao!
  [cartao conexao]
  (d/transact conexao [cartao]))

(defn salvar-compras-realizadas!
  [compra conexao]
  (d/transact conexao [compra]))

(defn pegar-todos-clientes! [conexao]
  (d/q '[:find [pull ?clientes [*] .]
         :where [?clientes :cliente/nome]]
       (d/db conexao)))

(defn pegar-todos-cartoes! [conexao]
  (d/q '[:find (pull ?cartoes [*])
         :where [?cartoes :cartao/numero]]
       (d/db conexao)))

(defn pegar-todos-compras! [conexao]
  (d/q '[:find (pull ?compras [*])
         :where [?compras :compra/valor]]
       (d/db conexao)))

(defn pegar-todas-compras-por-cartao
  [conexao numero-cartao]
  (d/q '[:find (pull ?e [*])
         :in $ ?numero
         :where [?e :compra/numero-cartao ?numero]]
       (d/db conexao) numero-cartao))

(defn pegar-cliente-mais-compras!
  [conexao]
  (d/q '[:find (frequencies [?valor ?cpf])
         :where [_ :cliente/cpf ?cpf]
         [?cartao-id :cartao/cpf ?cpf]
         [?cartao-id :cartao/numero ?numero]
         [?compras-id :compra/numero-cartao ?numero]
         [?compras-id :compra/valor ?valor]
         ]
       (d/db conexao)))

