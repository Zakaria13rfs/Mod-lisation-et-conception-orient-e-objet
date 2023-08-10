import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;


import static org.junit.jupiter.api.Assertions.*;

public class TimeLogTest_11 {

    @Test
    public void testModifyEmployeeInfo() {
        TimeLog timeLog = new TimeLog(5); // Max assignments per employee

        // Ajouter un administrateur
        Admin admin = new Admin("admin", "admin");
        timeLog.ajouterAdmin(admin);

        // Ajouter un employé
        Employe employe = new Employe("John Doe", 1, "password", new Date(), "1234567890", 20, 25, "Developer", new ArrayList<>());
        timeLog.ajouterEmploye(employe);

        // Modifier les informations de l'employé
        timeLog.modifierEmployes(admin, Collections.singletonList(
                new Employe("John Smith", 1, "newpassword", new Date(), "0987654321", 25, 30, "Designer", new ArrayList<>())
        ));
        // Vérifier si les modifications ont été appliquées
        assertEquals("John Smith", timeLog.rechercherEmployeParID(1).getNom());
        assertEquals("newpassword", timeLog.rechercherEmployeParID(1).getMotDePasse());
    }
}