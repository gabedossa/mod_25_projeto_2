package br.com.ebac.generics;

import java.util.Collection;

/**
 * Operações de CRUD comuns a qualquer entidade {@link Persistente}.
 *
 * @param <T> tipo da entidade
 */
public interface IGenericDAO<T extends Persistente> {

    Boolean salvar(T entidade);

    T buscarPorId(Long id);

    Collection<T> buscarTodos();

    Boolean alterar(T entidade);

    Boolean excluir(Long id);
}
