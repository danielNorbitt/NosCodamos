(ns noscodamos.logic
  (:require [noscodamos.database :as db]
            [noscodamos.model :as m]
            [schema.core :as s]))

(use 'java-time)
(s/set-fn-validation! true)

(s/defn cria-cliente :- m/Cliente
  [nome :- m/StrNotBlank
   cpf :- m/StrNotBlank
   email :- m/StrNotBlank]
  {:cpf cpf :nome nome :email email})

(s/defn cria-cartao :- m/Cartao
  [cpf :- m/StrNotBlank
   numero :- m/StrNotBlank
   cvv :- s/Num
   validade :- m/StrNotBlank
   limite :- m/Moeda]
  {:cpf cpf :numero numero :cvv cvv :validade validade :limite limite})

(s/defn pega-cartao-cliente :- [m/Cartao]
  [cpf :- m/StrNotBlank
   cartoes :- [m/Cartao]]
  (filter #(= cpf (:cpf %)) cartoes))

(s/defn compras-do-cartao :- [m/Compra]
  ([numero-cartao :- m/StrNotBlank
    compras :- [m/Compra]]
   (filter #(= (:numero-cartao %) numero-cartao) compras)))

(s/defn total-cartao :- m/Moeda
  [numero-cartao :- m/StrNotBlank
   compras :- [m/Compra]]
  (->> (compras-do-cartao numero-cartao compras)
       (map :valor)
       (reduce + 0)))

(s/defn tem-limite? :- s/Bool
  [cartao :- m/StrNotBlank
   valor :- m/Moeda
   compras :- [m/Compra]]
  (let [total (total-cartao cartao compras)]
    (<= (+ total valor) (:limite cartao))))

(s/defn cria-compra :- (s/maybe m/Compra)
  [data :- m/StrNotBlank
   valor :- m/Moeda
   estabelecimento :- m/StrNotBlank
   categoria :- m/StrNotBlank
   numero-cartao :- m/StrNotBlank
   compras :- [m/Compra]]
  (if (and (tem-limite? numero-cartao valor compras)
           (pos? valor))
    {:data            data
     :valor           valor
     :estabelecimento estabelecimento
     :categoria       categoria
     :numero-cartao   numero-cartao}
    nil))

(s/defn exibir-dados-cliente
  "Exibe dado de um cliente"
  [cliente :- [m/Cliente]]
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

(s/defn listagem-de-compras
  "Lista todass compras realizadas"
  [lista :- [m/Compra]]
  (println "----- Total de compras realizadas ---------")
  (map formata lista))

(s/defn agrupar-total-gastos-categoria :- {m/StrNotBlank m/Moeda}
  "Agrupa gastos por categoria"
  [cartao :- m/Cartao
   gastos :- [m/Compra]]
  (map #({:categoria (key %)
          :total     (total-cartao (:numero cartao) (val %))})
       (group-by :categoria (compras-do-cartao cartao gastos))))

(s/defn pegar-mes-data :- s/Int
  [data :- m/StrNotBlank]
  (as (local-date "dd/MM/yyyy" data) :month-of-year))

(s/defn calculo-fatura-mes :- m/Moeda
  "Calculo da fatura do mes"
  [numero-cartao :- m/StrNotBlank
   fatura :- [m/Compra]
   mes :- s/Int]
  (->> (fatura)
       (compras-do-cartao numero-cartao)
       (filter #(= (pegar-mes-data (:data %)) mes))
       (map :valor)
       (reduce + 0)))

(s/defn busca-por :- [m/Compra]
  "Busca as compras com valor ou estabelecimento passado"
  [compras :- [m/Compra]
   busca :- (or m/Moeda m/StrNotBlank)]
  (->> (compras)
       (filter #(or (= (:valor %) busca)
                    (= (:estabelecimento %) busca)))))

