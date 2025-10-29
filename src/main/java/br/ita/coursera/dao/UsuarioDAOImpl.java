package br.ita.coursera.dao;

import br.ita.coursera.model.Usuario;
import br.ita.coursera.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementação da interface UsuarioDAO usando JDBC.
 */
public class UsuarioDAOImpl implements UsuarioDAO {
    
    private static final String INSERT_SQL = 
        "INSERT INTO usuario(login, email, nome, senha, pontos) VALUES (?, ?, ?, ?, ?)";
    
    private static final String SELECT_BY_LOGIN_SQL = 
        "SELECT * FROM usuario WHERE login = ?";
    
    private static final String UPDATE_POINTS_SQL = 
        "UPDATE usuario SET pontos = pontos + ? WHERE login = ?";
    
    private static final String SELECT_RANKING_SQL = 
        "SELECT * FROM usuario ORDER BY pontos DESC";

    /**
     * Insere um novo usuário no banco de dados.
     * @param u O usuário a ser inserido
     */
    @Override
    public void inserir(Usuario u) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(INSERT_SQL);
            
            stmt.setString(1, u.getLogin());
            stmt.setString(2, u.getEmail());
            stmt.setString(3, u.getNome());
            stmt.setString(4, u.getSenha());
            stmt.setInt(5, u.getPontos());
            
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir usuário: " + e.getMessage(), e);
        } finally {
            closeResources(conn, stmt, null);
        }
    }

    /**
     * Recupera o usuário pelo seu login.
     * @param login O login do usuário
     * @return O usuário encontrado ou null se não existir
     */
    @Override
    public Usuario recuperar(String login) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Usuario usuario = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(SELECT_BY_LOGIN_SQL);
            stmt.setString(1, login);
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                usuario = extractUsuarioFromResultSet(rs);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao recuperar usuário: " + e.getMessage(), e);
        } finally {
            closeResources(conn, stmt, rs);
        }
        
        return usuario;
    }

    /**
     * Adiciona os pontos para o usuário no banco.
     * @param login O login do usuário
     * @param pontos A quantidade de pontos a adicionar
     */
    @Override
    public void adicionarPontos(String login, int pontos) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(UPDATE_POINTS_SQL);
            
            stmt.setInt(1, pontos);
            stmt.setString(2, login);
            
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar pontos: " + e.getMessage(), e);
        } finally {
            closeResources(conn, stmt, null);
        }
    }

    /**
     * Retorna a lista de usuários ordenada por pontos (maior primeiro).
     * @return Lista de usuários ordenada por pontos
     */
    @Override
    public List<Usuario> ranking() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Usuario> usuarios = new ArrayList<>();
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(SELECT_RANKING_SQL);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                usuarios.add(extractUsuarioFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao obter ranking: " + e.getMessage(), e);
        } finally {
            closeResources(conn, stmt, rs);
        }
        
        return usuarios;
    }
    
    /**
     * Extrai um objeto Usuario de um ResultSet.
     * @param rs O ResultSet
     * @return O objeto Usuario
     * @throws SQLException se houver erro ao acessar os dados
     */
    private Usuario extractUsuarioFromResultSet(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setLogin(rs.getString("login"));
        usuario.setEmail(rs.getString("email"));
        usuario.setNome(rs.getString("nome"));
        usuario.setSenha(rs.getString("senha"));
        usuario.setPontos(rs.getInt("pontos"));
        return usuario;
    }
    
    /**
     * Fecha os recursos do banco de dados.
     * @param conn A conexão
     * @param stmt O statement
     * @param rs O result set
     */
    private void closeResources(Connection conn, PreparedStatement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        DatabaseConnection.closeConnection(conn);
    }
}
