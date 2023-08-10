package Progression.testcode.src;

import java.util.List;

public class employé {
    List<Integer> historiqueTauxHoraire;
    int tauxSupplementaire;
    int ID;
    String nom;
    String dateEmbauche;
    String dateDepart;
    int NAS;
    String poste;
    public void getHistoriqueTauxHoraire(){
        for(int x=0;x<historiqueTauxHoraire.size();x++){
            System.out.println(historiqueTauxHoraire.get(x));
        }
    }
    public void addTauxHoraire(int nouveauTaux){
        this.historiqueTauxHoraire.add(nouveauTaux);
    }
    public int getTauxSupplementaire(){
        return this.tauxSupplementaire;
    }
    public void setTauxSupplementaire(int nouveauTauxSupplementaire){
        this.tauxSupplementaire = nouveauTauxSupplementaire;
    }
    public int getID(){
        return this.ID;
    }
    public void setID(int newID){
        this.ID = newID;
    }
    public String getNom(){
        return this.nom;
    }
    public void setNom(String newName){
        this.nom = newName;
    }
    public String getDateEmbauche(){
        return this.dateEmbauche;
    }
    public void setDateEmbauche(String newDate){
        this.dateEmbauche= newDate;
    }
    public String getDateDepart(){
        return this.dateDepart;
    }
    public void setDateDepart(String depart){
        this.dateDepart=depart;
    }
    public int getNAS(){
        return this.NAS;
    }
    public void setNAS(int Num){
        this.NAS=Num;
    }
    public String getPoste(){
        return this.poste;
    }
    public void setPoste(String newPoste){
        this.poste=newPoste;
    }
}
