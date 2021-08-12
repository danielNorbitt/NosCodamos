(ns noscodamos.database)

(defn cliente [] {:nome  "Daniel"
                  :cpf   "44937161857"
                  :email "daniel.oliveira@nubank.com.br"})

(defn cartao-de-credito [] {:numero   "1234567890123456"
                            :cvv      123
                            :validade "12/2020"
                            :limite   100000000
                            :cpf      "44937161857"})

(defn lista-de-compras [] [{:numero-cartao "1234567890123456" :data "12/01/2021" :valor 23.59 :estabelecimento "Uber" :categoria "Aplicativos"},
                           {:numero-cartao "1234567890123456" :data "25/06/2021" :valor 55 :estabelecimento "Netflix" :categoria "Entretenimento"},
                           {:numero-cartao "1234567890123456" :data "30/03/2021" :valor 0.59 :estabelecimento "Padaria" :categoria "Alimentação"},
                           {:numero-cartao "1234567890123456" :data "12/01/2021" :valor 23.59 :estabelecimento "99" :categoria "Aplicativos"},
                           {:numero-cartao "1234567890123456" :data "12/01/2021" :valor 23.59 :estabelecimento "Cabify" :categoria "Aplicativos"},
                           {:numero-cartao "1234567890123456" :data "25/06/2021" :valor 55 :estabelecimento "HBO Max" :categoria "Entretenimento"},
                           {:numero-cartao "1234567890123456" :data "25/06/2021" :valor 55 :estabelecimento "Disney Plus" :categoria "Entretenimento"},
                           {:numero-cartao "1234567890123456" :data "30/03/2021" :valor 0.59 :estabelecimento "Supermercado" :categoria "Alimentação"},
                           {:numero-cartao "1234567890123456" :data "30/04/2021" :valor 90.59 :estabelecimento "Kabum" :categoria "Compras"}])