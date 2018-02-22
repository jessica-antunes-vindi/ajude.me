package summer.ajudeme;

/**
 * Created by etoal on 22/02/2018.
 */

public class Usuario {
    private String nome;
    private String telefone;
    private String email;
    private String senha;

    public Usuario(String nome, String telefone, String email, String senha) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
    }

    public String getNome() { return nome; }

    public String getTelefone() { return telefone; }

    public String getEmail() { return email; }

    public String getSenha() { return senha; }

    public void setNome(String nome) { this.nome = nome; }

    public void setTelefone(String telefone) { this.telefone = telefone; }

    public void setEmail(String email) { this.email = email; }

    public void setSenha(String senha) { this.senha = senha; }
}
