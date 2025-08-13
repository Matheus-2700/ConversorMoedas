# Conversor de Moedas

Este é um projeto Java orientado a objetos que funciona como um conversor de moedas, utilizando a ExchangeRate-API para obter as taxas de câmbio em tempo real.

## Requisitos

- Java Development Kit (JDK) 11 ou superior
- Apache Maven

## Configuração da Chave da API

Para que o conversor funcione, você precisa inserir sua chave da ExchangeRate-API no código-fonte.

1. Abra o arquivo `src/main/java/com/currencyconverter/Main.java`.
2. Localize a linha:
   ```java
   String apiKey = "SUA_CHAVE_DA_API_AQUI"; // Substitua pela sua chave da API
   ```
3. Substitua `SUA_CHAVE_DA_API_AQUI` pela sua chave real da API.

## Como Compilar e Executar

Siga os passos abaixo para compilar e executar o projeto:

1. Navegue até o diretório raiz do projeto `CurrencyConverter` no seu terminal:
   ```bash
   cd /home/ubuntu/CurrencyConverter
   ```

2. Compile o projeto usando Maven:
   ```bash
   mvn clean install
   ```

3. Execute o aplicativo:
   ```bash
   mvn exec:java
   ```

## Funcionalidades

- **Conversão de Moedas**: Converte valores entre as moedas suportadas (ARS, BOB, BRL, CLP, COP, USD) utilizando taxas da ExchangeRate-API.
- **Histórico de Conversões**: Armazena e exibe as últimas conversões realizadas.
- **Interface de Console Interativa**: Menu simples para interação com o usuário.
- **Validações**: Impede entradas inválidas, como valores negativos ou moedas não suportadas.
- **Tratamento de Erros**: Lida com erros de rede, API e entrada do usuário.

## Estrutura do Projeto

O projeto segue os princípios de POO, com as seguintes classes principais:

- `Main.java`: Ponto de entrada da aplicação.
- `api/ApiService.java`: Responsável por fazer as requisições à ExchangeRate-API e processar as respostas JSON usando Gson.
- `converter/CurrencyConverter.java`: Contém a lógica de conversão de moedas e validações.
- `menu/Menu.java`: Gerencia a interface de console e a interação com o usuário.
- `history/ConversionHistory.java`: Gerencia o histórico de conversões.
- `history/ConversionRecord.java`: Representa um registro individual de conversão, incluindo data e hora.

## Testes Unitários

O projeto inclui testes unitários para as principais classes (`ApiServiceTest`, `CurrencyConverterTest`, `ConversionHistoryTest`, `MenuTest`) para garantir a funcionalidade e robustez do código.

Para rodar os testes, execute:
```bash
mvn test
```


