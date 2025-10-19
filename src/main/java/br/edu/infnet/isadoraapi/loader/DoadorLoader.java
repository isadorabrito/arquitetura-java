package br.edu.infnet.isadoraapi.loader;

import br.edu.infnet.isadoraapi.model.Doador;
import br.edu.infnet.isadoraapi.services.DoadorService;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

@Component
public class DoadorLoader implements ApplicationRunner {

    private final br.edu.infnet.isadoraapi.services.DoadorService doadorService;

    public DoadorLoader(DoadorService doadorService) {
        this.doadorService = doadorService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Resource resource = new ClassPathResource("doadores.txt");
        BufferedReader leitura = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));

        String linha = leitura.readLine();
        linha = leitura.readLine();
        String[] campos = null;

        while (linha != null) {
            campos = linha.split(",");

            Doador doador = new Doador();
            doador.setNome(campos[0].trim());
            doador.setEmail(campos[1].trim());
            doador.setTelefone(campos[2].trim());
            doador.setAtivo(Boolean.parseBoolean(campos[3].trim()));
            doador.setDataCadastro(LocalDate.now());

            doadorService.salvar(doador);

            linha = leitura.readLine();
        }

        List<Doador> doadores = doadorService.listarTodos();
        doadores.forEach(System.out::println);
        System.out.println("Total de doadores carregados: " + doadores.size());

        leitura.close();

    }
}