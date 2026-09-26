package br.com.ebac.dao;

import java.util.Collection;

import br.com.ebac.domain.Produto;

public interface IProdutoDAO {

    Boolean salvar(Produto produto);

    Produto buscarPorId(Long id);

    Collection<Produto> buscarTodos();

    Boolean alterar(Produto produto);

    Boolean excluir(Long id);
}
