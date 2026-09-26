package br.com.ebac.crud;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.ebac.dao.ProdutoDAO;
import br.com.ebac.domain.Produto;
import br.com.ebac.service.IProdutoService;
import br.com.ebac.service.ProdutoService;

/**
 * Testa o CRUD de produto de ponta a ponta: service real usando o DAO real.
 */
public class ProdutoCrudTest {

    private IProdutoService produtoService;
    private Produto notebook;
    private Produto mouse;

    @BeforeEach
    public void init() {
        produtoService = new ProdutoService(new ProdutoDAO());
        notebook = new Produto(1L, "Notebook", "Notebook 16GB RAM", new BigDecimal("3500.00"));
        mouse = new Produto(2L, "Mouse", "Mouse sem fio", new BigDecimal("89.90"));
    }

    @AfterEach
    public void end() {
        for (Produto p : produtoService.buscarTodos()) {
            produtoService.excluir(p.getId());
        }
        produtoService = null;
        notebook = null;
        mouse = null;
    }

    @Test
    public void deveExecutarCrudCompleto() {
        // Create
        assertTrue(produtoService.salvar(notebook));

        // Read
        Produto encontrado = produtoService.buscarPorId(1L);
        assertNotNull(encontrado);
        assertEquals("Notebook", encontrado.getNome());
        assertEquals(new BigDecimal("3500.00"), encontrado.getValor());

        // Update
        Produto alterado = new Produto(1L, "Notebook", "Notebook 32GB RAM", new BigDecimal("4200.00"));
        assertTrue(produtoService.alterar(alterado));
        assertEquals("Notebook 32GB RAM", produtoService.buscarPorId(1L).getDescricao());
        assertEquals(new BigDecimal("4200.00"), produtoService.buscarPorId(1L).getValor());

        // Delete
        assertTrue(produtoService.excluir(1L));
        assertNull(produtoService.buscarPorId(1L));
        assertTrue(produtoService.buscarTodos().isEmpty());
    }

    @Test
    public void deveListarTodosOsProdutosCadastrados() {
        produtoService.salvar(notebook);
        produtoService.salvar(mouse);

        assertEquals(2, produtoService.buscarTodos().size());
    }

    @Test
    public void naoDeveCadastrarDoisProdutosComMesmoId() {
        produtoService.salvar(notebook);
        Produto mesmoId = new Produto(1L, "Teclado", null, new BigDecimal("150.00"));

        assertFalse(produtoService.salvar(mesmoId));
        assertEquals("Notebook", produtoService.buscarPorId(1L).getNome());
    }

    @Test
    public void naoDeveAlterarProdutoInexistente() {
        assertFalse(produtoService.alterar(notebook));
        assertTrue(produtoService.buscarTodos().isEmpty());
    }

    @Test
    public void naoDeveExcluirProdutoInexistente() {
        produtoService.salvar(notebook);

        assertFalse(produtoService.excluir(99L));
        assertEquals(1, produtoService.buscarTodos().size());
    }

    @Test
    public void deveExcluirSomenteOProdutoInformado() {
        produtoService.salvar(notebook);
        produtoService.salvar(mouse);

        produtoService.excluir(1L);

        assertNull(produtoService.buscarPorId(1L));
        assertEquals("Mouse", produtoService.buscarPorId(2L).getNome());
    }
}
