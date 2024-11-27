import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.*;
import java.io.*;

public class LoginAppTest {
    private LoginApp loginApp;

    @BeforeEach
    public void setUp() throws Exception {
        // Initialize LoginApp
        loginApp = new LoginApp();

        // Set up the test database
        String setupFilePath = "db/setup.sql";  // Adjust path if needed
        Connection connection = DriverManager.getConnection("jdbc:sqlite:test.db");
        ScriptRunner runner = new ScriptRunner(connection);
        runner.runScript(new BufferedReader(new FileReader(setupFilePath)));
        connection.close();
    }

    @Test
    public void testValidLogin() throws ClassNotFoundException {
        String userName = loginApp.authenticateUser("johndoe@example.com", "password123");
        assertNotNull(userName);
        assertEquals("John Doe", userName);
    }

    @Test
    public void testInvalidLogin() throws ClassNotFoundException {
        String userName = loginApp.authenticateUser("invalid@example.com", "wrongpassword");
        assertNull(userName);
    }

    @Test
    public void testEmptyEmail() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            loginApp.authenticateUser("", "password123");
        });
        assertEquals("Email and Password cannot be empty.", exception.getMessage());
    }

    @Test
    public void testEmptyPassword() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            loginApp.authenticateUser("johndoe@example.com", "");
        });
        assertEquals("Email and Password cannot be empty.", exception.getMessage());
    }

    @Test
    public void testSQLInjection() throws ClassNotFoundException {
        String userName = loginApp.authenticateUser("johndoe@example.com", "' OR '1'='1");
        assertNull(userName);
    }
}
