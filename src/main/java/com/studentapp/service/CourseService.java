
package com.studentapp.service;

import com.studentapp.util.DBConnection;

import java.sql.*;
import java.math.BigDecimal;

public class CourseService {

    public void addCourse(String name, int duration,
                          BigDecimal fee) {
        String sql = "INSERT INTO courses (course_name, duration_months, fee) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setInt(2, duration);
            ps.setBigDecimal(3, fee);

            ps.executeUpdate();
            System.out.println("Course added.");

        } catch (SQLException e) {
            System.out.println("Add course failed: " + e.getMessage());
        }
    }

    public void viewAllCourses() {
        String sql = "SELECT * FROM courses ORDER BY course_id";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                System.out.printf(
                        "ID: %d | Course: %s | Duration: %d months | Fee: %s%n",
                        rs.getInt("course_id"),
                        rs.getString("course_name"),
                        rs.getInt("duration_months"),
                        rs.getBigDecimal("fee")
                );
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateCourse(int id, String name,
                             int duration, BigDecimal fee) {
        String sql = " UPDATE courses SET course_name = ?, duration_months = ?, fee = ? WHERE course_id = ? ";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setInt(2, duration);
            ps.setBigDecimal(3, fee);
            ps.setInt(4, id);

            System.out.println(ps.executeUpdate() > 0
                    ? "Course updated."
                    : "Course not found.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteCourse(int id) {
        String sql = "DELETE FROM courses WHERE course_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            System.out.println(ps.executeUpdate() > 0
                    ? "Course deleted."
                    : "Course not found.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    public void addCoursesBatch() {
        String sql1 = "INSERT INTO courses (course_name, duration_months, fee) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sql1)) {
                ps.setString(1, "Core Java");
                ps.setInt(2, 3);
                ps.setBigDecimal(3, new java.math.BigDecimal("8000"));
                ps.addBatch();

                ps.setString(1, "PostgreSQL");
                ps.setInt(2, 2);
                ps.setBigDecimal(3, new java.math.BigDecimal("5000"));
                ps.addBatch();

                ps.executeBatch();
                con.commit();

                System.out.println("Courses added successfully.");

            } catch (SQLException e) {
                con.rollback();
                System.out.println("Batch failed: " + e.getMessage());
            } finally {
                con.setAutoCommit(true);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
