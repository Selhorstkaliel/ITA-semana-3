package br.ita.coursera.model;

/**
 * Classe que representa um usuário no sistema.
 * Corresponde à tabela usuario no banco de dados.
 */
public class Usuario {
    private String login;
    private String email;
    private String nome;
    private String senha;
    private int pontos;

    /**
     * Construtor padrão.
     */
    public Usuario() {
    }

    /**
     * Construtor completo.
     */
    public Usuario(String login, String email, String nome, String senha, int pontos) {
        this.login = login;
        this.email = email;
        this.nome = nome;
        this.senha = senha;
        this.pontos = pontos;
    }

    // Getters e Setters
    
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", nome='" + nome + '\'' +
                ", pontos=" + pontos +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Usuario usuario = (Usuario) o;

        return login != null ? login.equals(usuario.login) : usuario.login == null;
    }

    @Override
    public int hashCode() {
        return login != null ? login.hashCode() : 0;
    }
}
