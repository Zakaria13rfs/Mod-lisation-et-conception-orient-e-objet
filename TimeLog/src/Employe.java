import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class Employe {
    private String nom;
    private int numeroIdentification;
    private String motDePasse;
    private Date dateEmbauche;
    private Date dateDepart;
    private String numeroAssuranceSocial;
    private String poste;
    private List<Integer> projetsAssignes;
    private Map<Integer, Map<String, Double>> heuresTravail;
    private int tauxHoraireDeBase;
    private int tauxSupplementaire;


    public Employe(String nom, int numeroIdentification, String motDePasse, Date dateEmbauche, String numeroAssuranceSocial, int tauxHoraireDeBase, int tauxSupplementaire,
                   String poste, List<Integer> projetsAssignes) {
        this.nom = nom;
        this.numeroIdentification = numeroIdentification;
        this.dateEmbauche = dateEmbauche;
        this.dateDepart = null;
        this.numeroAssuranceSocial = numeroAssuranceSocial;
        this.poste = poste;
        this.projetsAssignes = projetsAssignes; // != null ? new ArrayList<>(projetsAssignes) : new ArrayList<>();
        this.heuresTravail = new HashMap<>();
        this.tauxHoraireDeBase = tauxHoraireDeBase;
        this.tauxSupplementaire = tauxSupplementaire;
        this.motDePasse = motDePasse;
    }

   /* public Employe(String nom, int numeroIdentification, String motDePasse) {
        this.nom = nom;
        this.numeroIdentification = numeroIdentification;
        this.motDePasse = motDePasse;
        this.projetsAssignes = null;
        this.heuresTravail = new HashMap<>();
    }*/

    // Getters et Setters pour nom, numeroIdentification, dateEmbauche, dateDepart,
    // numeroAssuranceSocial et poste

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNumeroIdentification() {
        return numeroIdentification;
    }

    public void setNumeroIdentification(int numeroIdentification) {
        this.numeroIdentification = numeroIdentification;
    }

    public Date getDateEmbauche() {
        return dateEmbauche;
    }

    public void setDateEmbauche(Date dateEmbauche) {
        this.dateEmbauche = dateEmbauche;
    }

    public Date getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(Date dateDepart) {
        this.dateDepart = dateDepart;
    }

    public String getNumeroAssuranceSocial() {
        return numeroAssuranceSocial;
    }

    public void setNumeroAssuranceSocial(String numeroAssuranceSocial) {
        this.numeroAssuranceSocial = numeroAssuranceSocial;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public List<Integer> getProjetsAssignes() {
        return projetsAssignes;
    }

    public void setProjetsAssignes(List<Integer> projetsAssignes) {
        this.projetsAssignes = projetsAssignes;
    }

    public Map<Integer, Map<String, Double>> getHeuresTravail() {
        return heuresTravail;
    }

    public void setHeuresTravail(Map<Integer, Map<String, Double>> heuresTravail) {
        this.heuresTravail = heuresTravail;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public String setMotDePasse(String mdp){
       return this.motDePasse=mdp;
    }
    // Method to record the start of work
    public void signalerDebutTravail(int projetID, Date startDate) {
        Map<String, Double> travaux = heuresTravail.getOrDefault(projetID, new HashMap<>());
        travaux.put("startDate", (double) startDate.getTime());
        heuresTravail.put(projetID, travaux);
    }

    // Method to record the end of work and calculate worked hours
    public void signalerFinTravail(int projetID, Date endDate) {
        Map<String, Double> travaux = heuresTravail.getOrDefault(projetID, new HashMap<>());
        if (travaux.containsKey("startDate")) {
            double start = travaux.get("startDate");
            double end = (double) endDate.getTime();
            double workedHours = (end - start) / (1000.0 * 60.0 * 60.0); // Convert milliseconds to hours
            travaux.put("hoursWorked", workedHours);
            heuresTravail.put(projetID, travaux);
        } else {
            System.out.println("Please signal the start of work before signaling the end.");
        }
    }

    // Method to get the hours worked for a specific project
    public double getHoursWorkedForProject(int projetID) {
        Map<String, Double> travaux = heuresTravail.getOrDefault(projetID, new HashMap<>());
        return travaux.getOrDefault("hoursWorked", 0.0);
    }
}