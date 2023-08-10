import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class TimeLog {
    private List<Admin> admins;
    private List<Employe> employes;
    private List<Projet> projets;
    private List<Activite> activites;

    private List<Integer> employeeProjectAssignments; // EmployeeID -> List of Projects
    private int maxAssignmentsPerEmployee; // NPE (Nombre de projets maximum pour un employé)

    public TimeLog(int maxAssignmentsPerEmployee) {
        admins = new ArrayList<>();
        employes = new ArrayList<>();
        projets = new ArrayList<>();
        activites = new ArrayList<>();
        this.employeeProjectAssignments = new ArrayList<>();
        this.maxAssignmentsPerEmployee = maxAssignmentsPerEmployee;
    }

    // Méthode pour charger les données à partir du fichier JSON
    public void chargerDonneesDepuisFichier(String nomFichier) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject initialData = (JSONObject) parser.parse(new FileReader(nomFichier));

            // Charger les administrateurs
            JSONArray adminsArray = (JSONArray) initialData.get("admins");
            if (adminsArray != null) {
                for (Object adminObj : adminsArray) {
                    JSONObject adminJSON = (JSONObject) adminObj;
                    String username = (String) adminJSON.get("nomUtilisateur");
                    String password = (String) adminJSON.get("motDePasse");
                    Admin admin = new Admin(username, password);
                    ajouterAdmin(admin);
                }
            }


            // Charger les employés
            JSONArray employeesArray = (JSONArray) initialData.get("employes");
            if (employeesArray != null) {
                for (Object employeeObj : employeesArray) {
                    JSONObject employeeJSON = (JSONObject) employeeObj;
                    String name = (String) employeeJSON.get("nomUtilisateur");
                    int employeeID = Integer.parseInt(employeeJSON.get("idEmploye").toString());
                    String password = (String) employeeJSON.get("motDePasse");
                    String embauche =  employeeJSON.get("dateEmbauche").toString();
                    SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
                    Date dateEmbauche = formatDate.parse(embauche);
                    int tauxHoraire = Integer.parseInt(employeeJSON.get("tauxHoraireBase").toString());
                    int tauxSupp = Integer.parseInt(employeeJSON.get("tauxHoraireSupplementaire").toString());
                    String NAS = (String) employeeJSON.get("numSecuriteSociale");
                    String poste = (String) employeeJSON.get("poste");
                    String projetAssigner = employeeJSON.get("projetsAssignes").toString();
                    String[] stringProjetAssigner = projetAssigner.replaceAll("\\[", "")
                            .replaceAll("]", "")
                            .split(",");
                    List<Integer> list = new ArrayList<Integer>();
                    for (int x = 0; x<stringProjetAssigner.length; x++){
                        list.add(Integer.valueOf(stringProjetAssigner[x]));
                    }
                    HashMap<Integer, Map<String, Double>> heuresTravail = new HashMap<Integer, Map<String, Double>>();
                    Employe employe = new Employe(name, employeeID, password, dateEmbauche, NAS, tauxHoraire, tauxSupp, poste, list);
                    ajouterEmploye(employe);
                }
            }

            // Charger les projets
            JSONArray projectsArray = (JSONArray) initialData.get("projets");
            if (projectsArray != null) {
                for (Object projectObj : projectsArray) {
                    JSONObject projectJSON = (JSONObject) projectObj;
                    int projectID = Integer.parseInt(projectJSON.get("idProjet").toString());
                    String name = (String) projectJSON.get("nomProjet");
                    String startDateStr = (String) projectJSON.get("dateDebut");
                    String endDateStr = (String) projectJSON.get("dateFin");

                    SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
                    Date startDate = formatDate.parse(startDateStr);
                    Date endDate = formatDate.parse(endDateStr);
                    HashMap<String , Long> budget = (HashMap<String, Long>) projectJSON.get("budget");

                    Projet projet = new Projet(projectID, name, startDate, endDate, budget);
                    ajouterProjet(projet);
                }
            }


            // Charger les activités
            JSONArray activitiesArray = (JSONArray) initialData.get("activites");
            if (activitiesArray != null) {
                for (Object activityObj : activitiesArray) {
                    JSONObject activityJSON = (JSONObject) activityObj;
                    String startDateStr = (String) activityJSON.get("startDate");
                    String endDateStr = (String) activityJSON.get("endDate");
                    String discipline = (String) activityJSON.get("discipline");

                    SimpleDateFormat formatDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    Date startDate = formatDateTime.parse(startDateStr);
                    Date endDate = formatDateTime.parse(endDateStr);

                    Activite activite = new Activite(startDate, endDate, discipline);
                    ajouterActivite(activite);
                }
            }
        } catch (IOException | org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("Error parsing date: " + e.getMessage());
        }
    }

    // Méthodes pour gérer les administrateurs
    public void ajouterAdmin(Admin admin) {
        admins.add(admin);
    }

    public void supprimerAdmin(Admin admin) {
        admins.remove(admin);
    }

    public boolean estAdminValide(String nomUtilisateur, String motDePasse) {
        for (Admin admin : admins) {
            if (admin.getNomUtilisateur().equals(nomUtilisateur) && admin.getMotDePasse().equals(motDePasse)) {
                return true;
            }
        }
        return false;
    }

    // Méthodes pour gérer les employés
    public void ajouterEmploye(Employe employe) {
        employes.add(employe);
    }

    // Méthode pour modifier les projets par un admin
    public void modifierProjets(Admin admin, List<Projet> nouveauxProjets) {
        if (admins.contains(admin)) {
            projets = nouveauxProjets;
        } else {
            System.out.println("Unauthorized action. Access denied!");
        }
    }

    // Méthode pour modifier les employés par un admin
    public void modifierEmployes(Admin admin, List<Employe> nouveauxEmployes) {
        if (admins.contains(admin)) {
            employes = nouveauxEmployes;
        } else {
            System.out.println("Unauthorized action. Access denied!");
        }
    }

    public void supprimerEmploye(Employe employe) {
        employes.remove(employe);
    }

    public Employe rechercherEmployeParID(int numeroIdentification) {
        for (Employe employe : employes) {
            if (employe.getNumeroIdentification() == numeroIdentification) {
                return employe;
            }
        }
        return null;
    }

    // Méthodes pour gérer les projets
    public void ajouterProjet(Projet projet) {
        projets.add(projet);
    }

    public void supprimerProjet(Projet projet) {
        projets.remove(projet);
    }

    public Projet rechercherProjetParID(int projectID) {
        for (Projet projet : projets) {
            if (projet.getProjectID() == projectID) {
                return projet;
            }
        }
        return null;
    }

    // Méthodes pour gérer les activités
    public void ajouterActivite(Activite activite) {
        activites.add(activite);
    }

    public void supprimerActivite(Activite activite) {
        activites.remove(activite);
    }
    // Method to signal the start of work for an employee on a specific project
    public void signalerDebutTravailEmploye(Employe employe, int projectID, Date startDate) {
        employe.signalerDebutTravail(projectID, startDate);
    }

    // Method to signal the end of work for an employee on a specific project
    public void signalerFinTravailEmploye(Employe employe, int projectID, Date endDate) {
        employe.signalerFinTravail(projectID, endDate);
    }


    // Méthode pour afficher le rapport d'état d'un projet
    public void afficherRapportEtatProjet(int projectID) {
        Projet projet = rechercherProjetParID(projectID);
        if (projet != null) {
            // Calculer le pourcentage d'avancement de chaque discipline
            Map<String, Double> pourcentageAvancement = new HashMap<>();
            for (String discipline : projet.getBudgetedHoursByDiscipline().keySet()) {
                double workedHours = 0.0;
                for (Activite activite : activites) {
                    if (activite.getDiscipline().equals(discipline) && activite.getHeureFin() != null) {
                        workedHours += calculateHoursDifference(activite.getHeureDebut(), activite.getHeureFin());
                    }
                }
                double pourcentage = (workedHours / projet.getBudgetedHoursByDiscipline(discipline)) * 100.0;
                pourcentageAvancement.put(discipline, pourcentage);
            }

            // Afficher le rapport d'état du projet
            System.out.println("Rapport d'état du projet " + projet.getProjectID());
            System.out.println("Nom du projet: " + projet.getName());
            System.out.println("Date de début: " + projet.getStartDate());
            System.out.println("Date de fin: " + projet.getEndDate());
            System.out.println("Disciplines:" + projet.getBudgetedHoursByDiscipline());
            for (String discipline : projet.getBudgetedHoursByDiscipline().keySet()) {
                double budgetedHours = projet.getBudgetedHoursByDiscipline(discipline);
                double workedHours = 0.0;
                for (Activite activite : activites) {
                    if (activite.getDiscipline().equals(discipline) && activite.getHeureFin() != null) {
                        workedHours += calculateHoursDifference(activite.getHeureDebut(), activite.getHeureFin());
                    }
                }
                double pourcentage = pourcentageAvancement.get(discipline);
                System.out.println("  Discipline: " + discipline);
                System.out.println("    Heures budgétées: " + budgetedHours);
                System.out.println("    Heures travaillées: " + workedHours);
                System.out.println("    Pourcentage d'avancement: " + pourcentage + "%");
            }
        } else {
            System.out.println("Projet non trouvé !");
        }
    }

    // Méthode pour afficher le rapport d'état global de tous les projets
    public void afficherRapportEtatGlobal() {
        System.out.println("===== Rapport d'état global de tous les projets =====");
        for (Projet projet : projets) {
            afficherRapportEtatProjet(projet.getProjectID());
        }
    }

    // Méthode pour gérer l'assignation d'un employé à un projet
    public void gererAssignationEmployeProjet(Scanner scanner) {
        System.out.println("===== Assigner un Employé à un Projet =====");
        System.out.print("Entrez l'ID de l'employé : ");
        int employeeID = scanner.nextInt();
        scanner.nextLine(); // Consommer le caractère de nouvelle ligne laissé par nextInt()

        System.out.print("Entrez l'ID du projet : ");
        int projectID = scanner.nextInt();
        scanner.nextLine(); // Consommer le caractère de nouvelle ligne laissé par nextInt()

        Projet projet = rechercherProjetParID(projectID);
        Employe employe = rechercherEmployeParID(employeeID);

        if (projet == null || employe == null) {
            System.out.println("ID de projet ou ID d'employé non valide. L'assignation a échoué.");
        } else {
            assignerEmployeAProjet(admins.get(0), employeeID, projet);
        }
    }

    // Méthode pour gérer la suppression d'une assignation d'un employé à un projet
    public void gererSuppressionAssignationEmploye(Scanner scanner) {
        System.out.println("===== Supprimer l'assignation d'un Employé à un Projet =====");
        System.out.print("Entrez l'ID de l'employé : ");
        int employeeID = scanner.nextInt();
        scanner.nextLine(); // Consommer le caractère de nouvelle ligne laissé par nextInt()

        System.out.print("Entrez l'ID du projet : ");
        int projectID = scanner.nextInt();
        scanner.nextLine(); // Consommer le caractère de nouvelle ligne laissé par nextInt()

        Projet projet = rechercherProjetParID(projectID);
        Employe employe = rechercherEmployeParID(employeeID);

        if (projet == null || employe == null) {
            System.out.println("ID de projet ou ID d'employé non valide. La suppression de l'assignation a échoué.");
        } else {
            supprimerAffectationEmploye(admins.get(0), employeeID, projet);
        }
    }

    // Méthode pour assigner un employé à un projet par un admin
    public void assignerEmployeAProjet(Admin admin, int employeeID, Projet projet) {
        if (admins.contains(admin)) {
            List<Integer> employeeAssignments = rechercherEmployeParID(employeeID).getProjetsAssignes();
            if (employeeAssignments.size() < maxAssignmentsPerEmployee) {
                employeeAssignments.add(projet.getProjectID());
                employeeProjectAssignments.add(projet.getProjectID());
                System.out.println("Employee " + employeeID + " assigned to project " + projet.getProjectID());
            } else {
                System.out.println("Employee " + employeeID + " already assigned to maximum number of projects ("
                        + maxAssignmentsPerEmployee + ")");
            }
        } else {
            System.out.println("Unauthorized action. Access denied!");
        }
    }

    // Méthode pour obtenir les projets assignés à un employé
    public List<Integer> getProjetsAssignesAEmploye(int employeeID) {
        return rechercherEmployeParID(employeeID).getProjetsAssignes() ;
    }

    // Méthode pour supprimer l'affectation d'un employé à un projet par un admin
    public void supprimerAffectationEmploye(Admin admin, int employeeID, Projet projet) {
        if (admins.contains(admin)) {
            List<Integer> employeeAssignments = rechercherEmployeParID(employeeID).getProjetsAssignes();
            if (employeeAssignments.contains(projet.getProjectID())) {
                employeeAssignments.remove(projet.getProjectID());
                employeeProjectAssignments.add(employeeID);
                System.out.println("Assignment between employee " + employeeID + " and project " + projet.getProjectID()
                        + " removed.");
            } else {
                System.out.println("Employee " + employeeID + " is not assigned to project " + projet.getProjectID());
            }
        } else {
            System.out.println("Unauthorized action. Access denied!");
        }
    }

    // Méthode utilitaire pour calculer la différence en heures entre deux dates
    private double calculateHoursDifference(Date start, Date end) {
        long differenceInMillis = end.getTime() - start.getTime();
        return differenceInMillis / (1000.0 * 60.0 * 60.0);
    }

    // Admin login
    public Admin loginAdmin(String nomUtilisateur, String motDePasse) {
        for (Admin admin : admins) {
            if (admin.getNomUtilisateur().equals(nomUtilisateur) && admin.getMotDePasse().equals(motDePasse)) {
                return admin;
            }
        }
        return null;
    }

    // Employee login
    public Employe loginEmploye(int numeroIdentification, String motDePasse) {
        for (Employe employe : employes) {
            if (employe.getNumeroIdentification() == numeroIdentification
                    && employe.getMotDePasse().equals(motDePasse)) {
                return employe;
            }
        }
        return null;
    }

    // Admin menu
    public void afficherMenuAdmin() {

        // ... Afficher les options de menu pour l'administrateur, telles que :
        System.out.println("===== Admin Menu =====");
        System.out.println("1. Generate Global Status Report");
        System.out.println("2. Generate Project Status Report");
        System.out.println("3. Modify Projects");
        System.out.println("4. Modify Employees");
        System.out.println("5. Assign Employee to Project");
        System.out.println("6. Remove Employee Assignment from Project");
        System.out.println("7. Exit");
        System.out.println("======================");

    }

    // Méthode pour afficher le menu de l'employé
    // Méthode pour afficher le menu de l'employé
    public void afficherMenuEmploye() {
        System.out.println("===== Employee Menu =====");
        System.out.println("1. Generate My Project Report");
        System.out.println("2. Signal Work Hours");
        System.out.println("3. Exit");
    }


    // Méthode pour gérer les saisies du menu Admin
    public void gererSaisieMenuAdmin(Scanner scanner) throws ParseException {
        boolean quitter = false;
        while (!quitter) {
            afficherMenuAdmin();
            System.out.print("Entrez votre choix : ");
            int choix = scanner.nextInt();
            scanner.nextLine(); // Consommer le caractère de nouvelle ligne laissé par nextInt()

            switch (choix) {
                case 1:

                    gererAffichageEtatGlobal();
                    break;
                case 2:
                    gererAffichageEtatProjet(scanner);
                    break;
                case 3:
                    modifierProjets(scanner);
                    break;
                case 4:
                    modifierEmployes(scanner);
                    break;
                case 5:
                    gererAssignationEmployeProjet(scanner);
                    break;
                case 6:
                    gererSuppressionAssignationEmploye(scanner);
                    break;
                case 7:
                    quitter = true;
                    System.out.println("Merci d'utiliser TimeLog. Au revoir !");
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez entrer une option valide.");
                    break;
            }
        }
    }

    private void modifierProjets(Scanner scanner) {
        System.out.println("===== Modifier les Projets =====");
        System.out.print("Entrez l'identifiant du projet que vous souhaitez modifier : ");
        int projectID = scanner.nextInt();
        scanner.nextLine(); // Consommer le caractère de nouvelle ligne laissé par nextInt()

        Projet projet = rechercherProjetParID(projectID);
        if (projet != null) {
            System.out.println("Projet trouvé : " + projet.getName());
            System.out.print("Entrez le nouveau nom du projet : ");
            String newProjectName = scanner.nextLine();

            // Modifier le nom du projet
            projet.setName(newProjectName);
            System.out.println("Nom du projet modifié avec succès !");
        } else {
            System.out.println("Projet non trouvé !");
        }
    }

    // Méthode pour modifier les informations d'un employé
    private void modifierEmployes(Scanner scanner) throws ParseException {
        System.out.println("Veuillez entrer le numéro d'identification de l'employé");
        int numEmp = scanner.nextInt();
        Employe employeModication = employes.get(numEmp-1);
        System.out.println("Quelle modification souhaitez-vous faire? \n1. Modifier le nom d'usager\n2. Modifier le mot de passe");
        int choix = scanner.nextInt();
        if (choix == 1) {
            System.out.println("Veuillez entrer le nouveau nom d'usager");
            scanner.reset();
            String nom = scanner.next();
            employeModication.setNom(nom);
            System.out.println("Le nouveau nom d'usager est : "+employes.get(numEmp-1).getNom());
        } else if (choix==2) {
            System.out.println("Veuillez entrer le nouveau mot de passe");
            scanner.reset();
            String mdp = scanner.next();
            employeModication.setMotDePasse(mdp);
            System.out.println("Le nouveau mot de passe est : "+employes.get(numEmp-1).getMotDePasse());
        }

        else System.out.println("Ce choix n'est pas disponible");

    }

    // Méthode pour gérer les saisies du menu Employe
    // Méthode pour gérer les saisies du menu Employe
    public void gererSaisieMenuEmploye(Scanner scanner, SimpleDateFormat formatDateTime, Employe employe) {
        boolean quitter = false;
        while (!quitter) {
            afficherMenuEmploye();
            System.out.print("Entrez votre choix : ");
            int choix = scanner.nextInt();
            scanner.nextLine(); // Consommer le caractère de nouvelle ligne laissé par nextInt()

            switch (choix) {
                case 1:
                    gererAffichageEtatProjet(scanner, employe);
                    break;
                case 2:
                    signalerHeuresTravail(scanner, formatDateTime, employe); // Pass the formatDateTime object as a parameter
                    break;
                case 3:
                    quitter = true;
                    System.out.println("Merci d'utiliser TimeLog. Au revoir !");
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez entrer une option valide.");
                    break;
            }
        }
    }

    // Méthode pour gérer la saisie des heures de travail par un employé
    public void signalerHeuresTravail(Scanner scanner, SimpleDateFormat formatDateTime, Employe employe){
        System.out.println("===== Signaler les heures de travail =====");
        System.out.print("Entrez l'identifiant du projet : ");
        int projetID = scanner.nextInt();
        scanner.nextLine(); // Consommer le caractère de nouvelle ligne laissé par nextInt()

        // Check if the employee is assigned to the project
        List<Integer> projetsAssignes = employe.getProjetsAssignes();
        if (projetsAssignes == null) {
            System.out.println("Vous n'êtes assigné à aucun projet.");
            return;
        }

        if (!projetsAssignes.contains(projetID)) {
            System.out.println("Vous n'êtes pas assigné à ce projet.");
            return;
        }

        System.out.print("Entrez la date et heure de début du travail (format yyyy-MM-dd HH:mm) : ");
        String startDateStr = scanner.nextLine();
        System.out.print("Entrez la date et heure de fin du travail (format yyyy-MM-dd HH:mm) : ");
        String endDateStr = scanner.nextLine();

        try {
            Date startDate = formatDateTime.parse(startDateStr);
            Date endDate = formatDateTime.parse(endDateStr);

            // Signal the start and end of work
            signalerDebutTravailEmploye(employe, projetID, startDate);
            signalerFinTravailEmploye(employe, projetID, endDate);

            System.out.println("Heures de travail signalées avec succès !");
        } catch (ParseException e) {
            System.out.println("Erreur lors de la saisie des heures de travail : Format de date invalide.");
        }
    }

    // Méthode pour gérer l'ajout d'un Employé
    public void gererAjoutEmploye() throws ParseException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("===== Ajout d'un nouvel Employé =====");
        System.out.print("Entrez le nom de l'employé : ");
        String name = scanner.nextLine();
        System.out.print("Entrez l'ID de l'employé : ");
        int employeeID = scanner.nextInt();
        scanner.nextLine(); // Consommer le caractère de nouvelle ligne laissé par nextInt()

        System.out.print("Entrez le mot de passe de l'employé : ");
        String password = scanner.nextLine();

        System.out.print("Entrez la date d'embauche de l'employé (yyyy-MM-dd) : ");
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        String embauche = scanner.nextLine();
        Date dateEmbauche = formatDate.parse(embauche);
        scanner.reset();

        System.out.print("Entrez le numéro d'assurance social : ");
        String NAS = scanner.nextLine();
        scanner.reset();

        System.out.print("Entrez le taux horaire de l'employé : ");
        int tauxHoraire = scanner.nextInt();
        scanner.reset();

        System.out.print("Entrez le taux horaire supplémentaire de l'employé : ");
        int tauxSupp = scanner.nextInt();
        scanner.reset();
        scanner.nextLine();

        System.out.print("Entrez le poste de l'employé : ");
        String poste = scanner.nextLine();
        scanner.reset();

        List<Integer> list = new ArrayList<Integer>();

        Employe nouvelEmploye = new Employe(name, employeeID, password, dateEmbauche, NAS, tauxHoraire, tauxSupp, poste, list);
        ajouterEmploye(nouvelEmploye);

        System.out.println("Employé ajouté avec succès !");
    }

    // Méthode pour gérer l'affichage de l'état d'un projet spécifique pour un
    // employé
    public void gererAffichageEtatProjet(Scanner scanner, Employe employe) {
        System.out.println("===== Affichage de l'état d'un Projet =====");
        System.out.print("Entrez l'identifiant du projet : ");
        int projetID = scanner.nextInt();
        scanner.nextLine(); // Consommer le caractère de nouvelle ligne laissé par nextInt()

        // Vérifier si l'employé est assigné à ce projet
        List<Integer> projetsAssignes = getProjetsAssignesAEmploye(employe.getNumeroIdentification());
        boolean estAssigne = false;
        System.out.println("Taile = " + employe.getProjetsAssignes().size());
        for (int x=0;x<employe.getProjetsAssignes().size();x++) {
            System.out.println(employe.getProjetsAssignes().get(x));
            if (employe.getProjetsAssignes().get(x) == projetID) {
                estAssigne = true;
                break;
            }
        }

        if (estAssigne) {
            afficherRapportEtatProjet(projetID);
        } else {
            System.out.println("Vous n'êtes pas assigné à ce projet ou le projet n'existe pas.");
        }
    }

    // Méthode pour gérer l'ajout d'une Activité
    public void gererAjoutActivite(Scanner scanner) {
        System.out.println("===== Ajout d'une nouvelle Activité =====");
        System.out.print("Entrez la date de début de l'activité (format yyyy-MM-dd HH:mm) : ");
        String startDateStr = scanner.nextLine();
        System.out.print("Entrez la date de fin de l'activité (format yyyy-MM-dd HH:mm) : ");
        String endDateStr = scanner.nextLine();
        System.out.print("Entrez la discipline de l'activité : ");
        String discipline = scanner.nextLine();

        try {
            SimpleDateFormat formatDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date startDate = formatDateTime.parse(startDateStr);
            Date endDate = formatDateTime.parse(endDateStr);

            Activite nouvelleActivite = new Activite(startDate, endDate, discipline);
            ajouterActivite(nouvelleActivite);

            System.out.println("Activité ajoutée avec succès !");
        } catch (ParseException e) {
            System.out.println("Erreur lors de l'ajout de l'activité : Format de date invalide.");
        }
    }

    public void gererAffichageEtatProjet(Scanner scanner) {
        System.out.println("===== Affichage de l'état d'un Projet =====");
        System.out.print("Entrez l'identifiant du projet : ");
        int projetID = scanner.nextInt();
        scanner.nextLine(); // Consommer le caractère de nouvelle ligne laissé par nextInt()
        afficherRapportEtatProjet(projetID);
    }

    // Méthode pour gérer l'affichage de l'état global de tous les projets
    public void gererAffichageEtatGlobal() {
        System.out.println("===== Affichage de l'état global de tous les Projets =====");
        afficherRapportEtatGlobal();
    }

    public static void main(String[] args) throws ParseException {
        TimeLog timeLog = new TimeLog(5);
        timeLog.chargerDonneesDepuisFichier("C:\\Users\\olivi\\OneDrive\\Bureau\\Coding\\Mod-lisation-et-conception-orient-e-objet\\TimeLog\\src\\initial_data.json");
        // Declare and initialize the formatDateTime variable
        SimpleDateFormat formatDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Scanner scanner = new Scanner(System.in);

        System.out.println("===== Welcome to TimeLog Application =====");
        System.out.print("Enter your role (admin/employee): ");
        String role = scanner.nextLine();

        if (role.equalsIgnoreCase("admin")) {
            System.out.print("Enter your username: ");
            String adminUsername = scanner.nextLine();
            System.out.print("Enter your password: ");
            String adminPassword = scanner.nextLine();
            Admin loggedInAdmin = timeLog.loginAdmin(adminUsername, adminPassword);

            if (loggedInAdmin != null) {
                System.out.println("Login successful. Welcome, " + loggedInAdmin.getNomUtilisateur() + "!");
                timeLog.gererSaisieMenuAdmin(scanner);
            } else {
                System.out.println("Invalid username or password. Access denied!");
            }
        } else if (role.equalsIgnoreCase("employee")) {
            System.out.print("Enter your employee ID: ");
            int employeeID = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            System.out.print("Enter your password: ");
            String employeePassword = scanner.nextLine();
            Employe loggedInEmployee = timeLog.loginEmploye(employeeID, employeePassword);

            if (loggedInEmployee != null) {
                System.out.println("Login successful. Welcome, " + loggedInEmployee.getNom() + "!");
                timeLog.gererSaisieMenuEmploye(scanner, formatDateTime, loggedInEmployee);
            } else {
                System.out.println("Invalid employee ID or password. Access denied!");
            }
        } else {
            System.out.println("Invalid role. Please choose 'admin' or 'employee'.");
        }

        scanner.close();
    }
}
