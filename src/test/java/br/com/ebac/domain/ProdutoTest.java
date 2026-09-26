package br.com.ebac.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProdutoTest {

    private Produto produto;

    @BeforeEach
    public void init() {
        produto = new Produto(1L, "Notebook", "Notebook 16GB RAM", new BigDecimal("3500.00"));
    }

    @AfterEach
    public void end() {
        produto = null;
    }

    @Test
    public void deveCriarProdutoComTodosOsCampos() {
        assertEquals(1L, produto.getId());
        assertEquals("Notebook", produto.getNome());
        assertEquals("Notebook 16GB RAM", produto.getDescricao());
        assertEquals(new BigDecimal("3500.00"), produto.getValor());
    }

    @Test
    public void deveCriarProdutoVazio() {
        Produto vazio = new Produto();

        assertNull(vazio.getId());
        assertNull(vazio.getNome());
        assertNull(vazio.getDescricao());
        assertNull(vazio.getValor());
    }

    @Test
    public void devePreencherCamposPelosSetters() {
        Produto mouse = new Produto();
        mouse.setId(5L);
        mouse.setNome("Mouse");
        mouse.setDescricao("Mouse sem fio");
        mouse.setValor(new BigDecimal("89.90"));

        assertEquals(5L, mouse.getId());
        assertEquals("Mouse", mouse.getNome());
        assertEquals("Mouse sem fio", mouse.getDescricao());
        assertEquals(new BigDecimal("89.90"), mouse.getValor());
    }

    @Test
    public void deveAlterarValorDoProduto() {
        produto.setValor(new BigDecimal("3299.90"));

        assertEquals(new BigDecimal("3299.90"), produto.getValor());
    }

    @Test
    public void valorDeveSerCalculadoSemPerdaDePrecisao() {
        Produto a = new Produto(1L, "Bala", null, new BigDecimal("0.10"));
        Produto b = new Produto(2L, "Chiclete", null, new BigDecimal("0.20"));

        BigDecimal total = a.getValor().add(b.getValor());

        assertEquals(new BigDecimal("0.30"), total);
    }

    @Test
    public void produtosComMesmoIdDevemSerIguais() {
        Produto outro = new Produto(1L, "Mouse", null, null);

        assertEquals(produto, outro);
        assertEquals(produto.hashCode(), outro.hashCode());
    }

    @Test
    public void produtosComIdsDiferentesNaoDevemSerIguais() {
        Produto outro = new Produto(2L, "Notebook", null, null);

        assertNotEquals(produto, outro);
    }

    @Test
    public void produtoNaoDeveSerIgualANullOuOutroTipo() {
        Cliente cliente = new Cliente(1L, "Notebook", "12345678900", null, null, null);

        assertNotEquals(null, produto);
        assertNotEquals(produto, cliente);
    }

    @Test
    public void produtoDeveSerIgualASiMesmo() {
        assertEquals(produto, produto);
    }

    @Test
    public void toStringDeveConterOsDadosDoProduto() {
        String texto = produto.toString();

        assertTrue(texto.contains("Notebook"));
        assertTrue(texto.contains("Notebook 16GB RAM"));
        assertTrue(texto.contains("3500.00"));
    }
}
