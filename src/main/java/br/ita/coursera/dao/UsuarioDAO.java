package br.ita.coursera.dao;

import br.ita.coursera.model.Usuario;
import java.util.List;

/**
 * Interface para operações de acesso a dados de usuário.
 */
public interface UsuarioDAO {
    
    /**
     * Insere um novo usuário no banco de dados.
     * @param u O usuário a ser inserido
     */
    public void inserir(Usuario u);
    
    /**
     * Recupera o usuário pelo seu login.
     * @param login O login do usuário
     * @return O usuário encontrado ou null se não existir
     */
    public Usuario recuperar(String login);
    
    /**
     * Adiciona os pontos para o usuário no banco.
     * @param login O login do usuário
     * @param pontos A quantidade de pontos a adicionar
     */
    public void adicionarPontos(String login, int pontos);
    
    /**
     * Retorna a lista de usuários ordenada por pontos (maior primeiro).
     * @return Lista de usuários ordenada por pontos
     */
    public List<Usuario> ranking();
}
