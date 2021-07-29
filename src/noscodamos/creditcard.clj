(ns noscodamos.creditcard
  (:require [noscodamos.database :as db]))

(use 'java-time)

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

(defn pegar-mes-data [data]
  (as (local-date "dd/MM/yyyy" data) :month-of-year))

(defn calculo-fatura-mes
  "Calculo da fatura do mes"
  [fatura mes]
  (->>
    (fatura)
    (filter #(= (pegar-mes-data (:data %)) mes))
    (map :valor)
    (reduce + 0)))

(defn busca-por
  ""
  [compras busca]
  (->> (compras)
       (filter #(or (= (:valor %) busca)
                    (= (:estabelecimento %) busca)))
       ))

(exibir-dados-cliente (db/cliente))
(exibir-dados-cartao (db/cartao-de-credito))
(listagem-de-compras (db/lista-de-compras))
(println (agrupar-gastos-categoria (db/lista-de-compras)))
(println (filter-agrupamento
           (agrupar-gastos-categoria (db/lista-de-compras)) "Compras"))

(println "Total da fatura do mes é" (calculo-fatura-mes db/lista-de-compras 1))
(println "Todas as compras" (busca-por db/lista-de-compras 23.59))