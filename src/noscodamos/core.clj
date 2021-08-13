(ns noscodamos.core
  (:use clojure.pprint)
  (:require [noscodamos.database :as db]
            [noscodamos.logic :as l]))

(def conn (db/abre-conexao))

(println "Roda os schemas")
(db/define-schema conn)

(println "Cria um cliente")
(def cliente1 (l/cria-cliente "Daniel" "123456789" "daniel@nubank.com"))
(def cliente2 (l/cria-cliente "Babi" "12367890" "babi@nubank.com"))
(def cliente3 (l/cria-cliente "Robert" "1236789045" "robert@nubank.com"))
(db/cria-cliente! cliente1 conn)
(db/cria-cliente! cliente2 conn)
(db/cria-cliente! cliente3 conn)

(println "Cria um cartao")
(db/salvar-cartao! (l/cria-cartao "123456789"
                                  "1223334555"
                                  123
                                  "12/2020"
                                  1200.0) conn)
(db/salvar-cartao! (l/cria-cartao "123456789"
                                  "4567890"
                                  124
                                  "12/2020"
                                  12000.0) conn)
(db/salvar-cartao! (l/cria-cartao "1236789045"
                                  "12345"
                                  123
                                  "12/2020"
                                  800.0) conn)
(println "Cria um compra")
(db/salvar-cartao! (l/cria-compra "12/08/2021"
                                  10.0
                                  "Ifood"
                                  "Alimentacao"
                                  "1223334555"
                                  []) conn)
(db/salvar-cartao! (l/cria-compra "12/08/2021"
                                  12.0
                                  "Ifood"
                                  "Alimentacao"
                                  "12345"
                                  []) conn)
(db/salvar-cartao! (l/cria-compra "12/08/2021"
                                  10000.0
                                  "Ifood"
                                  "Alimentacao"
                                  "12367890"
                                  []) conn)
(db/salvar-cartao! (l/cria-compra "12/08/2021"
                                  100.0
                                  "Ifood"
                                  "Alimentacao"
                                  "1223334555"
                                  []) conn)
(db/salvar-cartao! (l/cria-compra "12/08/2021"
                                  800.0
                                  "Ifood"
                                  "Alimentacao"
                                  "4567890"
                                  []) conn)

(println "Pega todos os clientes")
(pprint (db/pegar-todos-clientes! conn))
(println "Pega todos os cartoes")
(pprint (db/pegar-todos-cartoes! conn))
(println "Pega todos as compras")
(pprint (db/pegar-todos-compras! conn))
(pprint "Pega compras de um cartao")
(db/pegar-todas-compras-por-cartao conn "12233345550")

(db/pegar-cliente-mais-compras! conn)

(db/apaga-banco)