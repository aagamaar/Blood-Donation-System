import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static int currentUserId = -1;
    private static String currentUserType = null;

    public static void main(String[] args) {
        System.out.println("Welcome to the Blood Donation System");
        while (true) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Admin Login");
            System.out.println("2. Patient Access");
            System.out.println("3. Donor Access");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    handleAdminLogin();
                    break;
                case "2":
                    handlePatientAccess();
                    break;
                case "3":
                    handleDonorAccess();
                    break;
                case "4":
                    System.out.println("Exiting application. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void handleAdminLogin() {
        try {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            int userId = AuthService.login(username, password, "admin");
            if (userId != -1) {
                currentUserId = userId;
                currentUserType = "admin";
                System.out.println("Login successful!");
                showUserMenu();
            } else {
                System.out.println("Login failed. Invalid credentials.");
            }
        } catch (SQLException e) {
            System.out.println("A database error occurred: " + e.getMessage());
        }
    }

    private static void handlePatientAccess() {
        System.out.println("\n--- Patient Access ---");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.print("Enter choice: ");
        String choice = scanner.nextLine();

        if ("1".equals(choice)) {
            performLogin("patient");
        } else if ("2".equals(choice)) {
            registerPatient();
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private static void handleDonorAccess() {
        System.out.println("\n--- Donor Access ---");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.print("Enter choice: ");
        String choice = scanner.nextLine();

        if ("1".equals(choice)) {
            performLogin("donor");
        } else if ("2".equals(choice)) {
            registerDonor();
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private static void performLogin(String userType) {
        try {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            int userId = AuthService.login(username, password, userType);
            if (userId != -1) {
                currentUserId = userId;
                currentUserType = userType;
                System.out.println("Login successful!");
                showUserMenu();
            } else {
                System.out.println("Login failed. Invalid credentials.");
            }
        } catch (SQLException e) {
            System.out.println("A database error occurred: " + e.getMessage());
        }
    }

    private static void registerPatient() {
        try {
            System.out.println("\n--- Patient Registration ---");
            System.out.print("Enter new username: ");
            String username = scanner.nextLine();
            System.out.print("Create a password: ");
            String password = scanner.nextLine();
            System.out.print("Enter your name: ");
            String name = scanner.nextLine();
            System.out.print("Enter your blood group (e.g., A+): ");
            String bloodGroup = scanner.nextLine();
            System.out.print("Enter your location: ");
            String location = scanner.nextLine();

            if (AuthService.registerPatient(username, password, name, bloodGroup, location)) {
                System.out.println("✅ Registration successful! You can now log in.");
            } else {
                System.out.println("❌ Registration failed. Username might already exist.");
            }
        } catch (SQLException e) {
            System.out.println("A database error occurred: " + e.getMessage());
        }
    }

    private static void registerDonor() {
        try {
            System.out.println("\n--- Donor Registration ---");
            System.out.print("Enter new username: ");
            String username = scanner.nextLine();
            System.out.print("Create a password: ");
            String password = scanner.nextLine();
            System.out.print("Enter your name: ");
            String name = scanner.nextLine();
            System.out.print("Enter your age: ");
            int age = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            System.out.print("Enter your blood group (e.g., A+): ");
            String bloodGroup = scanner.nextLine();
            System.out.print("Enter your location: ");
            String location = scanner.nextLine();
            System.out.print("Enter your contact info (email/phone): ");
            String contactInfo = scanner.nextLine();
            System.out.print("Enter your health status: ");
            String healthStatus = scanner.nextLine();

            if (AuthService.registerDonor(username, password, name, age, bloodGroup, location, contactInfo, healthStatus)) {
                System.out.println("✅ Registration successful! You can now log in.");
            } else {
                System.out.println("❌ Registration failed. Username might already exist.");
            }
        } catch (SQLException e) {
            System.out.println("A database error occurred: " + e.getMessage());
        }
    }


    private static void showUserMenu() {
        switch (currentUserType) {
            case "admin":
                adminMenu();
                break;
            case "patient":
                patientMenu();
                break;
            case "donor":
                donorMenu();
                break;
        }
    }

    private static void adminMenu() {
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. View All Donors");
            System.out.println("2. View All Patients");
            System.out.println("3. View All Blood Requests");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1":
                        AdminService.viewAllDonors();
                        break;
                    case "2":
                        AdminService.viewAllPatients();
                        break;
                    case "3":
                        AdminService.viewAllRequests();
                        break;
                    case "4":
                        System.out.println("Logged out successfully.");
                        return;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (SQLException e) {
                System.out.println("A database error occurred: " + e.getMessage());
            }
        }
    }

    private static void patientMenu() {
        while (true) {
            System.out.println("\n--- Patient Menu ---");
            System.out.println("1. Search Donors");
            System.out.println("2. Send Request");
            System.out.println("3. Logout");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1":
                        System.out.print("Enter blood group (e.g., A+): ");
                        String bloodGroup = scanner.nextLine();
                        System.out.print("Enter location: ");
                        String location = scanner.nextLine();
                        List<String> donors = PatientService.searchDonors(bloodGroup, location);
                        if (donors.isEmpty()) {
                            System.out.println("No donors found with the specified criteria.");
                        } else {
                            System.out.println("\n--- Available Donors ---");
                            donors.forEach(System.out::println);
                        }
                        break;
                    case "2":
                        System.out.print("Enter the Donor ID you want to send a request to: ");
                        int donorId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        if (PatientService.sendRequest(currentUserId, donorId)) {
                            System.out.println("Request sent successfully!");
                            PatientService.createNotificationForDonor(donorId, currentUserId);
                        } else {
                            System.out.println("Failed to send request. Donor ID might be invalid.");
                        }
                        break;
                    case "3":
                        System.out.println("Logged out successfully.");
                        return;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (SQLException e) {
                System.out.println("A database error occurred: " + e.getMessage());
            }
        }
    }

    private static void donorMenu() {
        while (true) {
            System.out.println("\n--- Donor Menu ---");
            System.out.println("1. View Pending Requests");
            System.out.println("2. Respond to Request");
            System.out.println("3. Logout");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1":
                        List<String> requests = DonorService.getPendingRequests(currentUserId);
                        if (requests.isEmpty()) {
                            System.out.println("You have no pending requests.");
                        } else {
                            System.out.println("\n--- Pending Requests ---");
                            requests.forEach(System.out::println);
                        }
                        break;
                    case "2":
                        System.out.print("Enter Request ID to respond to: ");
                        int requestId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        System.out.print("Enter new status ('Accepted' or 'Rejected'): ");
                        String status = scanner.nextLine();

                        if (DonorService.updateRequestStatus(requestId, status)) {
                            System.out.println("Request status updated successfully.");
                            // You can add logic here to notify the patient
                        } else {
                            System.out.println("Failed to update request status. Request ID might be invalid.");
                        }
                        break;
                    case "3":
                        System.out.println("Logged out successfully.");
                        return;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (SQLException e) {
                System.out.println("A database error occurred: " + e.getMessage());
            }
        }
    }
}