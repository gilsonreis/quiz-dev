#!/bin/bash

# ────────────────────────────────
# Spring Boot Dev Helper Script ☕
# by Gilson Reis Edition
# ────────────────────────────────

# Alias temporários (válidos só nesse terminal)
alias sb-dev='mvn spring-boot:run -Dspring-boot.run.fork=true -Dspring-boot.run.jvmArguments="-Dspring.devtools.restart.enabled=true"'
alias sb-watch='while true; do mvn compile -Dcompiler.useIncrementalCompilation=true; sleep 2; done'
alias sb-test='mvn test'
alias sb-build='mvn clean install'

echo "✅ Aliases carregados com sucesso:"
echo " - sb-dev     → roda a aplicação com hot reload"
echo " - sb-watch   → recompila .class a cada 2s (pra usar junto com sb-dev)"
echo " - sb-test    → roda os testes"
echo " - sb-build   → builda o projeto"
echo " - sb-xablau  → motivacional"

# Dica bônus
echo ""
echo "⚠️  Dica: use dois terminais:"
echo "   1. Terminal 1 → sb-dev"
echo "   2. Terminal 2 → sb-watch"
echo ""
echo "🧠 LiveReload: ative spring.devtools.livereload.enabled=true e use extensão no navegador"