Conversor de Moedas - Projeto em Java Challenge ONE - Oracle

Este é um projeto em Java que implementa um conversor de moedas utilizando uma API para obter as taxas de câmbio em tempo real. O programa permite ao usuário realizar conversões entre diferentes moedas através de uma interface de linha de comando (CLI).

Funcionalidades:
O usuário pode escolher entre várias opções de conversão, como Dólar para Real, Real para Dólar, entre outras.

O programa interage com uma API externa (Extended Rate) para obter as cotações em tempo real.

A conversão de valores é feita de acordo com as taxas de câmbio obtidas da API.

Utiliza a biblioteca Gson para desserializar o formato JSON da resposta da API.

Tecnologias utilizadas:
Java: Linguagem de programação principal.

API Extended Rate: Para obter as taxas de câmbio em tempo real.

Gson: Biblioteca para conversão de JSON em objetos Java.

Como executar:
Clone o repositório:

bash
Copiar
Editar
git clone https://github.com/seu-usuario/conversor-de-moedas.git

Compile o projeto com o comando:

bash
Copiar
Editar
javac -cp "lib/gson-2.10.1.jar" -d out src/*.java

Execute o programa com:

bash
Copiar
Editar
java -cp "lib/gson-2.10.1.jar;out" ConversorMoedas
