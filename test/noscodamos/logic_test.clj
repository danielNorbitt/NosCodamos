(ns noscodamos.logic_test
  (:require [clojure.test :refer :all]
            [noscodamos.logic :refer :all]
            [noscodamos.model :as m]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [schema-generators.complete :as c]))

(use 'java-time)

(deftest adiciona-compras-lista
  (testing "Testa o sucesso da criação de uma compra com Limite do cartão > valor"
    (let [cliente (cria-cliente "Daniel" "123456789" "danie.oliveira@nubank.com.br")
          cartao (cria-cartao (:cpf cliente) "12345" 123 "10/2021" 1000.0)
          nova-compra (cria-compra "11/12/2021" 200.0 "uber" "mobilidade" (:numero cartao) [])]
      (is (= nova-compra
             {:numero-cartao   (:numero cartao),
              :data            "11/12/2021",
              :valor           200.0,
              :categoria       "mobilidade",
              :estabelecimento "uber"}))))

  (testing "Testa a falha da criação da compra com Valor > Limite do cartão"
    (let [cliente (cria-cliente "Daniel" "123456789" "danie.oliveira@nubank.com.br")
          cartao (cria-cartao (:cpf cliente) "12345" 123 "10/2021" 1000.0)]
      (is (nil? (cria-compra "11/12/2021" 1200.0 "uber" "mobilidade" (:numero cartao) [])))))

  (testing "Testa a falha da criação de compra quando Valor > Valor atual do cartão"
    (let [cliente (cria-cliente "Daniel" "123456789" "danie.oliveira@nubank.com.br")
          cartao (cria-cartao (:cpf cliente) "12345" 123 "10/2021" 1000.0)]
      (is nil? (cria-compra "11/12/2021" 800.0 "uber" "mobilidade" (:numero cartao) [{:data            "11/12/2021"
                                                                                      :valor           (400.0)
                                                                                      :estabelecimento "ifodd"
                                                                                      :categoria       "alimentacao"
                                                                                      :numero-cartao   "12345"}]))))

  (testing "Teste a falha da criação com valor <= 0"
    (let [cliente (cria-cliente "Daniel" "123456789" "danie.oliveira@nubank.com.br")
          cartao (cria-cartao (:cpf cliente) "12345" 123 "10/2021" 1000.0)]
      (is nil? (cria-compra "11/12/2021" 0.0 "uber" "mobilidade" (:numero cartao) []))))
  )

(deftest listagem-de-compras-teste
  (testing "Testa o sucesso da listagem de compras com um cartão na lista"
    (let [compras [{:data            "11/12/2021"
                    :valor           400.0
                    :estabelecimento "ifodd"
                    :categoria       "alimentacao"
                    :numero-cartao   "12345"}
                   {:data            "11/12/2021"
                    :valor           300.0
                    :estabelecimento "ifodd"
                    :categoria       "alimentacao"
                    :numero-cartao   "123456"}]]
      (is (= {:data            "11/12/2021"
              :valor           400.0
              :estabelecimento "ifodd"
              :categoria       "alimentacao"
              :numero-cartao   "12345"}
             (compras-do-cartao "12345" compras)))))

  (testing "Testa a listagem de compras com um cartão que não esta na lista"
    (let [compras [{:data            "11/12/2021"
                    :valor           (400.0)
                    :estabelecimento "ifodd"
                    :categoria       "alimentacao"
                    :numero-cartao   "12345"}
                   {:data            "11/12/2021"
                    :valor           (400.0)
                    :estabelecimento "ifodd"
                    :categoria       "alimentacao"
                    :numero-cartao   "123456"}]]
      (is (empty? (compras-do-cartao "1234567" compras)))))

  (testing "Testa a listagem de compras com um cartão e uma lista de compras vazia"
    (is (empty? (compras-do-cartao "12345" []))))
  )


(defn geradorCasos
  [categoria valor numero-cartao]
  (c/complete {:categoria categoria :valor valor :numero-cartao numero-cartao} m/Compra))

(deftest agrupamento-por-categoria

  (testing "Propriedade "
    (prop/for-all [categorias (gen/vector (gen/elements ["Alimentação" "Mobilidade" "Educação"]) 1 5)
                   valores (gen/vector (gen/double* {:min 1 :max 1000 :infinite? true :NaN? false}))
                   numero-cartaos (gen/vector (gen/elements ["1234" "132" "321"]) 1 5)]
                  (let [cartao-escolhido (rand-nth numero-cartaos)
                        valor-escolhido (rand-nth valores)
                        compras (map #(geradorCasos % valor-escolhido (rand-nth numero-cartaos)) categorias)
                        total-inicial (reduce + 0 (filter #(= (:numero-cartao %) cartao-escolhido) compras))
                        total-final (reduce #(+ (:total %2) %1) 0 (agrupar-total-gastos-categoria cartao-escolhido compras))]
                    (is (= total-inicial total-final))))
    )

  )

