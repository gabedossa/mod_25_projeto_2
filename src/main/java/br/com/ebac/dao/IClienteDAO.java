package br.com.ebac.dao;

import br.com.ebac.domain.Cliente;
import br.com.ebac.generics.IGenericDAO;

public interface IClienteDAO extends IGenericDAO<Cliente> {

    Cliente buscarPorCpf(String cpf);
}
