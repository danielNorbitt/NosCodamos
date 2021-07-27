(ns noscodamos.creditcard)

(def cliente {:nome "Daniel" :cpf "44937161857" :email "daniel.oliveira@nubank.com.br"})

(def cartao-de-credito {:numero "1234567890123456" :cvv 123 :validade "12/2020" :limite 100000000})

(defn exibir-dados-cliente
  "Exibe dado de um cliente"
  [cliente]
  (let [{nome  :nome
         cpf   :cpf
         email :email} cliente]
    (println "Nome:" nome
             "CPF:" cpf
             "Email" email)))

(defn exibir-dados-cartao
  "Exibe dados de um cartão"
  [cartao]
  (let [{numero   :numero
         cvv      :cvv
         validade :validade
         limite   :limite} cartao]
    (println "Número do cartão:" numero
             "CVV:" cvv
             "Validade:" validade
             "Limite:" limite)))

(exibir-dados-cliente cliente)
(exibir-dados-cartao cartao-de-credito)

(def lista-de-compras [{:data "12/01/2021" :valor 23.59 :estabelecimento "Uber" :categoria "Aplicativos"}
                       {:data "25/06/2021" :valor 55 :estabelecimento "Netflix" :categoria "Entreterimento"}
                       {:data "30/03/2021" :valor 0.59 :estabelecimento "Padaria" :categoria "Alimentacao"}
                       ])

(defn formata
  "Formata a lista de compras"
  [item]
  (let [{data            :data,
         valor           :valor
         estabelecimento :estabelecimento
         categoria       :categoria} item]
    (println "Data:" data
             "Valor:" valor
             "Estabelecimento:" estabelecimento
             "Categoria:" categoria)))

(defn listagem-de-compras
  "Lista todass compras realizadas"
  [lista]
  (println "----- Total de compras realizadas ---------")
  (map formata lista))

(listagem-de-compras lista-de-compras)