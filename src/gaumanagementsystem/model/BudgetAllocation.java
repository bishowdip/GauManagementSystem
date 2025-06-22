/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gaumanagementsystem.model;

/**
 *
 * @author bishodip
 */
public class BudgetAllocation {
    private String category;
    private Double totalAmount;
    private Integer projectCount;

    // Default constructor
    public BudgetAllocation() {
    }

    // Constructor with fields
    public BudgetAllocation(String category, Double totalAmount, Integer projectCount) {
        this.category = category;
        this.totalAmount = totalAmount;
        this.projectCount = projectCount;
    }

    // Getters
    public String getCategory() { return category; }
    public Double getTotalAmount() { return totalAmount; }
    public Integer getProjectCount() { return projectCount; }

    // Setters
    public void setCategory(String category) { this.category = category; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }
    public void setProjectCount(Integer projectCount) { this.projectCount = projectCount; }

    // Convert to Object array for table display
    public Object[] toTableRow() {
        return new Object[]{category, totalAmount, projectCount};
    }

    @Override
    public String toString() {
        return "BudgetAllocation{" +
                "category='" + category + '\'' +
                ", totalAmount=" + totalAmount +
                ", projectCount=" + projectCount +
                '}';
    }
} 