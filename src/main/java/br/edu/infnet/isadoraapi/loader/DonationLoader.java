package br.edu.infnet.isadoraapi.loader;

import br.edu.infnet.isadoraapi.model.Donation;
import br.edu.infnet.isadoraapi.enums.DonationTypeEnum;
import br.edu.infnet.isadoraapi.services.DonationService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

@Component
@Order(3)
public class DonationLoader implements ApplicationRunner {

    private final DonationService donationService;

    public DonationLoader(DonationService donationService) {
        this.donationService = donationService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Resource resource = new ClassPathResource("donations.txt");
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));

        String line = reader.readLine();
        String[] fields;

        while ((line = reader.readLine()) != null) {
            fields = line.split(";");
            
         
            Long donorId = Long.parseLong(fields[1].trim());

        
            Donation donation = new Donation();
            donation.setDonationType(DonationTypeEnum.valueOf(fields[2].trim()));
            donation.setQuantity(Integer.parseInt(fields[3].trim()));
            donation.setDescription(fields[4].trim());
            donation.setDonationDate(LocalDate.parse(fields[5].trim()));

         
            donationService.create(donation, donorId);
        }

     
        List<Donation> donations = donationService.findAll();
        System.out.println("Total donations loaded: " + donations.size());
        donations.forEach(System.out::println);

        reader.close();
    }
}