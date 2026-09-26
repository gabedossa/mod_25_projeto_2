package br.com.ebac.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.ebac.domain.Produto;

/**
 * Mock do DAO de produto: não guarda nada de verdade, só devolve respostas
 * pré-configuradas e registra as chamadas para o teste conferir.
 */
public class ProdutoDAOMock implements IProdutoDAO {

    public Boolean retornoSalvar = true;
    public Boolean retornoAlterar = true;
    public Boolean retornoExcluir = true;
    public Produto produtoRetornado;
    public List<Produto> produtosRetornados = new ArrayList<>();

    public Produto produtoSalvo;
    public Produto produtoAlterado;
    public Long idExcluido;
    public Long idBuscado;
    public int chamadasSalvar;

    @Override
    public Boolean salvar(Produto produto) {
        chamadasSalvar++;
        produtoSalvo = produto;
        return retornoSalvar;
    }

    @Override
    public Produto buscarPorId(Long id) {
        idBuscado = id;
        return produtoRetornado;
    }

    @Override
    public Collection<Produto> buscarTodos() {
        return produtosRetornados;
    }

    @Override
    public Boolean alterar(Produto produto) {
        produtoAlterado = produto;
        return retornoAlterar;
    }

    @Override
    public Boolean excluir(Long id) {
        idExcluido = id;
        return retornoExcluir;
    }
}
