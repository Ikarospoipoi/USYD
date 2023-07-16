package au.edu.sydney.soft3202.task3.database;

import java.io.File;
import java.sql.*;

public class Database {
    private static int id = 1;
    private static final String dbName = "test.db";
    private static final String dbURL = "jdbc:sqlite:" + dbName;

    public static void createDB() {
        File dbFile = new File(dbName);
        if (dbFile.exists()) {
            System.out.println("Database already created");
            return;
        }
        try (Connection ignored = DriverManager.getConnection(dbURL)) {
            // If we get here that means no exception raised from getConnection - meaning it worked
            System.out.println("A new database has been created.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    public static void removeDB() {
        File dbFile = new File(dbName);
        if (dbFile.exists()) {
            boolean result = dbFile.delete();
            if (!result) {
                System.out.println("Couldn't delete existing db file");
                System.exit(-1);
            } else {
                System.out.println("Removed existing DB file.");
            }
        } else {
            System.out.println("No existing DB file.");
        }
    }

    public static void setupDB() {
        String serialise_dataTableSQL =
                """
                CREATE TABLE IF NOT EXISTS Save  (
                    serialise_data text NOT NULL,
                    GameName text NOT NULL,
                    users text NOT NULL
                );
                """;

        String createUnitTableSQL =
                """
                CREATE TABLE IF NOT EXISTS Users (
                    id integer PRIMARY KEY,
                    username text NOT NULL
                );
                """;

        try (Connection conn = DriverManager.getConnection(dbURL);
             Statement statement = conn.createStatement()) {
            statement.execute(serialise_dataTableSQL);
            statement.execute(createUnitTableSQL);

            System.out.println("Created tables");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }


    public static void addData(String serialise_data, String gameName, String users) {
        String addData =
                """
                INSERT INTO Save(serialise_data,GameName, users) VALUES
                    (?, ?,?)
                """;

        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement preparedStatement = conn.prepareStatement(addData)) {
            preparedStatement.setString(1, serialise_data);
            preparedStatement.setString(2, gameName);
            preparedStatement.setString(3, users);
            preparedStatement.executeUpdate();

            System.out.println("Added questionable data");
        } catch (SQLException e) {
            
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    public static void addUser(String name) {
        String addData =
                """
                INSERT INTO Users(id, username) VALUES
                    (?, ?)
                """;

        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement preparedStatement = conn.prepareStatement(addData)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.executeUpdate();
            id++;
            System.out.println("Added user");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    public static String select_data(String name, String gameName) {
        String studentRangeSQL =
                """
                SELECT serialise_data
                FROM Save
                where users = ? and gameName = ?
                """;

        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement preparedStatement = conn.prepareStatement(studentRangeSQL)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, gameName);
            ResultSet results = preparedStatement.executeQuery();

            while (results.next()) {
                System.out.println(results.getString("serialise_data"));
                return results.getString("serialise_data");
            }

            System.out.println("Finished simple query");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        return "Something went wrong";
    }

    public static String SelectData(String name, String gameName) {
        String enrolmentSQL =
                """
                SELECT serialise_data
                FROM Users AS U
                INNER JOIN Save AS S ON U.username = S.users
                where U.username = ? and S.gameName = ?
                """;

        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement preparedStatement = conn.prepareStatement(enrolmentSQL)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, gameName);
            ResultSet results = preparedStatement.executeQuery();

            while (results.next()) {
                System.out.println(results.getString("serialise_data"));
                return results.getString("serialise_data");
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        return "Something went wrong";
    }

    public static boolean checkGameName( String gameName) {
        String enrolmentSQL =
                """
                SELECT serialise_data
                FROM Users AS U
                INNER JOIN Save AS S ON U.username = S.users
                where S.gameName = ?
                """;

        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement preparedStatement = conn.prepareStatement(enrolmentSQL)) {
            preparedStatement.setString(1, gameName);
            ResultSet results = preparedStatement.executeQuery();

            while (results.next()) {
                System.out.println(results.getString("serialise_data"));
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    

    public static void main(String[] args) {
        removeDB();
        createDB();
        setupDB();

    }

    public static void deleteGame(String result) {
        String enrolmentSQL =
                """
                DELETE FROM Save
                WHERE gameName = ?
                """;

        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement preparedStatement = conn.prepareStatement(enrolmentSQL)) {
            preparedStatement.setString(1, result);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
}

