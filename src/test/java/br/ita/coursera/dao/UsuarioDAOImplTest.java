package br.ita.coursera.dao;

import br.ita.coursera.model.Usuario;
import br.ita.coursera.util.DatabaseConnection;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Testes DBUnit para a classe UsuarioDAOImpl.
 * Testa todos os métodos da interface UsuarioDAO.
 */
public class UsuarioDAOImplTest extends DBTestCase {
    
    private UsuarioDAO usuarioDAO;

    public UsuarioDAOImplTest() {
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "org.postgresql.Driver");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:postgresql://localhost:5432/coursera");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "postgres");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "postgres");
    }

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        usuarioDAO = new UsuarioDAOImpl();
        
        // Criar a tabela se não existir
        createTableIfNotExists();
    }

    @Override
    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        InputStream is = getClass().getClassLoader().getResourceAsStream("empty-dataset.xml");
        return new FlatXmlDataSetBuilder().build(is);
    }

    @Override
    protected DatabaseOperation getSetUpOperation() {
        return DatabaseOperation.CLEAN_INSERT;
    }

    @Override
    protected DatabaseOperation getTearDownOperation() {
        return DatabaseOperation.DELETE_ALL;
    }

    /**
     * Cria a tabela usuario se ela não existir.
     */
    private void createTableIfNotExists() {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.createStatement();
            
            String createTableSQL = "CREATE TABLE IF NOT EXISTS usuario (" +
                    "login text NOT NULL, " +
                    "email text, " +
                    "nome text, " +
                    "senha text, " +
                    "pontos integer, " +
                    "CONSTRAINT usuario_pkey PRIMARY KEY (login))";
            
            stmt.execute(createTableSQL);
            
        } catch (Exception e) {
            System.err.println("Aviso: Não foi possível criar a tabela: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Teste do método inserir.
     * Verifica se um novo usuário é inserido corretamente no banco de dados.
     */
    @Test
    public void testInserir() {
        // Criar um novo usuário
        Usuario usuario = new Usuario("testuser", "test@email.com", "Test User", "senha123", 50);
        
        // Inserir o usuário
        usuarioDAO.inserir(usuario);
        
        // Recuperar o usuário inserido
        Usuario usuarioRecuperado = usuarioDAO.recuperar("testuser");
        
        // Verificações
        assertNotNull("O usuário inserido deve ser recuperado", usuarioRecuperado);
        assertEquals("Login deve ser igual", "testuser", usuarioRecuperado.getLogin());
        assertEquals("Email deve ser igual", "test@email.com", usuarioRecuperado.getEmail());
        assertEquals("Nome deve ser igual", "Test User", usuarioRecuperado.getNome());
        assertEquals("Senha deve ser igual", "senha123", usuarioRecuperado.getSenha());
        assertEquals("Pontos devem ser iguais", 50, usuarioRecuperado.getPontos());
    }

    /**
     * Teste do método recuperar.
     * Verifica se um usuário é recuperado corretamente pelo login.
     */
    @Test
    public void testRecuperar() throws Exception {
        // Carregar dataset com usuários
        InputStream is = getClass().getClassLoader().getResourceAsStream("usuarios-dataset.xml");
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(is);
        DatabaseOperation.CLEAN_INSERT.execute(getConnection(), dataSet);
        
        // Recuperar usuário existente
        Usuario usuario = usuarioDAO.recuperar("joao");
        
        // Verificações
        assertNotNull("Usuário joão deve existir", usuario);
        assertEquals("Login deve ser joao", "joao", usuario.getLogin());
        assertEquals("Email deve ser correto", "joao@email.com", usuario.getEmail());
        assertEquals("Nome deve ser correto", "João Silva", usuario.getNome());
        assertEquals("Pontos devem ser 100", 100, usuario.getPontos());
        
        // Tentar recuperar usuário inexistente
        Usuario usuarioInexistente = usuarioDAO.recuperar("naoexiste");
        assertNull("Usuário inexistente deve retornar null", usuarioInexistente);
    }

    /**
     * Teste do método adicionarPontos.
     * Verifica se os pontos são adicionados corretamente ao usuário.
     */
    @Test
    public void testAdicionarPontos() throws Exception {
        // Carregar dataset com usuários
        InputStream is = getClass().getClassLoader().getResourceAsStream("usuarios-dataset.xml");
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(is);
        DatabaseOperation.CLEAN_INSERT.execute(getConnection(), dataSet);
        
        // Verificar pontos iniciais
        Usuario usuario = usuarioDAO.recuperar("joao");
        assertEquals("Pontos iniciais devem ser 100", 100, usuario.getPontos());
        
        // Adicionar 50 pontos
        usuarioDAO.adicionarPontos("joao", 50);
        
        // Verificar pontos após adição
        usuario = usuarioDAO.recuperar("joao");
        assertEquals("Pontos após adição devem ser 150", 150, usuario.getPontos());
        
        // Adicionar mais pontos
        usuarioDAO.adicionarPontos("joao", 25);
        
        // Verificar pontos após segunda adição
        usuario = usuarioDAO.recuperar("joao");
        assertEquals("Pontos após segunda adição devem ser 175", 175, usuario.getPontos());
    }

    /**
     * Teste do método ranking.
     * Verifica se os usuários são retornados ordenados por pontos (maior primeiro).
     */
    @Test
    public void testRanking() throws Exception {
        // Carregar dataset com usuários
        InputStream is = getClass().getClassLoader().getResourceAsStream("usuarios-dataset.xml");
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(is);
        DatabaseOperation.CLEAN_INSERT.execute(getConnection(), dataSet);
        
        // Obter ranking
        List<Usuario> ranking = usuarioDAO.ranking();
        
        // Verificações
        assertNotNull("Ranking não deve ser null", ranking);
        assertEquals("Deve ter 3 usuários", 3, ranking.size());
        
        // Verificar ordenação (maior pontuação primeiro)
        assertEquals("Primeiro deve ser maria com 200 pontos", "maria", ranking.get(0).getLogin());
        assertEquals("Pontos de maria devem ser 200", 200, ranking.get(0).getPontos());
        
        assertEquals("Segundo deve ser pedro com 150 pontos", "pedro", ranking.get(1).getLogin());
        assertEquals("Pontos de pedro devem ser 150", 150, ranking.get(1).getPontos());
        
        assertEquals("Terceiro deve ser joao com 100 pontos", "joao", ranking.get(2).getLogin());
        assertEquals("Pontos de joao devem ser 100", 100, ranking.get(2).getPontos());
    }

    /**
     * Teste adicional para verificar ranking vazio.
     */
    @Test
    public void testRankingVazio() {
        // Obter ranking de banco vazio
        List<Usuario> ranking = usuarioDAO.ranking();
        
        // Verificações
        assertNotNull("Ranking não deve ser null mesmo vazio", ranking);
        assertEquals("Ranking deve estar vazio", 0, ranking.size());
    }

    /**
     * Teste adicional para verificar inserção com pontos zero.
     */
    @Test
    public void testInserirComPontosZero() {
        Usuario usuario = new Usuario("newuser", "new@email.com", "New User", "pass", 0);
        
        usuarioDAO.inserir(usuario);
        
        Usuario recuperado = usuarioDAO.recuperar("newuser");
        assertNotNull("Usuário deve ser inserido", recuperado);
        assertEquals("Pontos devem ser zero", 0, recuperado.getPontos());
    }

    /**
     * Teste adicional para verificar adição de pontos negativos.
     */
    @Test
    public void testAdicionarPontosNegativos() throws Exception {
        // Carregar dataset com usuários
        InputStream is = getClass().getClassLoader().getResourceAsStream("usuarios-dataset.xml");
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(is);
        DatabaseOperation.CLEAN_INSERT.execute(getConnection(), dataSet);
        
        Usuario usuario = usuarioDAO.recuperar("joao");
        int pontosIniciais = usuario.getPontos();
        
        // Adicionar pontos negativos (reduzir pontos)
        usuarioDAO.adicionarPontos("joao", -30);
        
        usuario = usuarioDAO.recuperar("joao");
        assertEquals("Pontos devem ser reduzidos", pontosIniciais - 30, usuario.getPontos());
    }
}
