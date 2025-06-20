package gaumanagementsystem.controller;
import gaumanagementsystem.view.Service;
import gaumanagementsystem.controller.ServiceController;
import gaumanagementsystem.view.DashboardView;

public class DashboardController {

    private DashboardView view;
    private String userRole;

    public DashboardController(DashboardView view) {
        this(view, "admin"); // Default to admin for backward compatibility
    }

    public DashboardController(DashboardView view, String userRole) {
        this.view = view;
        this.userRole = userRole;
        initController();
    }

    private void initController() {
        view.getServiceButton().addActionListener(e -> openServiceModule());
        view.getBudgetButton().addActionListener(e -> openBudgetModule());
        view.getComplaintsButton().addActionListener(e -> openComplaintsModule());
        view.getProjectsButton().addActionListener(e -> openProjectsModule());
        view.getNewsButton().addActionListener(e -> openNewsModule());
        view.getCitizensButton().addActionListener(e -> openCitizensModule());
        view.getLogoutButton().addActionListener(e -> logout());
    }

    private void openServiceModule() {
        Service serviceView = new Service();
        serviceView.setVisible(true);
        view.dispose();
    }

    private void openBudgetModule() {
        javax.swing.JOptionPane.showMessageDialog(view, "Budget module is under construction.", "Info", javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    private void openComplaintsModule() {
        gaumanagementsystem.view.Complaints_Tables complaintsTableView = new gaumanagementsystem.view.Complaints_Tables();
        complaintsTableView.setVisible(true);
        view.dispose();
    }

    private void openProjectsModule() {
        gaumanagementsystem.view.ProjectRequests projectsView = new gaumanagementsystem.view.ProjectRequests();
        projectsView.setVisible(true);
        view.dispose();
    }

    private void openNewsModule() {
        gaumanagementsystem.view.NewsAndNotice newsView = new gaumanagementsystem.view.NewsAndNotice(userRole);
        newsView.setVisible(true);
        view.dispose();
    }

    private void openCitizensModule() {
        gaumanagementsystem.view.CitizenEdit citizensView = new gaumanagementsystem.view.CitizenEdit();
        citizensView.setVisible(true);
        view.dispose();
    }

    private void logout() {
        view.dispose();
        // Optionally, show login view here
    }
}
