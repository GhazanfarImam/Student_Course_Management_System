
package com.studentapp.service;

import com.studentapp.model.Student;
import com.studentapp.util.DBConnection;
import com.studentapp.util.DBConnection;

import java.sql.*;

public class StudentService {

    public void registerStudent(Student student) {
        String sql = " INSERT INTO students (student_name, email, phone) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, student.getStudentName());
            ps.setString(2, student.getEmail());
            ps.setString(3, student.getPhone());

            if (ps.executeUpdate() > 0) {
                System.out.println("Student registered.");
            }

        } catch (SQLException e) {
            System.out.println("Registration failed: "
                    + e.getMessage());
        }
    }

    public void viewAllStudents() {
        String sql = "SELECT * FROM students ORDER BY student_id";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                printStudent(rs);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void viewStudentById(int id) {
        String sql = "SELECT * FROM students WHERE student_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    printStudent(rs);
                } else {
                    System.out.println("Student not found.");
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateStudent(int id, String name,
                              String email, String phone) {
        String sql = " UPDATE students SET student_name = ?, email = ?, phone = ? WHERE student_id = ? ";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setInt(4, id);

            int rows = ps.executeUpdate();
            System.out.println(rows > 0
                    ? "Student updated."
                    : "Student not found.");

        } catch (SQLException e) {
            System.out.println("Update failed: " + e.getMessage());
        }
    }

    public void deleteStudent(int id) {
        String sql = "DELETE FROM students WHERE student_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rows = ps.executeUpdate();

            System.out.println(rows > 0
                    ? "Student deleted."
                    : "Student not found.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void printStudent(ResultSet rs)
            throws SQLException {
        System.out.printf(
                "ID: %d | Name: %s | Email: %s | Phone: %s%n",
                rs.getInt("student_id"),
                rs.getString("student_name"),
                rs.getString("email"),
                rs.getString("phone")
        );
    }
}
