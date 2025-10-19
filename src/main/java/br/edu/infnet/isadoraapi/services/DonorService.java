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

        for (int i = 0; i < donors.size(); i++) {
            if (donors.get(i).getId().equals(donor.getId())) {
                donors.set(i, donor);
                break;
            }
        }

        saveData();
        return donor;
    }

    @Override
    public void delete(Long id) {
        loadData();
        donors.removeIf(donor -> donor.getId().equals(id));
        saveData();
    }

    private void loadData() {
        donors.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line = reader.readLine(); 
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
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