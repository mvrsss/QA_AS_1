import java.util.HashMap;
import java.util.Map;

class AuthenticationService {
    private Map<String, String> credentials; // Map to store username-password pairs

    public AuthenticationService() {
        this.credentials = new HashMap<>();
    }

    // Method to register a new user with a username and password
    public void register(String username, String password) {
        if (credentials.containsKey(username)) {
            throw new IllegalArgumentException("Username already exists");
        }
        credentials.put(username, password);
    }

    // Method to authenticate a user by verifying the username and password
    public boolean login(String username, String password) {
        String storedPassword = credentials.get(username);
        return storedPassword != null && storedPassword.equals(password);
    }
}
