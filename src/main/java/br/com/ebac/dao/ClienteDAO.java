package br.com.ebac.dao;

import br.com.ebac.domain.Cliente;
import br.com.ebac.generics.GenericDAO;

public class ClienteDAO extends GenericDAO<Cliente> implements IClienteDAO {

    @Override
    public Cliente buscarPorCpf(String cpf) {
        if (cpf == null) {
            return null;
        }
        for (Cliente cliente : registros.values()) {
            if (cpf.equals(cliente.getCpf())) {
                return cliente;
            }
        }
        return null;
    }
}
