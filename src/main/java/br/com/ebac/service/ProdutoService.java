package br.com.ebac.service;

import br.com.ebac.dao.IProdutoDAO;
import br.com.ebac.domain.Produto;
import br.com.ebac.generics.GenericService;

public class ProdutoService extends GenericService<Produto> implements IProdutoService {

    public ProdutoService(IProdutoDAO produtoDAO) {
        super(produtoDAO);
    }
}
