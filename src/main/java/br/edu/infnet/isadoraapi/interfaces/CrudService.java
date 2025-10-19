package br.edu.infnet.isadoraapi.interfaces;

import java.util.List;
import java.util.Optional;


public interface CrudService<T, ID> {
    
    T salvar(T entity);
    Optional<T> buscarPorId(ID id);
    List<T> listarTodos();
    void excluir(ID id);
}