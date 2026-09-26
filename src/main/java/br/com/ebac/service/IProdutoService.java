package br.com.ebac.service;

import java.util.Collection;

import br.com.ebac.domain.Produto;

public interface IProdutoService {

    Boolean salvar(Produto produto);

    Produto buscarPorId(Long id);

    Collection<Produto> buscarTodos();

    Boolean alterar(Produto produto);

    Boolean excluir(Long id);
}
