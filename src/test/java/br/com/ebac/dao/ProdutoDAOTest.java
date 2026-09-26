package br.com.ebac.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.ebac.domain.Produto;

public class ProdutoDAOTest {

    private IProdutoDAO produtoDAO;
    private Produto produto;

    @BeforeEach
    public void init() {
        produtoDAO = new ProdutoDAO();
        produto = new Produto(1L, "Notebook", "Notebook 16GB RAM", new BigDecimal("3500.00"));
    }

    @AfterEach
    public void end() {
        for (Produto p : produtoDAO.buscarTodos()) {
            produtoDAO.excluir(p.getId());
        }
        produtoDAO = null;
        produto = null;
    }

    @Test
    public void deveSalvarProduto() {
        assertTrue(produtoDAO.salvar(produto));
        assertEquals(1, produtoDAO.buscarTodos().size());
    }

    @Test
    public void naoDeveSalvarIdRepetido() {
        produtoDAO.salvar(produto);

        assertFalse(produtoDAO.salvar(new Produto(1L, "Mouse", null, new BigDecimal("89.90"))));
        assertEquals("Notebook", produtoDAO.buscarPorId(1L).getNome());
        assertEquals(1, produtoDAO.buscarTodos().size());
    }

    @Test
    public void deveBuscarProdutoPorId() {
        produtoDAO.salvar(produto);

        assertSame(produto, produtoDAO.buscarPorId(1L));
    }

    @Test
    public void deveRetornarNullAoBuscarIdInexistente() {
        produtoDAO.salvar(produto);

        assertNull(produtoDAO.buscarPorId(99L));
    }

    @Test
    public void deveRetornarListaVaziaSemProdutos() {
        assertTrue(produtoDAO.buscarTodos().isEmpty());
    }

    @Test
    public void deveBuscarTodosNaOrdemDeCadastro() {
        Produto mouse = new Produto(2L, "Mouse", "Mouse sem fio", new BigDecimal("89.90"));
        produtoDAO.salvar(produto);
        produtoDAO.salvar(mouse);

        List<Produto> todos = new ArrayList<>(produtoDAO.buscarTodos());

        assertEquals(2, todos.size());
        assertSame(produto, todos.get(0));
        assertSame(mouse, todos.get(1));
    }

    @Test
    public void listaRetornadaNaoDeveAlterarOsDadosDoDao() {
        produtoDAO.salvar(produto);

        produtoDAO.buscarTodos().clear();

        assertEquals(1, produtoDAO.buscarTodos().size());
    }

    @Test
    public void deveAlterarProduto() {
        produtoDAO.salvar(produto);
        Produto alterado = new Produto(1L, "Notebook", "Notebook 32GB RAM", new BigDecimal("4200.00"));

        assertTrue(produtoDAO.alterar(alterado));

        assertEquals("Notebook 32GB RAM", produtoDAO.buscarPorId(1L).getDescricao());
        assertEquals(new BigDecimal("4200.00"), produtoDAO.buscarPorId(1L).getValor());
        assertEquals(1, produtoDAO.buscarTodos().size());
    }

    @Test
    public void naoDeveIncluirProdutoAoAlterarIdInexistente() {
        assertFalse(produtoDAO.alterar(produto));

        assertNull(produtoDAO.buscarPorId(1L));
        assertTrue(produtoDAO.buscarTodos().isEmpty());
    }

    @Test
    public void deveExcluirProduto() {
        produtoDAO.salvar(produto);

        assertTrue(produtoDAO.excluir(1L));

        assertNull(produtoDAO.buscarPorId(1L));
        assertTrue(produtoDAO.buscarTodos().isEmpty());
    }

    @Test
    public void deveExcluirSomenteOProdutoInformado() {
        produtoDAO.salvar(produto);
        produtoDAO.salvar(new Produto(2L, "Mouse", null, new BigDecimal("89.90")));

        assertTrue(produtoDAO.excluir(1L));

        assertNull(produtoDAO.buscarPorId(1L));
        assertEquals("Mouse", produtoDAO.buscarPorId(2L).getNome());
    }

    @Test
    public void deveRetornarFalseAoExcluirIdInexistente() {
        produtoDAO.salvar(produto);

        assertFalse(produtoDAO.excluir(99L));
        assertEquals(1, produtoDAO.buscarTodos().size());
    }

    @Test
    public void devePermitirSalvarNovamenteAposExcluir() {
        produtoDAO.salvar(produto);
        produtoDAO.excluir(1L);

        assertTrue(produtoDAO.salvar(produto));
    }
}
