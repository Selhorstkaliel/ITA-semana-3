# Guia de Entrega - ITA Semana 3

## Objetivo

Este documento fornece instruções sobre como preparar e entregar o projeto conforme solicitado no exercício.

## Requisitos de Entrega

Conforme especificado no exercício, deve ser entregue:
1. Um projeto (Eclipse ou NetBeans) em arquivo .zip
2. Uma imagem da tela mostrando o resultado da execução dos testes

## Como Gerar o Arquivo .zip

### Opção 1: Usando Maven (Recomendado)

```bash
# 1. Compilar o projeto
mvn clean compile

# 2. Compilar os testes
mvn test-compile

# 3. Criar o pacote
mvn package -DskipTests

# 4. O arquivo .jar será gerado em target/jdbc-usuario-1.0-SNAPSHOT.jar
```

### Opção 2: Criando ZIP manualmente

```bash
# No diretório raiz do projeto
zip -r ITA-semana-3-projeto.zip . -x "target/*" ".git/*" ".idea/*" "*.iml"
```

### Opção 3: Importar no Eclipse/NetBeans

#### Eclipse:
1. File → Import → Existing Maven Projects
2. Selecione o diretório do projeto
3. File → Export → Archive File
4. Selecione o projeto e escolha o destino do arquivo .zip

#### NetBeans:
1. File → Open Project
2. Selecione o diretório do projeto
3. Right-click no projeto → Export → ZIP
4. Escolha o destino do arquivo .zip

## Como Executar os Testes

### Pré-requisitos

Antes de executar os testes, certifique-se de:

1. **PostgreSQL instalado e rodando**
   ```bash
   sudo service postgresql start
   ```

2. **Banco de dados "coursera" criado**
   ```bash
   psql -U postgres
   CREATE DATABASE coursera;
   \c coursera
   ```

3. **Tabela usuario criada**
   ```bash
   psql -U postgres -d coursera -f sql/create_database.sql
   ```

4. **Credenciais configuradas**
   - Edite `src/test/resources/database.properties` com suas credenciais

### Executar os Testes

```bash
# Executar todos os testes
mvn test

# Executar um teste específico
mvn test -Dtest=UsuarioDAOImplTest#testInserir
mvn test -Dtest=UsuarioDAOImplTest#testRecuperar
mvn test -Dtest=UsuarioDAOImplTest#testAdicionarPontos
mvn test -Dtest=UsuarioDAOImplTest#testRanking
```

## Como Capturar a Tela dos Testes

### No Terminal

Depois de executar `mvn test`, você verá uma saída similar a:

```
-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running br.ita.coursera.dao.UsuarioDAOImplTest
Tests run: 7, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 2.5 sec

Results :

Tests run: 7, Failures: 0, Errors: 0, Skipped: 0

[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```

Capture uma screenshot desta saída.

### No Eclipse

1. Right-click no arquivo de teste → Run As → JUnit Test
2. A aba JUnit mostrará os resultados
3. Capture uma screenshot da aba JUnit mostrando todos os testes passando (verde)

### No NetBeans

1. Right-click no arquivo de teste → Test File
2. A janela Test Results mostrará os resultados
3. Capture uma screenshot mostrando todos os testes passando

## Verificação dos Testes

Os seguintes testes devem PASSAR:

✓ testInserir - Testa inserção de novo usuário
✓ testRecuperar - Testa recuperação de usuário por login
✓ testAdicionarPontos - Testa adição de pontos
✓ testRanking - Testa ranking ordenado por pontos
✓ testRankingVazio - Testa ranking com banco vazio
✓ testInserirComPontosZero - Testa inserção com pontos zero
✓ testAdicionarPontosNegativos - Testa redução de pontos

## Estrutura do Arquivo ZIP

O arquivo .zip deve conter:

```
ITA-semana-3/
├── pom.xml
├── README.md
├── ENTREGA.md (este arquivo)
├── sql/
│   └── create_database.sql
├── src/
│   ├── main/
│   │   ├── java/br/ita/coursera/
│   │   │   ├── model/Usuario.java
│   │   │   ├── dao/UsuarioDAO.java
│   │   │   ├── dao/UsuarioDAOImpl.java
│   │   │   └── util/DatabaseConnection.java
│   │   └── resources/
│   │       └── database.properties
│   └── test/
│       ├── java/br/ita/coursera/dao/
│       │   └── UsuarioDAOImplTest.java
│       └── resources/
│           ├── database.properties
│           ├── empty-dataset.xml
│           └── usuarios-dataset.xml
└── .gitignore
```

**NÃO** incluir no ZIP:
- Diretório `target/`
- Diretório `.git/`
- Diretório `.idea/` ou `.settings/`
- Arquivos `.iml` ou `.project`

## Checklist de Entrega

Antes de fazer a entrega, verifique:

- [ ] Banco de dados PostgreSQL criado e configurado
- [ ] Tabela usuario criada no banco
- [ ] Arquivo database.properties configurado com credenciais corretas
- [ ] Projeto compila sem erros (`mvn compile`)
- [ ] Testes compilam sem erros (`mvn test-compile`)
- [ ] Todos os testes passam (`mvn test`)
- [ ] Screenshot dos testes capturada
- [ ] Arquivo .zip criado com a estrutura correta
- [ ] README.md está completo e atualizado

## Documentação Adicional

Para mais informações sobre o projeto, consulte:
- `README.md` - Documentação completa do projeto
- `sql/create_database.sql` - Script de criação do banco
- Comentários nos arquivos fonte Java

## Suporte

Em caso de problemas:
1. Verifique se o PostgreSQL está rodando
2. Verifique as credenciais no arquivo database.properties
3. Verifique se o banco e tabela foram criados corretamente
4. Consulte os logs de erro no console

## Notas Importantes

- O projeto usa **apenas JDBC puro**, sem frameworks adicionais
- Todos os métodos da interface UsuarioDAO estão implementados
- Cada método possui testes correspondentes usando DBUnit
- As consultas SQL usadas são exatamente as especificadas no exercício
