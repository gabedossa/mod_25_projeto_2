package br.com.ebac.dao;

import java.util.Collection;

import br.com.ebac.domain.Cliente;

public interface IClienteDAO {

    Boolean salvar(Cliente cliente);

    Cliente buscarPorId(Long id);

    Cliente buscarPorCpf(String cpf);

    Collection<Cliente> buscarTodos();

    Boolean alterar(Cliente cliente);

    Boolean excluir(Long id);
}
