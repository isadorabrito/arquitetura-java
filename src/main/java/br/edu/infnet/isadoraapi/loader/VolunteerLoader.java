package br.edu.infnet.isadoraapi.loader;

import br.edu.infnet.isadoraapi.model.Address;
import br.edu.infnet.isadoraapi.model.Volunteer;
import br.edu.infnet.isadoraapi.services.VolunteerService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
public class VolunteerLoader implements ApplicationRunner {

    private final VolunteerService volunteerService;

    public VolunteerLoader(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Resource resource = new ClassPathResource("volunteers.txt");
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));

        String line = reader.readLine();
        String[] fields;

        while ((line = reader.readLine()) != null) {
            fields = line.split(";");

            Volunteer volunteer = new Volunteer();
            String idField = fields[0].trim();

            volunteer.setId(Long.parseLong(idField));
            volunteer.setName(fields[1].trim());
            volunteer.setEmail(fields[2].trim());
            volunteer.setPhone(fields[3].trim());
            volunteer.setCpf(fields[4].trim());
            volunteer.setRegistration(Integer.parseInt(fields[5].trim()));

            LocalDate date = LocalDate.parse(fields[6].trim());
            
            
            Address address = new Address();
            address.setZipCode(fields[7].trim());
            address.setStreet(fields[8].trim());
            address.setNeighborhood(fields[9].trim());
            address.setCity(fields[10].trim());
            address.setState(fields[11].trim());
            volunteer.setAddress(address);
            volunteer.setJoinDate(LocalDateTime.of(date, LocalTime.MIDNIGHT));

            volunteerService.save(volunteer);
        }

        List<Volunteer> volunteers = volunteerService.findAll();
        System.out.println("Total volunteers loaded: " + volunteers.size());
        volunteers.forEach(System.out::println);

        reader.close();
    }
}