/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gaumanagementsystem.model;

/**
 *
 * @author ASUS
 */
public class NewsAndNotice {
    private String fiscalYear;
    private String allocatedAmount;
    private String usedAmount;
    private String status;
    private String remarks;

    public NewsAndNotice(String fiscalYear, String allocatedAmount, String usedAmount, String status, String remarks) {
        this.fiscalYear = fiscalYear;
        this.allocatedAmount = allocatedAmount;
        this.usedAmount = usedAmount;
        this.status = status;
        this.remarks = remarks;
    }

    // Getters and Setters
    public String getFiscalYear() { return fiscalYear; }
    public void setFiscalYear(String fiscalYear) { this.fiscalYear = fiscalYear; }

    public String getAllocatedAmount() { return allocatedAmount; }
    public void setAllocatedAmount(String allocatedAmount) { this.allocatedAmount = allocatedAmount; }

    public String getUsedAmount() { return usedAmount; }
    public void setUsedAmount(String usedAmount) { this.usedAmount = usedAmount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

   
    public Object[] toObjectArray() {
        return new Object[]{fiscalYear, allocatedAmount, usedAmount, status, remarks};
    }
}
