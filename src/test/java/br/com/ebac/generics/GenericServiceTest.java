package br.com.ebac.generics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Testa o GenericService com uma entidade e um DAO criados só para o teste,
 * provando que ele funciona com qualquer classe que implemente Persistente.
 */
public class GenericServiceTest {

    static class Item implements Persistente {
        private Long id;
        private final String nome;

        Item(Long id, String nome) {
            this.id = id;
            this.nome = nome;
        }

        @Override
        public Long getId() {
            return id;
        }

        void setId(Long id) {
            this.id = id;
        }

        String getNome() {
            return nome;
        }
    }

    static class ItemDAO extends GenericDAO<Item> {
    }

    static class ItemService extends GenericService<Item> {
        ItemService(IGenericDAO<Item> dao) {
            super(dao);
        }
    }

    private IGenericService<Item> service;
    private Item item;

    @BeforeEach
    public void init() {
        service = new ItemService(new ItemDAO());
        item = new Item(1L, "Caneta");
    }

    @AfterEach
    public void end() {
        service = null;
        item = null;
    }

    @Test
    public void deveSalvarEBuscar() {
        assertTrue(service.salvar(item));
        assertSame(item, service.buscarPorId(1L));
    }

    @Test
    public void naoDeveSalvarIdRepetido() {
        service.salvar(item);

        assertFalse(service.salvar(new Item(1L, "Lápis")));
    }

    @Test
    public void deveListarTodos() {
        service.salvar(item);
        service.salvar(new Item(2L, "Lápis"));

        assertEquals(2, service.buscarTodos().size());
    }

    @Test
    public void deveAlterar() {
        service.salvar(item);

        assertTrue(service.alterar(new Item(1L, "Caneta azul")));
        assertEquals("Caneta azul", service.buscarPorId(1L).getNome());
    }

    @Test
    public void deveExcluir() {
        service.salvar(item);

        assertTrue(service.excluir(1L));
        assertNull(service.buscarPorId(1L));
    }

    @Test
    public void naoDeveAceitarEntidadeNula() {
        assertThrows(IllegalArgumentException.class, () -> service.salvar(null));
        assertThrows(IllegalArgumentException.class, () -> service.alterar(null));
    }

    @Test
    public void naoDeveAceitarEntidadeSemId() {
        item.setId(null);

        assertThrows(IllegalArgumentException.class, () -> service.salvar(item));
        assertThrows(IllegalArgumentException.class, () -> service.alterar(item));
    }

    @Test
    public void naoDeveExcluirComIdNulo() {
        assertThrows(IllegalArgumentException.class, () -> service.excluir(null));
    }
}
