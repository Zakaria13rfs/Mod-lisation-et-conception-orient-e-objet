import org.junit.jupiter.api.Test;
import java.util.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class TimeLogTest_12 {

    @Test
    public void testModifyProjectsAndAssignments() {
        TimeLog timeLog = new TimeLog(5); // Max assignments per employee

        // Ajouter un administrateur
        Admin admin = new Admin("admin", "admin");
        timeLog.ajouterAdmin(admin);

        // Ajouter un employé
        Employe employe = new Employe("John Doe", 1, "password", new Date(), "1234567890", 20, 25, "Developer", new ArrayList<>());
        timeLog.ajouterEmploye(employe);

        // Ajouter un projet
        Projet projet = new Projet(1, "Projet A", new Date(), new Date(), new HashMap<>());
        timeLog.ajouterProjet(projet);

        // Modifier la liste des projets et leurs caractéristiques
        timeLog.modifierProjets(admin, Collections.singletonList(
                new Projet(1, "Nouveau Projet A", new Date(), new Date(), new HashMap<>())
        ));

        // Assigner l'employé au projet
        timeLog.assignerEmployeAProjet(admin, 1, projet);

        // Vérifier si les modifications et les assignations ont été appliquées
        assertEquals("Nouveau Projet A", timeLog.rechercherProjetParID(1).getName());
        assertTrue(timeLog.getProjetsAssignesAEmploye(1).contains(projet.getProjectID()));
    }
}