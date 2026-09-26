package br.com.ebac.service;

import br.com.ebac.dao.IClienteDAO;
import br.com.ebac.domain.Cliente;
import br.com.ebac.generics.GenericService;

public class ClienteService extends GenericService<Cliente> implements IClienteService {

    private final IClienteDAO clienteDAO;

    public ClienteService(IClienteDAO clienteDAO) {
        super(clienteDAO);
        this.clienteDAO = clienteDAO;
    }

    @Override
    public Boolean salvar(Cliente cliente) {
        validar(cliente);
        if (clienteDAO.buscarPorCpf(cliente.getCpf()) != null) {
            return false;
        }
        return super.salvar(cliente);
    }

    @Override
    public Cliente buscaClienteCPF(String cpf) {
        return clienteDAO.buscarPorCpf(cpf);
    }

    @Override
    public Boolean alterar(Cliente cliente) {
        validar(cliente);
        Cliente donoDoCpf = clienteDAO.buscarPorCpf(cliente.getCpf());
        if (donoDoCpf != null && !donoDoCpf.getId().equals(cliente.getId())) {
            return false;
        }
        return super.alterar(cliente);
    }
}
