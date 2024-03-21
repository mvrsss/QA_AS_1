package com.qa.as1;
import java.time.LocalDateTime;
import java.util.UUID;

public class Client {
    private String name;
    private String surname;
    private String username;
    private LocalDateTime dateOfBirth;
    private String address;
    private String id;

    public Client(String name, String surname, String username, LocalDateTime dateOfBirth, String address) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.id = generateId();
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getUsername() {
        return username;
    }

    public LocalDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public String getId() {
        return id;
    }

    // Method to update address
    public void updateAddress(String newAddress) {
        this.address = newAddress;
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }
}


