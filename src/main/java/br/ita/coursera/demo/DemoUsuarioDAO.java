package br.ita.coursera.demo;

import br.ita.coursera.dao.UsuarioDAO;
import br.ita.coursera.dao.UsuarioDAOImpl;
import br.ita.coursera.model.Usuario;

import java.util.List;

/**
 * Classe de demonstração do uso da UsuarioDAO.
 * Esta classe mostra exemplos de como utilizar cada método da interface.
 */
public class DemoUsuarioDAO {

    public static void main(String[] args) {
        System.out.println("=== Demonstração do UsuarioDAO ===\n");
        
        // Criar instância do DAO
        UsuarioDAO dao = new UsuarioDAOImpl();
        
        try {
            // 1. Inserir usuários
            System.out.println("1. Inserindo usuários...");
            
            Usuario u1 = new Usuario("joao", "joao@email.com", "João Silva", "senha123", 100);
            Usuario u2 = new Usuario("maria", "maria@email.com", "Maria Santos", "senha456", 200);
            Usuario u3 = new Usuario("pedro", "pedro@email.com", "Pedro Costa", "senha789", 150);
            
            dao.inserir(u1);
            dao.inserir(u2);
            dao.inserir(u3);
            
            System.out.println("   ✓ Usuários inseridos com sucesso!\n");
            
            // 2. Recuperar usuário
            System.out.println("2. Recuperando usuário 'joao'...");
            Usuario usuario = dao.recuperar("joao");
            if (usuario != null) {
                System.out.println("   ✓ Usuário encontrado: " + usuario);
            } else {
                System.out.println("   ✗ Usuário não encontrado");
            }
            System.out.println();
            
            // 3. Adicionar pontos
            System.out.println("3. Adicionando 50 pontos para 'joao'...");
            dao.adicionarPontos("joao", 50);
            usuario = dao.recuperar("joao");
            System.out.println("   ✓ Novos pontos de joao: " + usuario.getPontos() + "\n");
            
            // 4. Exibir ranking
            System.out.println("4. Ranking de usuários (por pontos):");
            List<Usuario> ranking = dao.ranking();
            int posicao = 1;
            for (Usuario u : ranking) {
                System.out.println("   " + posicao + "º - " + u.getNome() + 
                                 " (" + u.getLogin() + ") - " + u.getPontos() + " pontos");
                posicao++;
            }
            System.out.println();
            
            System.out.println("=== Demonstração concluída com sucesso! ===");
            
        } catch (Exception e) {
            System.err.println("Erro durante a demonstração: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
