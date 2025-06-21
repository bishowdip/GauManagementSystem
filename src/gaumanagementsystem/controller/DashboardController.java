package gaumanagementsystem.controller;
import gaumanagementsystem.view.Service;
import gaumanagementsystem.controller.ServiceController;
import gaumanagementsystem.view.DashboardView;
import gaumanagementsystem.view.BugdgetAllocations;
import javax.swing.JOptionPane;

/**
 * Dashboard Controller - Handles navigation between different modules
 * Follows MVC pattern with proper separation of concerns
 * @author bisho
 */
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

    /**
     * Initialize controller and set up event handlers
     */
    private void initController() {
        view.getServiceButton().addActionListener(e -> openServiceModule());
        view.getBudgetButton().addActionListener(e -> openBudgetModule());
        view.getComplaintsButton().addActionListener(e -> openComplaintsModule());
        view.getProjectsButton().addActionListener(e -> openProjectsModule());
        view.getNewsButton().addActionListener(e -> openNewsModule());
        view.getCitizensButton().addActionListener(e -> openCitizensModule());
        view.getLogoutButton().addActionListener(e -> logout());
        
        System.out.println("Dashboard Controller initialized for user role: " + userRole);
    }

    /**
     * Open Service module
     */
    private void openServiceModule() {
        try {
            System.out.println("Opening Service module...");
            Service serviceView = new Service();
            serviceView.setVisible(true);
            view.dispose();
        } catch (Exception e) {
            handleNavigationError("Service", e);
        }
    }

    /**
     * Open Budget Allocations module
     */
    private void openBudgetModule() {
        try {
            System.out.println("Opening Budget Allocations module...");
            BugdgetAllocations budgetView = new BugdgetAllocations();
            budgetView.setVisible(true);
            view.dispose();
            System.out.println("Budget Allocations module opened successfully!");
        } catch (Exception e) {
            handleNavigationError("Budget Allocations", e);
        }
    }

    /**
     * Open Complaints module
     */
    private void openComplaintsModule() {
        try {
            System.out.println("Opening Complaints module...");
            gaumanagementsystem.view.Complaints_Tables complaintsTableView = 
                new gaumanagementsystem.view.Complaints_Tables();
            complaintsTableView.setVisible(true);
            view.dispose();
        } catch (Exception e) {
            handleNavigationError("Complaints", e);
        }
    }

    /**
     * Open Projects module
     */
    private void openProjectsModule() {
        try {
            System.out.println("Opening Projects module...");
            gaumanagementsystem.view.ProjectRequests projectsView = 
                new gaumanagementsystem.view.ProjectRequests();
            projectsView.setVisible(true);
            view.dispose();
        } catch (Exception e) {
            handleNavigationError("Projects", e);
        }
    }

    /**
     * Open News and Notice module
     */
    private void openNewsModule() {
        try {
            System.out.println("Opening News & Notice module...");
            gaumanagementsystem.view.NewsAndNotice newsView = 
                new gaumanagementsystem.view.NewsAndNotice(userRole);
            newsView.setVisible(true);
            view.dispose();
        } catch (Exception e) {
            handleNavigationError("News & Notice", e);
        }
    }

    /**
     * Open Citizens module
     */
    private void openCitizensModule() {
        try {
            System.out.println("Opening Citizens module...");
            gaumanagementsystem.view.CitizenEdit citizensView = 
                new gaumanagementsystem.view.CitizenEdit();
            citizensView.setVisible(true);
            view.dispose();
        } catch (Exception e) {
            handleNavigationError("Citizens", e);
        }
    }

    /**
     * Handle logout action
     */
    private void logout() {
        try {
            System.out.println("User logging out...");
            int confirm = JOptionPane.showConfirmDialog(
                view,
                "Are you sure you want to logout?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
            
            if (confirm == JOptionPane.YES_OPTION) {
                view.dispose();
                System.out.println("User logged out successfully.");
                
                // Optionally show login view here
                // LoginView loginView = new LoginView();
                // loginView.setVisible(true);
            }
        } catch (Exception e) {
            System.err.println("Error during logout: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Handle navigation errors with user-friendly messages
     */
    private void handleNavigationError(String moduleName, Exception e) {
        System.err.println("Error opening " + moduleName + " module: " + e.getMessage());
        e.printStackTrace();
        
        JOptionPane.showMessageDialog(
            view,
            "Unable to open " + moduleName + " module.\nError: " + e.getMessage(),
            "Navigation Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
    
    /**
     * Get current user role
     */
    public String getUserRole() {
        return userRole;
    }
    
    /**
     * Set user role
     */
    public void setUserRole(String userRole) {
        this.userRole = userRole;
        System.out.println("User role updated to: " + userRole);
    }
}
