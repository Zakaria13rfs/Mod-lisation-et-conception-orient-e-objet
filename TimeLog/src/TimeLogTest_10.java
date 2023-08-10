import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TimeLogTest_10 {

    @Test
    public void testAdminLogin() {
        TimeLog timeLog = new TimeLog(5); // Max assignments per employee

        // Ajouter un administrateur
        Admin admin = new Admin("admin", "admin");
        timeLog.ajouterAdmin(admin);

        // Tester la connexion avec les bonnes informations
        assertTrue(timeLog.estAdminValide("admin", "admin"));

        // Tester la connexion avec des informations incorrectes
        assertFalse(timeLog.estAdminValide("admin", "wrongpassword"));
        assertFalse(timeLog.estAdminValide("wrongusername", "admin"));
    }
}