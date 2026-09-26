package br.com.ebac.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.ebac.dao.ClienteDAOMock;
import br.com.ebac.domain.Cliente;

public class ClienteServiceTest {

    private ClienteDAOMock clienteDAO;
    private IClienteService clienteService;
    private Cliente cliente;

    @BeforeEach
    public void init() {
        clienteDAO = new ClienteDAOMock();
        clienteService = new ClienteService(clienteDAO);
        cliente = new Cliente(1L, "Maria Silva", "12345678900", LocalDate.of(1995, 5, 20),
                "maria@email.com", "11999998888");
    }

    @AfterEach
    public void end() {
        clienteService = null;
        clienteDAO = null;
        cliente = null;
    }

    @Test
    public void deveSalvarCliente() {
        Boolean salvo = clienteService.salvar(cliente);

        assertTrue(salvo);
        assertSame(cliente, clienteDAO.clienteSalvo);
        assertEquals("12345678900", clienteDAO.cpfBuscado);
    }

    @Test
    public void deveRetornarFalseQuandoDaoNaoSalvar() {
        clienteDAO.retornoSalvar = false;

        assertFalse(clienteService.salvar(cliente));
    }

    @Test
    public void naoDeveSalvarClienteComCpfJaCadastrado() {
        clienteDAO.clienteRetornado = new Cliente(2L, "João Souza", "12345678900", null, null, null);

        Boolean salvo = clienteService.salvar(cliente);

        assertFalse(salvo);
        assertEquals(0, clienteDAO.chamadasSalvar);
    }

    @Test
    public void naoDeveSalvarClienteNulo() {
        assertThrows(IllegalArgumentException.class, () -> clienteService.salvar(null));
        assertEquals(0, clienteDAO.chamadasSalvar);
    }

    @Test
    public void naoDeveSalvarClienteSemId() {
        cliente.setId(null);

        assertThrows(IllegalArgumentException.class, () -> clienteService.salvar(cliente));
        assertEquals(0, clienteDAO.chamadasSalvar);
    }

    @Test
    public void deveBuscarClientePorId() {
        clienteDAO.clienteRetornado = cliente;

        assertSame(cliente, clienteService.buscarPorId(1L));
        assertEquals(1L, clienteDAO.idBuscado);
    }

    @Test
    public void deveBuscarClientePorCpf() {
        clienteDAO.clienteRetornado = cliente;

        assertSame(cliente, clienteService.buscaClienteCPF("12345678900"));
        assertEquals("12345678900", clienteDAO.cpfBuscado);
    }

    @Test
    public void deveBuscarTodosOsClientes() {
        clienteDAO.clientesRetornados.add(cliente);

        assertEquals(1, clienteService.buscarTodos().size());
    }

    @Test
    public void deveAlterarCliente() {
        Boolean alterado = clienteService.alterar(cliente);

        assertTrue(alterado);
        assertSame(cliente, clienteDAO.clienteAlterado);
    }

    @Test
    public void deveAlterarClienteMantendoOProprioCpf() {
        clienteDAO.clienteRetornado = new Cliente(1L, "Maria", "12345678900", null, null, null);

        assertTrue(clienteService.alterar(cliente));
        assertSame(cliente, clienteDAO.clienteAlterado);
    }

    @Test
    public void naoDeveAlterarParaCpfDeOutroCliente() {
        clienteDAO.clienteRetornado = new Cliente(2L, "João Souza", "12345678900", null, null, null);

        Boolean alterado = clienteService.alterar(cliente);

        assertFalse(alterado);
        assertNull(clienteDAO.clienteAlterado);
    }

    @Test
    public void deveRetornarFalseAoAlterarClienteInexistente() {
        clienteDAO.retornoAlterar = false;

        assertFalse(clienteService.alterar(cliente));
    }

    @Test
    public void naoDeveAlterarClienteNulo() {
        assertThrows(IllegalArgumentException.class, () -> clienteService.alterar(null));
        assertNull(clienteDAO.clienteAlterado);
    }

    @Test
    public void naoDeveAlterarClienteSemId() {
        cliente.setId(null);

        assertThrows(IllegalArgumentException.class, () -> clienteService.alterar(cliente));
        assertNull(clienteDAO.clienteAlterado);
    }

    @Test
    public void deveExcluirCliente() {
        Boolean excluido = clienteService.excluir(1L);

        assertTrue(excluido);
        assertEquals(1L, clienteDAO.idExcluido);
    }

    @Test
    public void deveRetornarFalseAoExcluirClienteInexistente() {
        clienteDAO.retornoExcluir = false;

        assertFalse(clienteService.excluir(99L));
    }

    @Test
    public void naoDeveExcluirComIdNulo() {
        assertThrows(IllegalArgumentException.class, () -> clienteService.excluir(null));
        assertNull(clienteDAO.idExcluido);
    }
}
