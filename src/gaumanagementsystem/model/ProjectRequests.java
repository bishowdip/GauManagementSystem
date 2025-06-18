/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gaumanagementsystem.model;

/**
 *
 * @author SONIC
 */
public class ProjectRequests {
    private String requestId;
    private String projectName;
    private String startedDate;
    private String ward;
    private String expectedToEnd;
    private String description;
    private String status;
    private String budget;
    public ProjectRequestsString requestId, String projectName, String startedDate, String ward,String expectedToEnd, String description, String status, String budget) {
        this.requestId = requestId;
        this.projectName = projectName;
        this.startedDate = startedDate;
        this.ward = ward;
        this.expectedToEnd = expectedToEnd;
        this.description = description;
        this.status = status;
        this.budget = budget;
    }
}

