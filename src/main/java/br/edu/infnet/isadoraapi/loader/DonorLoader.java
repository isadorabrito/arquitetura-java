package br.edu.infnet.isadoraapi.loader;

import br.edu.infnet.isadoraapi.model.Donor;
import br.edu.infnet.isadoraapi.services.DonorService;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class DonorLoader implements ApplicationRunner {

    private final DonorService donorService;

    public DonorLoader(DonorService donorService) {
        this.donorService = donorService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Resource resource = new ClassPathResource("donors.txt");
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));

        String line = reader.readLine();
        String[] fields;

        while ((line = reader.readLine()) != null) {
            fields = line.split(";");

            Donor donor = new Donor();
            donor.setId(Long.parseLong(fields[0].trim()));
            donor.setName(fields[1].trim());
            donor.setEmail(fields[2].trim());
            donor.setPhone(fields[3].trim());
            donor.setCpf(fields[4].trim());
            donor.setActive(Boolean.parseBoolean(fields[5].trim()));

            donorService.save(donor);
        }

        List<Donor> donors = donorService.findAll();
        System.out.println("Total donors loaded: " + donors.size());
        donors.forEach(System.out::println);

        reader.close();
    }
}