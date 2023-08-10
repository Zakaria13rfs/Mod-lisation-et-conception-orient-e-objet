import java.util.Date;

public class Activite {
    private Date heureDebut;
    private Date heureFin;
    private String discipline;
    private double heuresTravaillees;

    public Activite(Date heureDebut, Date heureFin, String discipline) {
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.discipline = discipline;
        this.heuresTravaillees = 0.0;
    }

    // Getters et Setters pour heureDebut, heureFin, discipline et heuresTravaillees

    public Date getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(Date heureDebut) {
        this.heureDebut = heureDebut;
    }

    public Date getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(Date heureFin) {
        this.heureFin = heureFin;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public double getHeuresTravaillees() {
        return heuresTravaillees;
    }

    public void setHeuresTravaillees(double heuresTravaillees) {
        this.heuresTravaillees = heuresTravaillees;
    }
}