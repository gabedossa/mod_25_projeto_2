package br.com.ebac.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import br.com.ebac.domain.Produto;

public class ProdutoDAO implements IProdutoDAO {

    private final Map<Long, Produto> produtos = new LinkedHashMap<>();

    @Override
    public Boolean salvar(Produto produto) {
        if (produtos.containsKey(produto.getId())) {
            return false;
        }
        produtos.put(produto.getId(), produto);
        return true;
    }

    @Override
    public Produto buscarPorId(Long id) {
        return produtos.get(id);
    }

    @Override
    public Collection<Produto> buscarTodos() {
        return new ArrayList<>(produtos.values());
    }

    @Override
    public Boolean alterar(Produto produto) {
        if (!produtos.containsKey(produto.getId())) {
            return false;
        }
        produtos.put(produto.getId(), produto);
        return true;
    }

    @Override
    public Boolean excluir(Long id) {
        return produtos.remove(id) != null;
    }
}
