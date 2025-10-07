import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminService {

    public static void viewAllRequests() throws SQLException {
        String sql = "SELECT * FROM requests";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            System.out.println("--- All Blood Requests ---");
            while (rs.next()) {
                System.out.printf("Request ID: %d, Patient ID: %d, Donor ID: %d, Status: %s, Date: %s%n",
                        rs.getInt("request_id"),
                        rs.getInt("patient_id"),
                        rs.getInt("donor_id"),
                        rs.getString("status"),
                        rs.getString("request_date")
                );
            }
        }
    }

    public static void viewAllDonors() throws SQLException {
        String sql = "SELECT * FROM donors";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            System.out.println("--- All Donors ---");
            while (rs.next()) {
                System.out.printf("ID: %d, Name: %s, Blood Group: %s, Location: %s%n",
                        rs.getInt("donor_id"),
                        rs.getString("name"),
                        rs.getString("blood_group"),
                        rs.getString("location")
                );
            }
        }
    }

    public static void viewAllPatients() throws SQLException {
        String sql = "SELECT * FROM patients";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            System.out.println("--- All Patients ---");
            while (rs.next()) {
                System.out.printf("ID: %d, Name: %s, Blood Group: %s, Location: %s%n",
                        rs.getInt("patient_id"),
                        rs.getString("name"),
                        rs.getString("blood_group"),
                        rs.getString("location")
                );
            }
        }
    }
}