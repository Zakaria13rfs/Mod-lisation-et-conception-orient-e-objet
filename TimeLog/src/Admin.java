import java.util.List;

public class Admin {
    private String nomUtilisateur;
    private String motDePasse;
    private boolean estAdmin;
    private List<Employe> employesGeres;

    public Admin(String nomUtilisateur, String motDePasse) {
        this.nomUtilisateur = nomUtilisateur;
        this.motDePasse = motDePasse;
        this.estAdmin = true;
        this.employesGeres = null;
    }

    // Getters et Setters pour nomUtilisateur, motDePasse, estAdmin et employesGeres

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public boolean estAdmin() {
        return estAdmin;
    }

    public void setAdmin(boolean estAdmin) {
        this.estAdmin = estAdmin;
    }

    public List<Employe> getEmployesGeres() {
        return employesGeres;
    }

    public void setEmployesGeres(List<Employe> employesGeres) {
        this.employesGeres = employesGeres;
    }
}