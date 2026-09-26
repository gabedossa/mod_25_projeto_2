package br.com.ebac.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import br.com.ebac.domain.Cliente;

public class ClienteDAO implements IClienteDAO {

    private final Map<Long, Cliente> clientes = new LinkedHashMap<>();

    @Override
    public Boolean salvar(Cliente cliente) {
        if (clientes.containsKey(cliente.getId())) {
            return false;
        }
        clientes.put(cliente.getId(), cliente);
        return true;
    }

    @Override
    public Cliente buscarPorId(Long id) {
        return clientes.get(id);
    }

    @Override
    public Cliente buscarPorCpf(String cpf) {
        if (cpf == null) {
            return null;
        }
        for (Cliente cliente : clientes.values()) {
            if (cpf.equals(cliente.getCpf())) {
                return cliente;
            }
        }
        return null;
    }

    @Override
    public Collection<Cliente> buscarTodos() {
        return new ArrayList<>(clientes.values());
    }

    @Override
    public Boolean alterar(Cliente cliente) {
        if (!clientes.containsKey(cliente.getId())) {
            return false;
        }
        clientes.put(cliente.getId(), cliente);
        return true;
    }

    @Override
    public Boolean excluir(Long id) {
        return clientes.remove(id) != null;
    }
}
