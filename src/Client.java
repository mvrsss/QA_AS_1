import java.time.LocalDateTime;

// Client class
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
        // Assume generation of ID
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

    // Assume ID generation logic
    private String generateId() {
        return "GeneratedID";
    }
}


