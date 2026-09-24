# ==============================================================================
# Makefile - Project_Cards (Blackjack / Jogo de 21 em Java)
# Automação de compilação, testes e execução
# ==============================================================================

MVN = mvn

all: build

build:
	$(MVN) clean package -DskipTests=false

test:
	$(MVN) test

run: build
	java -jar target/blackjack-1.0.0.jar

clean:
	$(MVN) clean

.PHONY: all build test run clean
