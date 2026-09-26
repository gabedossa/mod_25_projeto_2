package br.com.ebac.service;

import java.util.Collection;

import br.com.ebac.dao.IClienteDAO;
import br.com.ebac.domain.Cliente;

public class ClienteService implements IClienteService {

    private final IClienteDAO clienteDAO;

    public ClienteService(IClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    @Override
    public Boolean salvar(Cliente cliente) {
        validar(cliente);
        if (clienteDAO.buscarPorCpf(cliente.getCpf()) != null) {
            return false;
        }
        return clienteDAO.salvar(cliente);
    }

    @Override
    public Cliente buscarPorId(Long id) {
        return clienteDAO.buscarPorId(id);
    }

    @Override
    public Cliente buscaClienteCPF(String cpf) {
        return clienteDAO.buscarPorCpf(cpf);
    }

    @Override
    public Collection<Cliente> buscarTodos() {
        return clienteDAO.buscarTodos();
    }

    @Override
    public Boolean alterar(Cliente cliente) {
        validar(cliente);
        Cliente donoDoCpf = clienteDAO.buscarPorCpf(cliente.getCpf());
        if (donoDoCpf != null && !donoDoCpf.getId().equals(cliente.getId())) {
            return false;
        }
        return clienteDAO.alterar(cliente);
    }

    @Override
    public Boolean excluir(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id é obrigatório");
        }
        return clienteDAO.excluir(id);
    }

    private void validar(Cliente cliente) {
        if (cliente == null || cliente.getId() == null) {
            throw new IllegalArgumentException("Cliente e id são obrigatórios");
        }
    }
}
