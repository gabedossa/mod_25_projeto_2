package br.com.ebac.service;

import br.com.ebac.domain.Cliente;
import br.com.ebac.generics.IGenericService;

public interface IClienteService extends IGenericService<Cliente> {

    Cliente buscaClienteCPF(String cpf);
}
