package br.com.ebac.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.ebac.domain.Cliente;

/**
 * Mock do DAO de cliente: não guarda nada de verdade, só devolve respostas
 * pré-configuradas e registra as chamadas para o teste conferir.
 */
public class ClienteDAOMock implements IClienteDAO {

    public Boolean retornoSalvar = true;
    public Boolean retornoAlterar = true;
    public Boolean retornoExcluir = true;
    public Cliente clienteRetornado;
    public List<Cliente> clientesRetornados = new ArrayList<>();

    public Cliente clienteSalvo;
    public Cliente clienteAlterado;
    public Long idExcluido;
    public Long idBuscado;
    public String cpfBuscado;
    public int chamadasSalvar;

    @Override
    public Boolean salvar(Cliente cliente) {
        chamadasSalvar++;
        clienteSalvo = cliente;
        return retornoSalvar;
    }

    @Override
    public Cliente buscarPorId(Long id) {
        idBuscado = id;
        return clienteRetornado;
    }

    @Override
    public Cliente buscarPorCpf(String cpf) {
        cpfBuscado = cpf;
        return clienteRetornado;
    }

    @Override
    public Collection<Cliente> buscarTodos() {
        return clientesRetornados;
    }

    @Override
    public Boolean alterar(Cliente cliente) {
        clienteAlterado = cliente;
        return retornoAlterar;
    }

    @Override
    public Boolean excluir(Long id) {
        idExcluido = id;
        return retornoExcluir;
    }
}
