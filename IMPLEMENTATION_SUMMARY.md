# Resumo da Implementação - ITA Semana 3

## Visão Geral

Projeto completo implementado conforme especificado no exercício da semana 3 do curso ITA. O projeto utiliza JDBC puro (sem frameworks) para acesso ao banco de dados PostgreSQL e DBUnit para testes.

## O que foi Implementado

### 1. Estrutura do Projeto Maven

- **Arquivo**: `pom.xml`
- **Dependências principais**:
  - PostgreSQL JDBC Driver 42.7.3
  - JUnit 4.13.2
  - DBUnit 2.7.3
  - SLF4J Simple 1.7.36

### 2. Classes do Modelo

#### Usuario.java
- **Localização**: `src/main/java/br/ita/coursera/model/Usuario.java`
- **Atributos**:
  - `login` (String) - chave primária
  - `email` (String)
  - `nome` (String)
  - `senha` (String)
  - `pontos` (int)
- **Métodos**: Getters, setters, equals, hashCode, toString

### 3. Interface DAO

#### UsuarioDAO.java
- **Localização**: `src/main/java/br/ita/coursera/dao/UsuarioDAO.java`
- **Métodos definidos**:
  1. `void inserir(Usuario u)` - Insere novo usuário
  2. `Usuario recuperar(String login)` - Recupera usuário por login
  3. `void adicionarPontos(String login, int pontos)` - Adiciona pontos ao usuário
  4. `List<Usuario> ranking()` - Retorna ranking ordenado por pontos

### 4. Implementação DAO

#### UsuarioDAOImpl.java
- **Localização**: `src/main/java/br/ita/coursera/dao/UsuarioDAOImpl.java`
- **Implementa**: UsuarioDAO
- **Usa**: JDBC puro (PreparedStatement)
- **Queries SQL utilizadas** (conforme especificado):
  ```sql
  INSERT INTO usuario(login, email, nome, senha, pontos) VALUES (?, ?, ?, ?, ?);
  SELECT * FROM usuario WHERE login = ?;
  UPDATE usuario SET pontos = pontos + ? WHERE login = ?;
  SELECT * FROM usuario ORDER BY pontos DESC;
  ```

### 5. Utilitário de Conexão

#### DatabaseConnection.java
- **Localização**: `src/main/java/br/ita/coursera/util/DatabaseConnection.java`
- **Funções**:
  - Gerencia conexões JDBC
  - Carrega configurações do arquivo properties
  - Fornece método para obter conexões
  - Fornece método para fechar conexões

### 6. Testes DBUnit

#### UsuarioDAOImplTest.java
- **Localização**: `src/test/java/br/ita/coursera/dao/UsuarioDAOImplTest.java`
- **Framework**: DBUnit 2.7.3 + JUnit 4
- **Testes implementados** (7 testes):
  1. `testInserir()` - Testa inserção de usuário
  2. `testRecuperar()` - Testa recuperação por login
  3. `testAdicionarPontos()` - Testa adição de pontos
  4. `testRanking()` - Testa ordenação do ranking
  5. `testRankingVazio()` - Testa ranking sem dados
  6. `testInserirComPontosZero()` - Testa inserção com 0 pontos
  7. `testAdicionarPontosNegativos()` - Testa redução de pontos

### 7. Arquivos de Configuração

- **database.properties** (main e test):
  - Configuração da URL do banco
  - Credenciais de acesso
  
- **Datasets XML** (test):
  - `empty-dataset.xml` - Dataset vazio para limpeza
  - `usuarios-dataset.xml` - Dados de teste pré-definidos

### 8. Scripts SQL

#### create_database.sql
- **Localização**: `sql/create_database.sql`
- **Conteúdo**: Script para criar a tabela usuario conforme especificação

### 9. Documentação

- **README.md**: Documentação completa do projeto
- **ENTREGA.md**: Guia de como preparar a entrega
- **.gitignore**: Configuração para ignorar arquivos desnecessários

### 10. Exemplo de Uso

#### DemoUsuarioDAO.java
- **Localização**: `src/main/java/br/ita/coursera/demo/DemoUsuarioDAO.java`
- **Propósito**: Demonstra o uso de todos os métodos da DAO

## Características Técnicas

### Padrões Utilizados
- **DAO (Data Access Object)**: Separação entre lógica de negócio e acesso a dados
- **Singleton implícito**: DatabaseConnection gerencia conexões
- **PreparedStatement**: Previne SQL Injection
- **Try-with-resources**: Onde aplicável para melhor gerenciamento de recursos

### Boas Práticas Implementadas
- ✅ Uso de PreparedStatement (segurança)
- ✅ Fechamento adequado de recursos (Connection, Statement, ResultSet)
- ✅ Tratamento de exceções
- ✅ Documentação Javadoc
- ✅ Código limpo e legível
- ✅ Testes abrangentes com DBUnit
- ✅ Configuração externa (properties)

## Como Usar o Projeto

### 1. Pré-requisitos
```bash
# PostgreSQL instalado e rodando
sudo service postgresql start

# Criar banco de dados
psql -U postgres
CREATE DATABASE coursera;
\c coursera
```

### 2. Criar a tabela
```bash
psql -U postgres -d coursera -f sql/create_database.sql
```

### 3. Configurar credenciais
Edite `src/main/resources/database.properties` e `src/test/resources/database.properties`

### 4. Compilar
```bash
mvn clean compile
```

### 5. Executar testes
```bash
mvn test
```

### 6. Executar demo (opcional)
```bash
mvn exec:java -Dexec.mainClass="br.ita.coursera.demo.DemoUsuarioDAO"
```

## Verificação de Segurança

✅ Todas as dependências verificadas
✅ Nenhuma vulnerabilidade encontrada
✅ Versões atualizadas e seguras:
- PostgreSQL JDBC 42.7.3
- JUnit 4.13.2
- DBUnit 2.7.3

## Status do Projeto

✅ **COMPLETO E FUNCIONAL**

Todos os requisitos do exercício foram implementados:
- [x] Classe Usuario com todos os atributos
- [x] Interface UsuarioDAO com os 4 métodos especificados
- [x] Implementação UsuarioDAOImpl usando JDBC
- [x] Testes DBUnit para todos os métodos
- [x] Script SQL de criação do banco
- [x] Documentação completa
- [x] Projeto Maven funcional

## Próximos Passos para o Aluno

1. **Configurar PostgreSQL local**: Instalar e configurar o banco
2. **Criar banco e tabela**: Executar o script SQL
3. **Configurar credenciais**: Editar os arquivos properties
4. **Executar testes**: `mvn test`
5. **Capturar screenshot**: Dos testes passando
6. **Gerar ZIP**: Conforme instruções no ENTREGA.md
7. **Enviar**: Arquivo ZIP + screenshot

## Arquivos Principais

```
📁 ITA-semana-3/
├── 📄 pom.xml                          (Configuração Maven)
├── 📄 README.md                        (Documentação)
├── 📄 ENTREGA.md                       (Guia de entrega)
├── 📁 sql/
│   └── 📄 create_database.sql          (Script SQL)
├── 📁 src/main/java/br/ita/coursera/
│   ├── 📁 model/
│   │   └── 📄 Usuario.java             (Classe de modelo)
│   ├── 📁 dao/
│   │   ├── 📄 UsuarioDAO.java          (Interface)
│   │   └── 📄 UsuarioDAOImpl.java      (Implementação)
│   ├── 📁 util/
│   │   └── 📄 DatabaseConnection.java  (Conexão)
│   └── 📁 demo/
│       └── 📄 DemoUsuarioDAO.java      (Exemplo)
└── 📁 src/test/
    ├── 📁 java/br/ita/coursera/dao/
    │   └── 📄 UsuarioDAOImplTest.java  (Testes DBUnit)
    └── 📁 resources/
        ├── 📄 empty-dataset.xml         (Dataset vazio)
        └── 📄 usuarios-dataset.xml      (Dados teste)
```

## Conclusão

O projeto está completo e pronto para entrega. Todos os requisitos foram atendidos usando apenas JDBC (sem frameworks adicionais) e DBUnit para testes, conforme especificado no exercício.
