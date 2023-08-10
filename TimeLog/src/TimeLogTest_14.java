import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimeLogTest_14 {

    @Test
    public void testSignalerDebutTravailEmploye() throws Exception {
        TimeLog timeLog = new TimeLog(3); // Créez une instance de TimeLog avec le nombre maximal d'assignations par employé

        // Créez un employé pour le test
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        Date dateEmbauche = formatDate.parse("2023-01-01");
        List<Integer> list = new ArrayList<>();
        list.add(1);
        Employe employe = new Employe("John Doe", 1, "password", dateEmbauche, "123456789", 15, 20, "Developer", list);

        // Ajoutez l'employé à la liste des employés dans TimeLog
        timeLog.ajouterEmploye(employe);

        // Créez une date pour le début de l'activité
        SimpleDateFormat formatDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date debutTravail = formatDateTime.parse("2023-08-09 09:00");

        // Signalez le début du travail pour l'employé sur un projet spécifique
        timeLog.signalerDebutTravailEmploye(employe, 123, debutTravail);

        // Vérifiez si le début du travail a été correctement signalé pour l'employé
        assertEquals(1, employe.getProjetsAssignes().size());
        //assertEquals(debutTravail, employe.getProjetsAssignes().get(0).getDateDebut());
    }
}