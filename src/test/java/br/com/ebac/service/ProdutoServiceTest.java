package br.com.ebac.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.ebac.dao.ProdutoDAOMock;
import br.com.ebac.domain.Produto;

public class ProdutoServiceTest {

    private ProdutoDAOMock produtoDAO;
    private IProdutoService produtoService;
    private Produto produto;

    @BeforeEach
    public void init() {
        produtoDAO = new ProdutoDAOMock();
        produtoService = new ProdutoService(produtoDAO);
        produto = new Produto(1L, "Notebook", "Notebook 16GB RAM", new BigDecimal("3500.00"));
    }

    @AfterEach
    public void end() {
        produtoService = null;
        produtoDAO = null;
        produto = null;
    }

    @Test
    public void deveSalvarProduto() {
        Boolean salvo = produtoService.salvar(produto);

        assertTrue(salvo);
        assertSame(produto, produtoDAO.produtoSalvo);
    }

    @Test
    public void deveRetornarFalseQuandoDaoNaoSalvar() {
        produtoDAO.retornoSalvar = false;

        assertFalse(produtoService.salvar(produto));
    }

    @Test
    public void naoDeveSalvarProdutoNulo() {
        assertThrows(IllegalArgumentException.class, () -> produtoService.salvar(null));
        assertEquals(0, produtoDAO.chamadasSalvar);
    }

    @Test
    public void naoDeveSalvarProdutoSemId() {
        produto.setId(null);

        assertThrows(IllegalArgumentException.class, () -> produtoService.salvar(produto));
        assertEquals(0, produtoDAO.chamadasSalvar);
    }

    @Test
    public void deveBuscarProdutoPorId() {
        produtoDAO.produtoRetornado = produto;

        assertSame(produto, produtoService.buscarPorId(1L));
        assertEquals(1L, produtoDAO.idBuscado);
    }

    @Test
    public void deveBuscarTodosOsProdutos() {
        produtoDAO.produtosRetornados.add(produto);

        assertEquals(1, produtoService.buscarTodos().size());
    }

    @Test
    public void deveAlterarProduto() {
        Boolean alterado = produtoService.alterar(produto);

        assertTrue(alterado);
        assertSame(produto, produtoDAO.produtoAlterado);
    }

    @Test
    public void deveRetornarFalseAoAlterarProdutoInexistente() {
        produtoDAO.retornoAlterar = false;

        assertFalse(produtoService.alterar(produto));
    }

    @Test
    public void naoDeveAlterarProdutoNulo() {
        assertThrows(IllegalArgumentException.class, () -> produtoService.alterar(null));
        assertNull(produtoDAO.produtoAlterado);
    }

    @Test
    public void naoDeveAlterarProdutoSemId() {
        produto.setId(null);

        assertThrows(IllegalArgumentException.class, () -> produtoService.alterar(produto));
        assertNull(produtoDAO.produtoAlterado);
    }

    @Test
    public void deveExcluirProduto() {
        Boolean excluido = produtoService.excluir(1L);

        assertTrue(excluido);
        assertEquals(1L, produtoDAO.idExcluido);
    }

    @Test
    public void deveRetornarFalseAoExcluirProdutoInexistente() {
        produtoDAO.retornoExcluir = false;

        assertFalse(produtoService.excluir(99L));
    }

    @Test
    public void naoDeveExcluirComIdNulo() {
        assertThrows(IllegalArgumentException.class, () -> produtoService.excluir(null));
        assertNull(produtoDAO.idExcluido);
    }
}
