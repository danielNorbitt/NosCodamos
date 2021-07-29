(ns noscodamos.creditcard
  (:require [noscodamos.database :as n.database])
  )

(defn exibir-dados-cliente
  "Exibe dado de um cliente"
  [cliente]
  (let [{:keys [nome cpf email]} cliente]
    (println "Nome:" nome
             "CPF:" cpf
             "Email" email)))

(defn exibir-dados-cartao
  "Exibe dados de um cartão"
  [cartao]
  (let [{:keys [numero cvv validade limite]} cartao]
    (println "Número do cartão:" numero
             "CVV:" cvv
             "Validade:" validade
             "Limite:" limite)))

(defn formata
  "Formata a lista de compras"
  [item]
  (let [{:keys [data
                valor
                estabelecimento
                categoria]} item]
    (println "Data:" data
             "Valor:" valor
             "Estabelecimento:" estabelecimento
             "Categoria:" categoria)))

(defn listagem-de-compras
  "Lista todass compras realizadas"
  [lista]
  (println "----- Total de compras realizadas ---------")
  (map formata lista))



(defn agrupar-gastos-categoria
  "Agrupa gastos por categoria"
  [gastos]
  (group-by :categoria gastos))
(defn filter-agrupamento
  [gasto categoria]
  (filter #(= (key %) categoria) gasto))

(exibir-dados-cliente (n.database/cliente))
(exibir-dados-cartao (n.database/cartao-de-credito))
(listagem-de-compras (n.database/lista-de-compras))
(println (agrupar-gastos-categoria (n.database/lista-de-compras)))
(println (filter-agrupamento
           (agrupar-gastos-categoria (n.database/lista-de-compras)) "Compras"))

