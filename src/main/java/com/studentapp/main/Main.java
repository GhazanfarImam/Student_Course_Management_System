
package com.studentapp.main;

import com.studentapp.model.Student;
import com.studentapp.service.*;
import com.studentapp.util.DBConnection;
import com.studentapp.util.DBConnection;

import java.math.BigDecimal;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        StudentService students = new StudentService();
        CourseService courses = new CourseService();
        EnrollmentService enrollments = new EnrollmentService();
        ReportService reports = new ReportService();

        try {
            boolean running = true;

            while (running) {
                System.out.println("\n===== STUDENT COURSE REGISTRATION =====");
                System.out.println("1. Register Student");
                System.out.println("2. Add Course");
                System.out.println("3. Enroll Student");
                System.out.println("4. View All Students");
                System.out.println("5. View All Courses");
                System.out.println("6. View Student by ID");
                System.out.println("7. Display Students in a Course");
                System.out.println("8. List Student's Courses (Procedure)");
                System.out.println("9. Student-Course JOIN Report");
                System.out.println("10. Update Student");
                System.out.println("11. Delete Student");
                System.out.println("12. Update Course");
                System.out.println("13. Delete Course");
                System.out.println("14. Add Courses in Batch");
                System.out.println("15. Exit");

                try {
                    System.out.print("Enter choice: ");
                    int choice = Integer.parseInt(sc.nextLine());

                    switch (choice) {
                        case 1:
                            System.out.print("Student name: ");
                            String name = sc.nextLine();

                            System.out.print("Email: ");
                            String email = sc.nextLine();

                            System.out.print("Phone: ");
                            String phone = sc.nextLine();

                            students.registerStudent(
                                    new Student(name, email, phone)
                            );
                            break;

                        case 2:
                            System.out.print("Course name: ");
                            String courseName = sc.nextLine();

                            System.out.print("Duration in months: ");
                            int duration = Integer.parseInt(sc.nextLine());

                            System.out.print("Fee: ");
                            BigDecimal fee =
                                    new BigDecimal(sc.nextLine());

                            courses.addCourse(courseName, duration, fee);
                            break;

                        case 3:
                            System.out.print("Student ID: ");
                            int sid = Integer.parseInt(sc.nextLine());

                            System.out.print("Course ID: ");
                            int cid = Integer.parseInt(sc.nextLine());

                            enrollments.enrollStudent(sid, cid);
                            break;

                        case 4:
                            students.viewAllStudents();
                            break;

                        case 5:
                            courses.viewAllCourses();
                            break;

                        case 6:
                            System.out.print("Student ID: ");
                            students.viewStudentById(
                                    Integer.parseInt(sc.nextLine())
                            );
                            break;

                        case 7:
                            System.out.print("Course ID: ");
                            enrollments.displayStudentsInCourse(
                                    Integer.parseInt(sc.nextLine())
                            );
                            break;

                        case 8:
                            System.out.print("Student ID: ");
                            reports.displayStudentCourses(
                                    Integer.parseInt(sc.nextLine())
                            );
                            break;

                        case 9:
                            reports.displayStudentCourseReport();
                            break;

                        case 10:
                            System.out.print("Student ID: ");
                            int updateSid =
                                    Integer.parseInt(sc.nextLine());

                            System.out.print("New name: ");
                            String newName = sc.nextLine();

                            System.out.print("New email: ");
                            String newEmail = sc.nextLine();

                            System.out.print("New phone: ");
                            String newPhone = sc.nextLine();

                            students.updateStudent(
                                    updateSid, newName, newEmail, newPhone
                            );
                            break;

                        case 11:
                            System.out.print("Student ID: ");
                            students.deleteStudent(
                                    Integer.parseInt(sc.nextLine())
                            );
                            break;

                        case 12:
                            System.out.print("Course ID: ");
                            int updateCid =
                                    Integer.parseInt(sc.nextLine());

                            System.out.print("New course name: ");
                            String newCourseName = sc.nextLine();

                            System.out.print("New duration: ");
                            int newDuration =
                                    Integer.parseInt(sc.nextLine());

                            System.out.print("New fee: ");
                            BigDecimal newFee =
                                    new BigDecimal(sc.nextLine());

                            courses.updateCourse(
                                    updateCid, newCourseName,
                                    newDuration, newFee
                            );
                            break;

                        case 13:
                            System.out.print("Course ID: ");
                            courses.deleteCourse(
                                    Integer.parseInt(sc.nextLine())
                            );
                            break;

                        case 14:
                            courses.addCoursesBatch();
                            break;

                        case 15:
                            running = false;
                            System.out.println("Application closed.");
                            break;

                        default:
                            System.out.println("Invalid choice.");
                    }

                } catch (NumberFormatException e) {
                    System.out.println(
                            "Invalid number. Please try again."
                    );
                }
            }

        } finally {
            sc.close();
            DBConnection.closePool();
        }
    }
}
