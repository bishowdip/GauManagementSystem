package gaumanagementsystem.controller;
import gaumanagementsystem.view.Service;
import gaumanagementsystem.controller.ServiceController;
import gaumanagementsystem.view.DashboardView;
import gaumanagementsystem.view.BugdgetAllocations;
import javax.swing.JOptionPane;

/**
 *
 * @author bishodip
 */
public class DashboardController {

    private DashboardView view;
    private String userRole;
    private String currentUserId;

    public DashboardController(DashboardView view) {
        this(view, "admin", null); // Default to admin for backward compatibility
    }

    public DashboardController(DashboardView view, String userRole) {
        this(view, userRole, null); // Backward compatibility
    }

    public DashboardController(DashboardView view, String userRole, String currentUserId) {
        this.view = view;
        this.userRole = userRole;
        this.currentUserId = currentUserId;
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
            Service serviceView = new Service(userRole, currentUserId);
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
            BugdgetAllocations budgetView = new BugdgetAllocations(userRole, currentUserId);
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
                new gaumanagementsystem.view.Complaints_Tables(userRole, currentUserId);
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
                new gaumanagementsystem.view.ProjectRequests(userRole);
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
     * Open Citizens module - role-based functionality
     * Admin: Opens CitizenEdit to manage all citizens
     * User: Opens ProfileView to view/edit their own profile
     */
    private void openCitizensModule() {
        try {
            if ("user".equalsIgnoreCase(userRole)) {
                System.out.println("Opening My Profile for user...");
                
                // Try to find user's existing profile data using email
                gaumanagementsystem.controller.CitizenController citizenController = 
                    new gaumanagementsystem.controller.CitizenController();
                
                gaumanagementsystem.model.CitizenData existingProfile = null;
                String userEmail = view.getUserEmail(); // Get email from dashboard view
                
                // First, try to find profile by email (most reliable method)
                if (userEmail != null && !userEmail.trim().isEmpty()) {
                    existingProfile = citizenController.getCitizenByEmail(userEmail);
                    System.out.println("Searching for profile with email: " + userEmail);
                }
                
                // Fallback: try to find profile by currentUserId if email search fails
                if (existingProfile == null && currentUserId != null && !currentUserId.trim().isEmpty()) {
                    existingProfile = citizenController.getCitizenById(currentUserId);
                    System.out.println("Fallback: Searching for profile with ID: " + currentUserId);
                }
                
                // If profile found, open ProfileView with existing data
                if (existingProfile != null) {
                    System.out.println("Found existing profile for user: " + existingProfile.getName());
                    gaumanagementsystem.view.ProfileView profileView = 
                        new gaumanagementsystem.view.ProfileView(existingProfile.getCitizenId(), true, userRole);
                    profileView.setVisible(true);
                } else {
                    // No existing profile found, open EditProfileView to create new profile
                    System.out.println("No existing profile found. Opening profile creation form...");
                    javax.swing.JOptionPane.showMessageDialog(
                        view,
                        "Welcome! It looks like you haven't created your profile yet.\n" +
                        "Please fill in your details to create your profile.",
                        "Create Your Profile",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE
                    );
                    gaumanagementsystem.view.EditProfileView editProfileView = 
                        new gaumanagementsystem.view.EditProfileView("user", currentUserId, false);
                    // TODO: Pre-fill the email field with the logged-in user's email
                    // editProfileView.setUserEmail(userEmail);
                    editProfileView.setVisible(true);
                }
            } else {
                System.out.println("Opening Citizens module...");
                // For admin role, open CitizenEdit to manage all citizens
                gaumanagementsystem.view.CitizenEdit citizensView = 
                    new gaumanagementsystem.view.CitizenEdit(userRole, currentUserId);
                citizensView.setVisible(true);
            }
            view.dispose();
        } catch (Exception e) {
            handleNavigationError(
                "user".equalsIgnoreCase(userRole) ? "My Profile" : "Citizens", 
                e
            );
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
