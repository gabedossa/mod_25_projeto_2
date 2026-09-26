package br.com.ebac.crud;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.ebac.dao.ClienteDAO;
import br.com.ebac.domain.Cliente;
import br.com.ebac.service.ClienteService;
import br.com.ebac.service.IClienteService;

/**
 * Testa o CRUD de cliente de ponta a ponta: service real usando o DAO real.
 */
public class ClienteCrudTest {

    private IClienteService clienteService;
    private Cliente maria;
    private Cliente joao;

    @BeforeEach
    public void init() {
        clienteService = new ClienteService(new ClienteDAO());
        maria = new Cliente(1L, "Maria Silva", "12345678900", LocalDate.of(1995, 5, 20),
                "maria@email.com", "11999998888");
        joao = new Cliente(2L, "João Souza", "98765432100", LocalDate.of(1988, 12, 1),
                "joao@email.com", "21988887777");
    }

    @AfterEach
    public void end() {
        for (Cliente c : clienteService.buscarTodos()) {
            clienteService.excluir(c.getId());
        }
        clienteService = null;
        maria = null;
        joao = null;
    }

    @Test
    public void deveExecutarCrudCompleto() {
        // Create
        assertTrue(clienteService.salvar(maria));

        // Read
        Cliente encontrado = clienteService.buscarPorId(1L);
        assertNotNull(encontrado);
        assertEquals("Maria Silva", encontrado.getNome());
        assertEquals(encontrado, clienteService.buscaClienteCPF("12345678900"));

        // Update
        Cliente alterado = new Cliente(1L, "Maria Souza", "12345678900", LocalDate.of(1995, 5, 20),
                "maria.souza@email.com", "11911112222");
        assertTrue(clienteService.alterar(alterado));
        assertEquals("Maria Souza", clienteService.buscarPorId(1L).getNome());
        assertEquals("maria.souza@email.com", clienteService.buscarPorId(1L).getEmail());

        // Delete
        assertTrue(clienteService.excluir(1L));
        assertNull(clienteService.buscarPorId(1L));
        assertNull(clienteService.buscaClienteCPF("12345678900"));
        assertTrue(clienteService.buscarTodos().isEmpty());
    }

    @Test
    public void deveListarTodosOsClientesCadastrados() {
        clienteService.salvar(maria);
        clienteService.salvar(joao);

        assertEquals(2, clienteService.buscarTodos().size());
    }

    @Test
    public void naoDeveCadastrarDoisClientesComMesmoCpf() {
        clienteService.salvar(maria);
        Cliente copia = new Cliente(3L, "Outra Maria", "12345678900", null, null, null);

        assertFalse(clienteService.salvar(copia));
        assertNull(clienteService.buscarPorId(3L));
    }

    @Test
    public void naoDeveCadastrarDoisClientesComMesmoId() {
        clienteService.salvar(maria);
        Cliente mesmoId = new Cliente(1L, "João Souza", "98765432100", null, null, null);

        assertFalse(clienteService.salvar(mesmoId));
        assertEquals("Maria Silva", clienteService.buscarPorId(1L).getNome());
    }

    @Test
    public void naoDeveAlterarParaCpfDeOutroCliente() {
        clienteService.salvar(maria);
        clienteService.salvar(joao);
        Cliente joaoComCpfDaMaria = new Cliente(2L, "João Souza", "12345678900", null, null, null);

        assertFalse(clienteService.alterar(joaoComCpfDaMaria));
        assertEquals("98765432100", clienteService.buscarPorId(2L).getCpf());
    }

    @Test
    public void naoDeveAlterarClienteInexistente() {
        assertFalse(clienteService.alterar(maria));
        assertTrue(clienteService.buscarTodos().isEmpty());
    }

    @Test
    public void naoDeveExcluirClienteInexistente() {
        clienteService.salvar(maria);

        assertFalse(clienteService.excluir(99L));
        assertEquals(1, clienteService.buscarTodos().size());
    }

    @Test
    public void deveLiberarCpfAposExcluirCliente() {
        clienteService.salvar(maria);
        clienteService.excluir(1L);
        Cliente novo = new Cliente(3L, "Nova Maria", "12345678900", null, null, null);

        assertTrue(clienteService.salvar(novo));
        assertEquals(novo, clienteService.buscaClienteCPF("12345678900"));
    }
}
