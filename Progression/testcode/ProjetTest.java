package Progression.testcode;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ProjetTest {
    private int projetID;
    private String name;
    private Date startDate;
    private Date endDate;
    private Map<String, Double> budgetedHoursByDiscipline; // Discipline -> Budgeted Hours

    /*public Project(int projectID, String name, Date startDate, Date endDate) {
        this.projectID = projectID;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budgetedHoursByDiscipline = new HashMap<>();
    }*/

    // Getters and Setters for projectID, name, startDate, and endDate

    public void addBudgetedHours(String discipline, double budgetedHours) {
        budgetedHoursByDiscipline.put(discipline, budgetedHours);
    }

    public double getBudgetedHoursByDiscipline(String discipline) {
        return budgetedHoursByDiscipline.getOrDefault(discipline, 0.0);
    }

    public Map<String, Double> getBudgetedHoursByDiscipline() {
        return budgetedHoursByDiscipline;
    }

    public double calculatePercentageCompletionByDiscipline(String discipline, double workedHours) {
        double budgetedHours = getBudgetedHoursByDiscipline(discipline);
        if (budgetedHours == 0) {
            return 0.0;
        }
        return (workedHours / budgetedHours) * 100.0;
    }

}
