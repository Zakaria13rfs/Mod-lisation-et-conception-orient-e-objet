import org.junit.jupiter.api.Test;
import java.util.Date;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class TimeLogTest_13 {

    @Test
    public void testEmployeeLogin() {
        TimeLog timeLog = new TimeLog(5); // Max assignments per employee

        // Ajouter un employé
        Employe employe = new Employe("John Doe", 1, "password", new Date(), "1234567890", 20, 25, "Developer", new ArrayList<>());
        timeLog.ajouterEmploye(employe);

        // Tester la connexion avec les bonnes informations
        assertNotNull(timeLog.loginEmploye(1, "password"));

        // Tester la connexion avec des informations incorrectes
        assertNull(timeLog.loginEmploye(1, "wrongpassword"));
        assertNull(timeLog.loginEmploye(2, "password")); // Employé avec un ID incorrect
    }
}