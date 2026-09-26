package br.com.ebac.generics;

/**
 * Contrato das entidades que podem ser gravadas por um {@link IGenericDAO}.
 */
public interface Persistente {

    Long getId();
}
