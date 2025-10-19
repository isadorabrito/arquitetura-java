package br.edu.infnet.isadoraapi.services;

import br.edu.infnet.isadoraapi.interfaces.ICrudService;
import br.edu.infnet.isadoraapi.model.Donor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DonorService implements ICrudService<Donor, Long> {

    private static final String FILE_PATH = "src/main/resources/donors.txt";
    private static List<Donor> donors = new ArrayList<>();

    @Override
    public List<Donor> findAll() {
        loadData();
        return donors;
    }

    @Override
    public Optional<Donor> findById(Long id) {
        loadData();
        return donors.stream()
                .filter(donor -> donor.getId().equals(id))
                .findFirst();
    }

    @Override
    public Donor save(Donor donor) {
        loadData();

        Long newId = donors.stream()
                .mapToLong(Donor::getId)
                .max()
                .orElse(0L) + 1;
        donor.setId(newId);
        donors.add(donor);

        saveData();
        return donor;
    }

    @Override
    public void update(Long id, Donor donorDetails) {
        loadData();

        for (int i = 0; i < donors.size(); i++) {
            Donor existingDonor = donors.get(i);

            if (existingDonor.getId().equals(id)) {
                existingDonor.setName(donorDetails.getName());
                existingDonor.setEmail(donorDetails.getEmail());
                existingDonor.setPhone(donorDetails.getPhone());
                existingDonor.setCpf(donorDetails.getCpf());
                existingDonor.setActive(donorDetails.isActive());

                saveData();

            }
        }
    }

    @Override
    public void delete(Long id) {
        loadData();
        donors.removeIf(donor -> donor.getId().equals(id));
        saveData();
    }

    public Optional<Donor> deactivate(Long id) {
        loadData();
        Optional<Donor> donorOpt = donors.stream()
                .filter(donor -> donor.getId().equals(id))
                .findFirst();

        donorOpt.ifPresent(donor -> {
            donor.setActive(false);
            saveData();
        });

        return donorOpt;
    }

    private void loadData() {
        donors.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] data = line.split(";");
                if (data.length < 6)
                    continue;

                Donor donor = new Donor();
                donor.setId(Long.parseLong(data[0]));
                donor.setName(data[1]);
                donor.setEmail(data[2]);
                donor.setPhone(data[3]);
                donor.setCpf(data[4]);
                donor.setActive(Boolean.parseBoolean(data[5]));
                donors.add(donor);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    private void saveData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write("id;name;email;phone;cpf;active\n");

            for (Donor donor : donors) {
                writer.write(String.format("%d;%s;%s;%s;%s;%b\n",
                        donor.getId(),
                        donor.getName(),
                        donor.getEmail(),
                        donor.getPhone(),
                        donor.getCpf(),
                        donor.isActive()));
            }
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }
}
