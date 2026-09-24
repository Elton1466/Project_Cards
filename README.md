# 🃏 Project Cards - Jogo de Blackjack (21) Multiplayer em Java

Este repositório contém uma aplicação orientada a objetos desenvolvida em **Java 17+** para o clássico jogo de cartas **Blackjack (21)** com suporte ao modo **Multiplayer (2 Jogadores Humanos contra a Mesa/Dealer)**, com foco em boas práticas de Engenharia de Software, Arquitetura Limpa, Testes Unitários e Automação de Build via Maven.

---

## 🏗️ Arquitetura do Sistema e Padrões Aplicados

A aplicação foi projetada seguindo os princípios de Responsabilidade Única (SRP), Encapsulamento, OCP e Legibilidade de Código Clean Code:

- **Modo Multiplayer (2 Jogadores vs Dealer)**:
  - Permite que dois jogadores humanos joguem em turnos no mesmo terminal (*hotseat/pass-and-play*), com saldos de fichas, apostas e decisões independentes contra a mesa (Dealer).
- **Orientação a Objetos e Imutabilidade**:
  - `Naipe` & `ValorCarta`: Enumeradores encapsulando os símbolos (♠, ♥, ♦, ♣) e os valores de pontuação das cartas.
  - `Carta`: Entidade imutável que combina Naipe e Valor.
  - `Baralho`: Coleção de 52 cartas com reabastecimento automático e embaralhamento via `Collections.shuffle`.
  - `Mao`: Algoritmo inteligente que ajusta dinamicamente a pontuação do Ás (11 ou 1) para otimizar a mão do jogador sem estourar 21.
- **Hierarquia de Participantes**:
  - `Jogador`: Classe base abstrata para participantes do jogo.
  - `Humano`: Especialização com controle de saldo de fichas, apostas e recompensas (pagamento 3:2 no Blackjack natural).
  - `Dealer`: Inteligência da banca que segue a regra oficial do cassino (compra obrigatória se pontuação < 17).
- **Interface e Serviços**:
  - `JogoService`: Orquestra o ciclo de vida das rodadas multiplayer, distribuição de cartas e decisão de vencedores.
  - `ConsoleUI`: Interface gráfica via terminal com formatação ASCII, exibição das mãos de todos os participantes e sanitização de entradas.

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem**: Java 17 (ISO Standard)
- **Gerenciador de Build**: Apache Maven (`pom.xml`)
- **Framework de Testes**: JUnit 5 (`junit-jupiter`)
- **Automação**: GNU `make`

---

## ⚙️ Como Compilar e Executar

### Pré-requisitos
- JDK 17 ou superior instalado.
- Apache Maven instalado.

### 1. Compilar o Projeto
Para realizar a compilação limpa, checagem de tipos e empacotamento em arquivo JAR:
```bash
make build
```
ou via Maven direto:
```bash
mvn clean package
```

### 2. Executar os Testes Unitários
Para rodar a suíte automatizada de testes do JUnit 5:
```bash
make test
```
ou:
```bash
mvn test
```

### 3. Executar o Jogo Multiplayer no Terminal
Para iniciar a partida interativa de Blackjack no console:
```bash
make run
```
ou executando o JAR gerado:
```bash
java -jar target/blackjack-1.0.0.jar
```

---

## 🧪 Testes Unitários

A suíte de testes em `MaoTest.java` cobre cenários críticos de regra de jogo:
- ✅ Cálculo de pontuações sem Ás.
- ✅ Reajuste automático do valor do Ás de 11 para 1 ao ultrapassar 21 pontos.
- ✅ Combinação de múltiplos Áses em uma mesma mão.
- ✅ Detecção imediata de Blackjack Natural (21 pontos na abertura com 2 cartas).
- ✅ Validação de estouro (*Bust*).

---

## 🤖 Nota de Transparência e Uso de IA

> [!NOTE]
> A arquitetura orientada a objetos, suporte multiplayer, implementação das classes em Java 17, suíte de testes unitários com JUnit 5, automação via Maven (`pom.xml`) e a elaboração desta documentação foram desenvolvidas com a assistência de **Inteligência Artificial (Google Antigravity AI Agent)** no processo de engenharia e refatoração de código.

---

## 👤 Autoria
Desenvolvido por **Elton Araújo** no âmbito do Bacharelado em Sistemas de Informação.