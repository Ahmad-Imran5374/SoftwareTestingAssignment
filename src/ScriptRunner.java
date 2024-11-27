import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ScriptRunner {
    private final Connection connection;

    public ScriptRunner(Connection connection) {
        this.connection = connection;
    }

    public void runScript(Reader reader) throws IOException, SQLException {
        StringBuilder command = new StringBuilder();
        BufferedReader br = new BufferedReader(reader);

        String line;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (!line.startsWith("--") && !line.isEmpty()) {  // Skip comments and blank lines
                command.append(line).append(" ");
                if (line.endsWith(";")) {  // Execute when statement ends with a semicolon
                    try (Statement stmt = connection.createStatement()) {
                        stmt.execute(command.toString());
                        command = new StringBuilder();
                    }
                }
            }
        }
        br.close();
    }
}
