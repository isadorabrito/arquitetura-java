package br.edu.infnet.isadoraapi.services;

import br.edu.infnet.isadoraapi.interfaces.CrudService;
import br.edu.infnet.isadoraapi.model.Doador;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;


@Service
public class DoadorService implements CrudService<Doador, Long> {
    
    private final ConcurrentHashMap<Long, Doador> armazenamento = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    
    @Override
    public Doador salvar(Doador doador) {
        if (doador.getId() == null) {
            doador.setId(idGenerator.getAndIncrement());
        }
        armazenamento.put(doador.getId(), doador);
        return doador;
    }
    
    @Override
    public Optional<Doador> buscarPorId(Long id) {
        return Optional.ofNullable(armazenamento.get(id));
    }
    
    @Override
    public List<Doador> listarTodos() {
        return new ArrayList<>(armazenamento.values());
    }
    
    @Override
    public void excluir(Long id) {
        armazenamento.remove(id);
    }
}