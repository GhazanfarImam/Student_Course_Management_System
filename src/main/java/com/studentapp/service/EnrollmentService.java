
package com.studentapp.service;

import com.studentapp.util.DBConnection;
import java.sql.*;

public class EnrollmentService {

    public void enrollStudent(int studentId, int courseId) {
        String sql = " INSERT INTO enrollments (student_id, course_id, enrollment_date) VALUES (?, ?, CURRENT_DATE) ";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, studentId);
                ps.setInt(2, courseId);

                ps.executeUpdate();
                con.commit();

                System.out.println("Enrollment successful.");

            } catch (SQLException e) {
                con.rollback();
                System.out.println(
                        "Enrollment failed; transaction rolled back: "
                                + e.getMessage()
                );
            } finally {
                con.setAutoCommit(true);
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    public void displayStudentsInCourse(int courseId) {
        String sql = "SELECT s.student_id, s.student_name, s.email,e.enrollment_date FROM students s JOIN enrollments e ON s.student_id = e.student_id WHERE e.course_id = ? ORDER BY s.student_name ";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, courseId);

            try (ResultSet rs = ps.executeQuery()) {
                boolean found = false;

                while (rs.next()) {
                    found = true;
                    System.out.printf(
                            "ID: %d | Name: %s | Email: %s | Enrolled: %s%n",
                            rs.getInt("student_id"),
                            rs.getString("student_name"),
                            rs.getString("email"),
                            rs.getDate("enrollment_date")
                    );
                }

                if (!found) {
                    System.out.println("No students enrolled in this course.");
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

