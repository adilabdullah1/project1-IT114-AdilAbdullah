package com.example.listprocessor;

import java.text.DecimalFormat;

public class Employee {
    // Data members
    private String name;
    private String id;
    private double salary;
    private String office;
    private String extension;
    private double performanceRating;
    private int startYear;

    // Constants for bonuses
    public static final double BONUS_THRESHOLD_HIGH = 3.5;
    public static final double BONUS_THRESHOLD_LOW = 2.0;
    public static final double BONUS_PERCENT_HIGH = 0.05; // 5%
    public static final double BONUS_PERCENT_LOW  = 0.02; // 2%

    public Employee() { }

    public Employee(String name, String id, double salary, String office,
                    String extension, double performanceRating, int startYear) {
        this.name = name;
        this.id = id;
        this.salary = salary;
        this.office = office;
        this.extension = extension;
        this.performanceRating = performanceRating;
        this.startYear = startYear;
    }

    // Accessors & mutators
    public String getName() { return name; }
    public void setName(String s) { name = s; }

    public String getId() { return id; }
    public void setId(String s) { id = s; }

    public double getSalary() { return salary; }
    public void setSalary(double d) { salary = d; }

    public String getOffice() { return office; }
    public void setOffice(String s) { office = s; }

    public String getExtension() { return extension; }
    public void setExtension(String s) { extension = s; }

    public double getPerformanceRating() { return performanceRating; }
    public void setPerformanceRating(double d) { performanceRating = d; }

    public int getStartYear() { return startYear; }
    public void setStartYear(int y) { startYear = y; }

    // compute bonus
    public double getBonus() {
        if (performanceRating > BONUS_THRESHOLD_HIGH) {
            return salary * BONUS_PERCENT_HIGH;
        } else if (performanceRating >= BONUS_THRESHOLD_LOW && performanceRating <= BONUS_THRESHOLD_HIGH) {
            return salary * BONUS_PERCENT_LOW;
        } else {
            return 0.0;
        }
    }

    // years of service (current year retrieved from DateUtil)
    public int getYearsService() {
        int currentYear = DateUtil.get_current_year();
        return currentYear - startYear;
    }

    // helper to show basic line: name, id, startYear
    public String summaryLine() {
        return name + ", " + id + ", " + startYear;
    }

    // pretty print details
    public String details() {
        StringBuilder sb = new StringBuilder();
        DecimalFormat df = new DecimalFormat("#,##0.00");
        sb.append("Name: ").append(name).append("\n");
        sb.append("ID: ").append(id).append("\n");
        sb.append("Salary: $").append(df.format(salary)).append("\n");
        sb.append("Office: ").append(office).append("\n");
        sb.append("Extension: ").append(extension).append("\n");
        sb.append("Performance Rating: ").append(performanceRating).append("\n");
        double bonus = getBonus();
        if (bonus > 0.0) {
            sb.append("Bonus: $").append(df.format(bonus)).append("\n");
        }
        int yrs = getYearsService();
        if (yrs > 0) {
            sb.append("Years of Service: ").append(yrs).append("\n");
        } else {
            sb.append("Years of Service: new employee\n");
        }
        return sb.toString();
    }
}
