# ==============================================================================
# Makefile - jogo-blackjack-java (Blackjack / Jogo de 21 em Java)
# Automação de compilação, testes e execução
# ==============================================================================

MVN = mvn

all: build

build:
	$(MVN) clean package -DskipTests=false

test:
	$(MVN) test

run: build
	java -jar target/jogo-blackjack-java-1.0.0.jar

clean:
	$(MVN) clean

.PHONY: all build test run clean
