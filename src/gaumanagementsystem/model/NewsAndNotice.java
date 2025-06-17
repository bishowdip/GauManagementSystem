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
    private String date;
    private String audience;
    private String subject;
    private String description;
    private String expiryDate;

    public NewsAndNotice(String date, String audience, String subject, String description, String expiryDate) {
        this.date = date;
        this.audience = audience;
        this.subject = subject;
        this.description = description;
        this.expiryDate = expiryDate;
    }

    // Getters
    public String getDate() { return date; }
    public String getAudience() { return audience; }
    public String getSubject() { return subject; }
    public String getDescription() { return description; }
    public String getExpiryDate() { return expiryDate; }

    // Setters
    public void setDate(String date) { this.date = date; }
    public void setAudience(String audience) { this.audience = audience; }
    public void setSubject(String subject) { this.subject = subject; }
    public void setDescription(String description) { this.description = description; }
    public void setExpiryDate(String expiryDate) { this.expiryDate = expiryDate; }

    // Convert to Object array for table display
    public Object[] toTableRow() {
        return new Object[]{date, audience, subject, description, expiryDate};
    }

    private void filterTable(String search, String audience) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        List<NewsAndNotice> filteredList = controller.search(search, audience);
        for (NewsAndNotice notice : filteredList) {
            model.addRow(notice.toTableRow());
        }
    }
}
