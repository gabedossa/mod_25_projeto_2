package br.com.ebac.service;

import java.util.Collection;

import br.com.ebac.dao.IProdutoDAO;
import br.com.ebac.domain.Produto;

public class ProdutoService implements IProdutoService {

    private final IProdutoDAO produtoDAO;

    public ProdutoService(IProdutoDAO produtoDAO) {
        this.produtoDAO = produtoDAO;
    }

    @Override
    public Boolean salvar(Produto produto) {
        validar(produto);
        return produtoDAO.salvar(produto);
    }

    @Override
    public Produto buscarPorId(Long id) {
        return produtoDAO.buscarPorId(id);
    }

    @Override
    public Collection<Produto> buscarTodos() {
        return produtoDAO.buscarTodos();
    }

    @Override
    public Boolean alterar(Produto produto) {
        validar(produto);
        return produtoDAO.alterar(produto);
    }

    @Override
    public Boolean excluir(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id é obrigatório");
        }
        return produtoDAO.excluir(id);
    }

    private void validar(Produto produto) {
        if (produto == null || produto.getId() == null) {
            throw new IllegalArgumentException("Produto e id são obrigatórios");
        }
    }
}
