package br.com.ebac.generics;

import java.util.Collection;

/**
 * Service genérico: valida as entradas e delega a persistência ao DAO.
 * Cada service específico estende esta classe e só acrescenta (ou
 * sobrescreve) as regras próprias da sua entidade.
 *
 * @param <T> tipo da entidade
 */
public abstract class GenericService<T extends Persistente> implements IGenericService<T> {

    protected final IGenericDAO<T> dao;

    protected GenericService(IGenericDAO<T> dao) {
        this.dao = dao;
    }

    @Override
    public Boolean salvar(T entidade) {
        validar(entidade);
        return dao.salvar(entidade);
    }

    @Override
    public T buscarPorId(Long id) {
        return dao.buscarPorId(id);
    }

    @Override
    public Collection<T> buscarTodos() {
        return dao.buscarTodos();
    }

    @Override
    public Boolean alterar(T entidade) {
        validar(entidade);
        return dao.alterar(entidade);
    }

    @Override
    public Boolean excluir(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id é obrigatório");
        }
        return dao.excluir(id);
    }

    protected void validar(T entidade) {
        if (entidade == null || entidade.getId() == null) {
            throw new IllegalArgumentException("Entidade e id são obrigatórios");
        }
    }
}
