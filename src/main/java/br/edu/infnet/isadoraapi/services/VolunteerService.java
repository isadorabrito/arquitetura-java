package br.edu.infnet.isadoraapi.services;

import br.edu.infnet.isadoraapi.interfaces.ICrudService;
import br.edu.infnet.isadoraapi.model.Address;
import br.edu.infnet.isadoraapi.model.Volunteer;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VolunteerService implements ICrudService<Volunteer, Long> {
    private static final String FILE_PATH = "src/main/resources/volunteers.txt";
    private static List<Volunteer> volunteers = new ArrayList<>();

    @Override
    public List<Volunteer> findAll() {
        loadData();
        return volunteers;
    }

    @Override
    public Optional<Volunteer> findById(Long id) {
        loadData();
        return volunteers.stream()
                .filter(volunteer -> volunteer.getId().equals(id))
                .findFirst();
    }

    @Override
    public Volunteer save(Volunteer volunteer) {
        loadData();

        for (int i = 0; i < volunteers.size(); i++) {
            if (volunteers.get(i).getId().equals(volunteer.getId())) {
                volunteers.set(i, volunteer);
                break;
            }
        }

        saveData();
        return volunteer;
    }

    @Override
    public void delete(Long id) {
        loadData();
        volunteers.removeIf(volunteer -> volunteer.getId().equals(id));
        saveData();
    }

    private void loadData() {
        volunteers.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                Volunteer volunteer = new Volunteer();
                volunteer.setId(Long.parseLong(data[0]));
                volunteer.setName(data[1]);
                volunteer.setEmail(data[2]);
                volunteer.setPhone(data[3]);
                volunteer.setCpf(data[4]);
                volunteer.setRegistration(Integer.parseInt(data[5]));
                LocalDateTime joinDate = LocalDateTime.parse(data[6] + "T00:00:00");
                volunteer.setJoinDate(joinDate);

                
                Address address = new Address();
                address.setZipCode(data[7]);
                address.setStreet(data[8]);
                address.setNeighborhood(data[9]);
                address.setCity(data[10]);
                address.setState(data[11]);
                volunteer.setAddress(address);

                volunteers.add(volunteer);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    private void saveData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {

            writer.write("id;name;email;phone;cpf;registration;join_date;zip_code;street;neighborhood;city;state\n");

            for (Volunteer volunteer : volunteers) {
                writer.write(String.format("%d;%s;%s;%s;%s;%d;%s;%s;%s;%s;%s;%s\n",
                        volunteer.getId(),
                        volunteer.getName(),
                        volunteer.getEmail(),
                        volunteer.getPhone(),
                        volunteer.getCpf(),
                        volunteer.getRegistration(),
                        volunteer.getJoinDate().toLocalDate(),
                        volunteer.getAddress().getZipCode(),
                        volunteer.getAddress().getStreet(),
                        volunteer.getAddress().getNeighborhood(),
                        volunteer.getAddress().getCity(),
                        volunteer.getAddress().getState()));
            }
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }
}