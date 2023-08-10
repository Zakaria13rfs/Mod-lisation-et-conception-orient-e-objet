import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Projet {
    private int projetID;
    private String name;
    private Date startDate;
    private Date endDate;
    private Map<String, Long> budgetedHoursByDiscipline; // Discipline -> Budgeted Hours
    private Map<String, Long> workedHoursByDiscipline; // Discipline -> Worked Hours

    public Projet(int projetID, String name, Date startDate, Date endDate, HashMap<String, Long> budget) {
        this.projetID = projetID;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budgetedHoursByDiscipline = budget;
        this.workedHoursByDiscipline = new HashMap<>();
    }

    // Getters and Setters for projectID, name, startDate, and endDate

    public void addBudgetedHours(String discipline, long budgetedHours) {
        budgetedHoursByDiscipline.put(discipline, budgetedHours);
    }

    public double getBudgetedHoursByDiscipline(String discipline) {
        return budgetedHoursByDiscipline.getOrDefault(discipline, 0L);
    }

    public void addWorkedHours(String discipline, long workedHours) {
        workedHoursByDiscipline.put(discipline, workedHours);
    }

    public double getWorkedHoursByDiscipline(String discipline) {
        return workedHoursByDiscipline.getOrDefault(discipline, 0L);
    }

    public Map<String, Long> getBudgetedHoursByDiscipline() {
        return budgetedHoursByDiscipline;
    }

    public Map<String, Long> getWorkedHoursByDiscipline() {
        return workedHoursByDiscipline;
    }

    public double calculatePercentageCompletionByDiscipline(String discipline) {
        double budgetedHours = getBudgetedHoursByDiscipline(discipline);
        double workedHours = getWorkedHoursByDiscipline(discipline);
        if (budgetedHours == 0) {
            return 0.0;
        }
        return (workedHours / budgetedHours) * 100.0;
    }

    public int getProjectID() {
        return projetID;
    }

    public String getName() {
        return name;
    }

    public void setName(String nom){
        this.name = nom;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}