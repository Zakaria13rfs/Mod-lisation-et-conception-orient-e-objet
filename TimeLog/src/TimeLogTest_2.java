import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeLogTest_2 {

    @Test
    void gererAffichageEtatGlobal() {
        var testEtatGlobal = new TimeLog(2);
        testEtatGlobal.chargerDonneesDepuisFichier("C:\\Users\\olivi\\IdeaProjects\\Modelisation_App\\src\\initial_data.json");
        testEtatGlobal.afficherRapportEtatGlobal();
    }
}