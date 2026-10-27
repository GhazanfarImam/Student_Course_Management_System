
package com.studentapp.service;

import com.studentapp.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportService {

    // Report: students and their courses
    public void displayStudentCourseReport() {
        String sql = "SELECT s.student_id, s.student_name, s.email, c.course_name, e.enrollment_date FROM students s JOIN enrollments e ON s.student_id = e.student_id JOIN courses c ON e.course_id = c.course_id ORDER BY s.student_name, c.course_name ";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                System.out.printf(
                        "Student ID: %d | Student: %s | Email: %s | Course: %s | Date: %s%n",
                        rs.getInt("student_id"),
                        rs.getString("student_name"),
                        rs.getString("email"),
                        rs.getString("course_name"),
                        rs.getDate("enrollment_date")
                );
            }

        } catch (SQLException e) {
            System.out.println("Report failed: " + e.getMessage());
        }
    }

    // PostgreSQL stored procedure using refcursor


    public void displayStudentCourses(int studentId) {

        String cursorName = "student_courses";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try {
                String callSql =
                        "CALL get_student_courses(?, CAST(? AS refcursor))";

                try (PreparedStatement ps = con.prepareStatement(callSql)) {
                    ps.setInt(1, studentId);
                    ps.setString(2, cursorName);
                    ps.execute();
                }

                try (Statement st = con.createStatement();
                     ResultSet rs = st.executeQuery(
                             "FETCH ALL FROM " + cursorName)) {

                    boolean found = false;

                    while (rs.next()) {
                        found = true;

                        System.out.println(
                                "Student ID: " + rs.getInt("student_id"));
                        System.out.println(
                                "Student Name: " + rs.getString("student_name"));
                        System.out.println(
                                "Course ID: " + rs.getInt("course_id"));
                        System.out.println(
                                "Course Name: " + rs.getString("course_name"));
                        System.out.println(
                                "Enrollment Date: " +
                                        rs.getDate("enrollment_date"));
                        System.out.println("----------------------");
                    }

                    if (!found) {
                        System.out.println(
                                "No enrolled courses found for student ID "
                                        + studentId);
                    }
                }

                con.commit();

            } catch (SQLException e) {
                con.rollback();
                throw e;
            } finally {
                con.setAutoCommit(true);
            }

        } catch (SQLException e) {
            System.out.println(
                    "Stored procedure call failed: " + e.getMessage());
        }
    }


}
