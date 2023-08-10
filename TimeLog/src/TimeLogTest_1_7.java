import org.junit.jupiter.api.Test;

class TimeLogTest_1_7 {

    @Test
    void gererAffichageEtatProjet() {
        var testEtatProjet = new TimeLog(2);
        testEtatProjet.chargerDonneesDepuisFichier("C:\\Users\\olivi\\OneDrive\\Bureau\\Coding\\Mod-lisation-et-conception-orient-e-objet\\TimeLog\\src\\initial_data.json");
        testEtatProjet.afficherRapportEtatProjet(1);
    }
}