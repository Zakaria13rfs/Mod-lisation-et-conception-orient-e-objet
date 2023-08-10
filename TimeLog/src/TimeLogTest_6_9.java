import org.junit.jupiter.api.Test;

class TimeLogTest_6_9 {

    @Test
    void assignerEmployeAProjetFail() {
        var testAssignationEmploye = new TimeLog(2);
        testAssignationEmploye.chargerDonneesDepuisFichier("C:\\Users\\olivi\\IdeaProjects\\Modelisation_App\\src\\initial_data.json");
        testAssignationEmploye.assignerEmployeAProjet(testAssignationEmploye.loginAdmin("admin","admin"),1,testAssignationEmploye.rechercherProjetParID(3));
        System.out.println(testAssignationEmploye.rechercherEmployeParID(1).getProjetsAssignes());
    }

    @Test
    void assignerEmployeAProjet() {
        var testAssignationEmploye = new TimeLog(3);
        testAssignationEmploye.chargerDonneesDepuisFichier("C:\\Users\\olivi\\IdeaProjects\\Modelisation_App\\src\\initial_data.json");
        testAssignationEmploye.assignerEmployeAProjet(testAssignationEmploye.loginAdmin("admin","admin"),1,testAssignationEmploye.rechercherProjetParID(3));
        System.out.println(testAssignationEmploye.rechercherEmployeParID(1).getProjetsAssignes());
    }
}