package br.com.ebac.service;

import java.util.Collection;

import br.com.ebac.domain.Cliente;

public interface IClienteService {

    Boolean salvar(Cliente cliente);

    Cliente buscarPorId(Long id);

    Cliente buscaClienteCPF(String cpf);

    Collection<Cliente> buscarTodos();

    Boolean alterar(Cliente cliente);

    Boolean excluir(Long id);
}
