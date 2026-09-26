package br.com.ebac.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ClienteTest {

    private static final LocalDate NASCIMENTO = LocalDate.of(1995, 5, 20);

    private Cliente cliente;

    @BeforeEach
    public void init() {
        cliente = new Cliente(1L, "Maria Silva", "12345678900", NASCIMENTO, "maria@email.com", "11999998888");
    }

    @AfterEach
    public void end() {
        cliente = null;
    }

    @Test
    public void deveCriarClienteComTodosOsCampos() {
        assertEquals(1L, cliente.getId());
        assertEquals("Maria Silva", cliente.getNome());
        assertEquals("12345678900", cliente.getCpf());
        assertEquals(NASCIMENTO, cliente.getDataNasc());
        assertEquals("maria@email.com", cliente.getEmail());
        assertEquals("11999998888", cliente.getTelefone());
    }

    @Test
    public void deveCriarClienteVazio() {
        Cliente vazio = new Cliente();

        assertNull(vazio.getId());
        assertNull(vazio.getNome());
        assertNull(vazio.getCpf());
        assertNull(vazio.getDataNasc());
        assertNull(vazio.getEmail());
        assertNull(vazio.getTelefone());
    }

    @Test
    public void devePreencherCamposPelosSetters() {
        LocalDate nascimento = LocalDate.of(1988, 12, 1);
        Cliente joao = new Cliente();
        joao.setId(10L);
        joao.setNome("João Souza");
        joao.setCpf("98765432100");
        joao.setDataNasc(nascimento);
        joao.setEmail("joao@email.com");
        joao.setTelefone("21988887777");

        assertEquals(10L, joao.getId());
        assertEquals("João Souza", joao.getNome());
        assertEquals("98765432100", joao.getCpf());
        assertEquals(nascimento, joao.getDataNasc());
        assertEquals("joao@email.com", joao.getEmail());
        assertEquals("21988887777", joao.getTelefone());
    }

    @Test
    public void deveAlterarDadosDoCliente() {
        cliente.setEmail("maria.nova@email.com");
        cliente.setTelefone("11911112222");

        assertEquals("maria.nova@email.com", cliente.getEmail());
        assertEquals("11911112222", cliente.getTelefone());
    }

    @Test
    public void deveManterZeroAEsquerdaNoTelefone() {
        cliente.setTelefone("0800123456");

        assertEquals("0800123456", cliente.getTelefone());
    }

    @Test
    public void clientesComMesmoIdDevemSerIguais() {
        Cliente outro = new Cliente(1L, "Outro nome", "98765432100", null, null, null);

        assertEquals(cliente, outro);
        assertEquals(cliente.hashCode(), outro.hashCode());
    }

    @Test
    public void clientesComIdsDiferentesNaoDevemSerIguais() {
        Cliente outro = new Cliente(2L, "Maria Silva", "12345678900", null, null, null);

        assertNotEquals(cliente, outro);
    }

    @Test
    public void clienteNaoDeveSerIgualANullOuOutroTipo() {
        Produto produto = new Produto(1L, "Maria Silva", null, null);

        assertNotEquals(null, cliente);
        assertNotEquals(cliente, produto);
    }

    @Test
    public void clienteDeveSerIgualASiMesmo() {
        assertEquals(cliente, cliente);
    }

    @Test
    public void toStringDeveConterOsDadosDoCliente() {
        String texto = cliente.toString();

        assertTrue(texto.contains("Maria Silva"));
        assertTrue(texto.contains("12345678900"));
        assertTrue(texto.contains("1995-05-20"));
        assertTrue(texto.contains("maria@email.com"));
        assertTrue(texto.contains("11999998888"));
    }
}
