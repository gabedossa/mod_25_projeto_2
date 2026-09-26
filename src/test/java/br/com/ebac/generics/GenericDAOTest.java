package br.com.ebac.generics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Testa o GenericDAO com uma entidade criada só para o teste, provando que
 * ele funciona com qualquer classe que implemente Persistente.
 */
public class GenericDAOTest {

    static class Item implements Persistente {
        private final Long id;
        private final String nome;

        Item(Long id, String nome) {
            this.id = id;
            this.nome = nome;
        }

        @Override
        public Long getId() {
            return id;
        }

        String getNome() {
            return nome;
        }
    }

    static class ItemDAO extends GenericDAO<Item> {
    }

    private IGenericDAO<Item> dao;
    private Item item;

    @BeforeEach
    public void init() {
        dao = new ItemDAO();
        item = new Item(1L, "Caneta");
    }

    @AfterEach
    public void end() {
        dao = null;
        item = null;
    }

    @Test
    public void deveSalvarEBuscar() {
        assertTrue(dao.salvar(item));
        assertSame(item, dao.buscarPorId(1L));
    }

    @Test
    public void naoDeveSalvarIdRepetido() {
        dao.salvar(item);

        assertFalse(dao.salvar(new Item(1L, "Lápis")));
        assertEquals("Caneta", dao.buscarPorId(1L).getNome());
    }

    @Test
    public void deveListarTodos() {
        dao.salvar(item);
        dao.salvar(new Item(2L, "Lápis"));

        assertEquals(2, dao.buscarTodos().size());
    }

    @Test
    public void deveAlterar() {
        dao.salvar(item);

        assertTrue(dao.alterar(new Item(1L, "Caneta azul")));
        assertEquals("Caneta azul", dao.buscarPorId(1L).getNome());
    }

    @Test
    public void naoDeveAlterarInexistente() {
        assertFalse(dao.alterar(item));
        assertTrue(dao.buscarTodos().isEmpty());
    }

    @Test
    public void deveExcluir() {
        dao.salvar(item);

        assertTrue(dao.excluir(1L));
        assertNull(dao.buscarPorId(1L));
    }

    @Test
    public void naoDeveExcluirInexistente() {
        assertFalse(dao.excluir(99L));
    }
}
