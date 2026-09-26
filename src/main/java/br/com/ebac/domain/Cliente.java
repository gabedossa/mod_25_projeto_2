package br.com.ebac.domain;

import java.time.LocalDate;
import java.util.Objects;

import br.com.ebac.generics.Persistente;

public class Cliente implements Persistente {

    private Long id;
    private String nome;
    private String cpf;
    private LocalDate dataNasc;
    private String email;
    private String telefone;

    public Cliente() {
    }

    public Cliente(Long id, String nome, String cpf, LocalDate dataNasc, String email, String telefone) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.dataNasc = dataNasc;
        this.email = email;
        this.telefone = telefone;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente)) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(id, cliente.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Cliente{id=" + id + ", nome='" + nome + "', cpf='" + cpf + "', dataNasc=" + dataNasc
                + ", email='" + email + "', telefone='" + telefone + "'}";
    }
}
