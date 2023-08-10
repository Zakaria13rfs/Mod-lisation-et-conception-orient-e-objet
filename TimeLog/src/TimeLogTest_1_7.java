import org.junit.jupiter.api.Test;

class TimeLogTest_1_7 {

    @Test
    void gererAffichageEtatProjet() {
        var testEtatProjet = new TimeLog(2);
        testEtatProjet.chargerDonneesDepuisFichier("C:\\Users\\olivi\\IdeaProjects\\Modelisation_App\\src\\initial_data.json");
        testEtatProjet.afficherRapportEtatProjet(1);
    }
}