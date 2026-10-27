package com.studentapp.util;




import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {

    private static final HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(
                "jdbc:postgresql://localhost:5432/student_course_db?escapeSyntaxCallMode=callIfNoReturn"
        );
        config.setUsername("postgres");
        config.setPassword("root");

        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);

        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection()
            throws SQLException {
        return dataSource.getConnection();
    }

    public static void closePool() {
        if (!dataSource.isClosed()) {
            dataSource.close();
        }
    }
}
