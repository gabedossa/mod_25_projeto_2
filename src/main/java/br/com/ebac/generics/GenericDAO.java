package br.com.ebac.generics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Implementação em memória do CRUD genérico. Cada DAO específico estende
 * esta classe e só acrescenta as buscas próprias da sua entidade.
 *
 * @param <T> tipo da entidade
 */
public abstract class GenericDAO<T extends Persistente> implements IGenericDAO<T> {

    protected final Map<Long, T> registros = new LinkedHashMap<>();

    @Override
    public Boolean salvar(T entidade) {
        if (registros.containsKey(entidade.getId())) {
            return false;
        }
        registros.put(entidade.getId(), entidade);
        return true;
    }

    @Override
    public T buscarPorId(Long id) {
        return registros.get(id);
    }

    @Override
    public Collection<T> buscarTodos() {
        return new ArrayList<>(registros.values());
    }

    @Override
    public Boolean alterar(T entidade) {
        if (!registros.containsKey(entidade.getId())) {
            return false;
        }
        registros.put(entidade.getId(), entidade);
        return true;
    }

    @Override
    public Boolean excluir(Long id) {
        return registros.remove(id) != null;
    }
}
