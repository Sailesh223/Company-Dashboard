package SaveUser;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/SaveUser")
public class SaveUser extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form data
        String action = request.getParameter("action"); 
        String email = request.getParameter("email"); 
        
        try {
            // Database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/json", "root", "1234");
            
            if ("save".equals(action)) {
                String cuisine = request.getParameter("cuisine");
                String title = request.getParameter("title");
                String prep_time = request.getParameter("prep_time");
                String total_time = request.getParameter("total_time");
                
                // Insert query
                String query = "Select*from Recipe where prep_time=15";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, cuisine);
                ps.setString(2, title);
                ps.setString(3, prep_time);
                ps.setString(4, total_time);


                // Execute query
                int result = ps.executeUpdate();
                if (result > 0) {
                    response.sendRedirect("about.html"); 
                } else {
                    response.getWriter().println("Failed to save user details.");
                }
                ps.close();
            } else if ("delete".equals(action)) {
                // Delete query
                String query = "DELETE FROM users WHERE email = ?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, email);
                
                // Execute query
                int result = ps.executeUpdate();
                if (result > 0) {
                    response.sendRedirect("dashboard.html"); 
                } else {
                    response.getWriter().println("Failed to delete user details. User not found.");
                }
                ps.close();
            }
            
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
