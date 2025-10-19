package br.edu.infnet.isadoraapi.controllers;

import br.edu.infnet.isadoraapi.model.Doador;
import br.edu.infnet.isadoraapi.services.DoadorService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/doadores")
public class DoadorController {

    private final DoadorService doadorService;

    public DoadorController(DoadorService doadorService) {
        this.doadorService = doadorService;
    }

    @GetMapping
    public List<Doador> listarTodos() {
        return doadorService.listarTodos();
    }

}
