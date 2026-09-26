package br.com.ebac.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.ebac.domain.Cliente;

public class ClienteDAOTest {

    private IClienteDAO clienteDAO;
    private Cliente cliente;

    @BeforeEach
    public void init() {
        clienteDAO = new ClienteDAO();
        cliente = new Cliente(1L, "Maria Silva", "12345678900", null, "maria@email.com", "11999998888");
    }

    @AfterEach
    public void end() {
        for (Cliente c : clienteDAO.buscarTodos()) {
            clienteDAO.excluir(c.getId());
        }
        clienteDAO = null;
        cliente = null;
    }

    @Test
    public void deveSalvarCliente() {
        assertTrue(clienteDAO.salvar(cliente));
        assertEquals(1, clienteDAO.buscarTodos().size());
    }

    @Test
    public void naoDeveSalvarIdRepetido() {
        clienteDAO.salvar(cliente);

        assertFalse(clienteDAO.salvar(new Cliente(1L, "João", "98765432100", null, null, null)));
        assertEquals("Maria Silva", clienteDAO.buscarPorId(1L).getNome());
        assertEquals(1, clienteDAO.buscarTodos().size());
    }

    @Test
    public void deveBuscarClientePorId() {
        clienteDAO.salvar(cliente);

        assertSame(cliente, clienteDAO.buscarPorId(1L));
    }

    @Test
    public void deveRetornarNullAoBuscarIdInexistente() {
        clienteDAO.salvar(cliente);

        assertNull(clienteDAO.buscarPorId(99L));
    }

    @Test
    public void deveBuscarClientePorCpf() {
        clienteDAO.salvar(cliente);
        clienteDAO.salvar(new Cliente(2L, "João", "98765432100", null, null, null));

        assertEquals(2L, clienteDAO.buscarPorCpf("98765432100").getId());
    }

    @Test
    public void deveRetornarNullAoBuscarCpfInexistente() {
        clienteDAO.salvar(cliente);

        assertNull(clienteDAO.buscarPorCpf("00000000000"));
    }

    @Test
    public void deveRetornarNullAoBuscarCpfNulo() {
        clienteDAO.salvar(cliente);

        assertNull(clienteDAO.buscarPorCpf(null));
    }

    @Test
    public void deveRetornarListaVaziaSemClientes() {
        assertTrue(clienteDAO.buscarTodos().isEmpty());
    }

    @Test
    public void deveBuscarTodosNaOrdemDeCadastro() {
        Cliente joao = new Cliente(2L, "João", "98765432100", null, null, null);
        clienteDAO.salvar(cliente);
        clienteDAO.salvar(joao);

        List<Cliente> todos = new ArrayList<>(clienteDAO.buscarTodos());

        assertEquals(2, todos.size());
        assertSame(cliente, todos.get(0));
        assertSame(joao, todos.get(1));
    }

    @Test
    public void listaRetornadaNaoDeveAlterarOsDadosDoDao() {
        clienteDAO.salvar(cliente);

        clienteDAO.buscarTodos().clear();

        assertEquals(1, clienteDAO.buscarTodos().size());
    }

    @Test
    public void deveAlterarCliente() {
        clienteDAO.salvar(cliente);
        Cliente alterado = new Cliente(1L, "Maria Souza", "12345678900", null, "nova@email.com", null);

        assertTrue(clienteDAO.alterar(alterado));

        assertEquals("Maria Souza", clienteDAO.buscarPorId(1L).getNome());
        assertEquals("nova@email.com", clienteDAO.buscarPorId(1L).getEmail());
        assertEquals(1, clienteDAO.buscarTodos().size());
    }

    @Test
    public void naoDeveIncluirClienteAoAlterarIdInexistente() {
        assertFalse(clienteDAO.alterar(cliente));

        assertNull(clienteDAO.buscarPorId(1L));
        assertTrue(clienteDAO.buscarTodos().isEmpty());
    }

    @Test
    public void deveExcluirCliente() {
        clienteDAO.salvar(cliente);

        assertTrue(clienteDAO.excluir(1L));

        assertNull(clienteDAO.buscarPorId(1L));
        assertTrue(clienteDAO.buscarTodos().isEmpty());
    }

    @Test
    public void deveExcluirSomenteOClienteInformado() {
        clienteDAO.salvar(cliente);
        clienteDAO.salvar(new Cliente(2L, "João", "98765432100", null, null, null));

        assertTrue(clienteDAO.excluir(1L));

        assertNull(clienteDAO.buscarPorId(1L));
        assertEquals("João", clienteDAO.buscarPorId(2L).getNome());
    }

    @Test
    public void deveRetornarFalseAoExcluirIdInexistente() {
        clienteDAO.salvar(cliente);

        assertFalse(clienteDAO.excluir(99L));
        assertEquals(1, clienteDAO.buscarTodos().size());
    }

    @Test
    public void devePermitirSalvarNovamenteAposExcluir() {
        clienteDAO.salvar(cliente);
        clienteDAO.excluir(1L);

        assertTrue(clienteDAO.salvar(cliente));
    }
}
