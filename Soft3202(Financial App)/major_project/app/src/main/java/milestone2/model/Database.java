package milestone2.model;


import java.io.File;
import java.sql.*;
import java.util.ArrayList;

public class Database {
    private static final String dbName = "test.db";
    private static final String dbURL = "jdbc:sqlite:" + dbName;

    /**
     * Creates a new database file
     */
    public static void createDB() {
        File dbFile = new File(dbName);
        if (dbFile.exists()) {
            System.out.println("Database already created");
            return;
        }
        try (Connection ignored = DriverManager.getConnection(dbURL)) {
            System.out.println("A new database has been created.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    /**
     * Removes the database file
     */
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

    /**
     * Sets up the database tables
     */
    public static void setupDB() {
        String CapitalExpenditures =
                """
                        CREATE TABLE IF NOT EXISTS  Details(
                            id integer PRIMARY KEY,
                            ConmpanyName text NOT NULL,
                            CapitalExpenditure BIGINT,
                            DividendPayout BIGINT,
                            NetIncome BIGINT,
                            OperatingCashflow BIGINT,
                            ProfitLoss BIGINT,
                            Date_year text NOT NULL
                        );
                        """;

        String Transalated_Data =
                """
                        CREATE TABLE IF NOT EXISTS  Translated_Data(
                            id integer PRIMARY KEY,
                            Language_name text NOT NULL,
                            page text NOT NULL,
                            Context text NOT NULL
                        );
                        """;

        try (Connection conn = DriverManager.getConnection(dbURL);
             Statement statement = conn.createStatement()) {
            statement.execute(CapitalExpenditures);
            statement.execute(Transalated_Data);
            System.out.println("Created tables");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }


    }

    public void add_cache(String year, long ce, long dp, long ni, long oc, long pl,String companyName) {
        String addSingleStudentWithParametersSQL =
                """
                        INSERT INTO Details(ConmpanyName,CapitalExpenditure,DividendPayout,NetIncome,OperatingCashflow,ProfitLoss,Date_year) VALUES
                            (?, ?, ?, ?, ?, ?, ?)
                        """;
        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement preparedStatement =
                     conn.prepareStatement(addSingleStudentWithParametersSQL)) {
            preparedStatement.setString(1, companyName);
            preparedStatement.setLong(2, ce);
            preparedStatement.setLong(3, dp);
            preparedStatement.setLong(4, ni);
            preparedStatement.setLong(5, oc);
            preparedStatement.setLong(6, pl);
            preparedStatement.setString(7, year);
            preparedStatement.executeUpdate();
            System.out.println("Added questionable data");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    /**
     * @param CompanyName
     * @return ArrayList<Long>
     * This method returns the list of Capital Expenditure for which the company has data
     */
    public ArrayList<Long> SelectCapitalExpenditure(String CompanyName) {
        ArrayList<Long> CapitalExpenditures = new ArrayList<>();
        String studentRangeSQL =
                """
                        SELECT CapitalExpenditure
                        FROM Details WHERE ConmpanyName = ?
                        """;
        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement preparedStatement =
                     conn.prepareStatement(studentRangeSQL)) {
            preparedStatement.setString(1, CompanyName);

            ResultSet results = preparedStatement.executeQuery();

            while (results.next()) {
                CapitalExpenditures.add(results.getLong("CapitalExpenditure"));
            }
            System.out.println("Finished simple query");
            return CapitalExpenditures;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
            return null;
        }
    }

    /**
     * @param CompanyName
     * @return ArrayList<Long>
     * This method returns the list of Dividend Payout for which the company has data
     */
    public ArrayList<Long> SelectDividendPayout(String CompanyName) {
        ArrayList<Long> DividendPayout = new ArrayList<>();
        String studentRangeSQL =
                """
                        SELECT DividendPayout
                        FROM Details WHERE ConmpanyName = ?
                        """;
        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement preparedStatement =
                     conn.prepareStatement(studentRangeSQL)) {
            preparedStatement.setString(1, CompanyName);

            ResultSet results = preparedStatement.executeQuery();

            while (results.next()) {
                DividendPayout.add(results.getLong("DividendPayout"));
            }
            System.out.println("Finished simple query");
            return DividendPayout;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
            return null;
        }
    }


    /**
     * @param CompanyName
     * @return ArrayList<Long>
     * This method returns the list of Net Income for which the company has data
     */
    public ArrayList<Long> SelectOperatingCashflow(String CompanyName) {
        ArrayList<Long> OperatingCashflow = new ArrayList<>();
        String studentRangeSQL =
                """
                        SELECT OperatingCashflow
                        FROM Details WHERE ConmpanyName = ?
                        """;
        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement preparedStatement =
                     conn.prepareStatement(studentRangeSQL)) {
            preparedStatement.setString(1, CompanyName);

            ResultSet results = preparedStatement.executeQuery();

            while (results.next()) {
                OperatingCashflow.add(results.getLong("OperatingCashflow"));
            }
            System.out.println("Finished simple query");
            return OperatingCashflow;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
            return null;
        }
    }

    /**
     * @param CompanyName
     * @return ArrayList<Long>
     * This method returns the list of Net Income for which the company has data
     */
    public ArrayList<Long> SelectNetIncome(String CompanyName) {
        ArrayList<Long> NetIncome = new ArrayList<>();
        String studentRangeSQL =
                """
                        SELECT NetIncome
                        FROM Details WHERE ConmpanyName = ?
                        """;
        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement preparedStatement =
                     conn.prepareStatement(studentRangeSQL)) {
            preparedStatement.setString(1, CompanyName);

            ResultSet results = preparedStatement.executeQuery();

            while (results.next()) {
                NetIncome.add(results.getLong("NetIncome"));
            }
            System.out.println("Finished simple query");
            return NetIncome;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
            return null;
        }
    }

    /**
     * @param CompanyName
     * @return ArrayList<Long>
     * This method returns the list of Profit Loss for which the company has data
     */
    public ArrayList<Long> SelectProfitLoss(String CompanyName) {
        ArrayList<Long> ProfitLoss = new ArrayList<>();
        String studentRangeSQL =
                """
                        SELECT ProfitLoss
                        FROM Details WHERE ConmpanyName = ?
                        """;
        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement preparedStatement =
                     conn.prepareStatement(studentRangeSQL)) {
            preparedStatement.setString(1, CompanyName);

            ResultSet results = preparedStatement.executeQuery();

            while (results.next()) {
                ProfitLoss.add(results.getLong("ProfitLoss"));
            }
            System.out.println("Finished simple query");
            return ProfitLoss;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
            return null;
        }
    }

    /**
     * @param CompanyName
     * @return ArrayList<String>
     * This method returns the list of Date for which the company has data
     */
    public ArrayList<String> SelectDate(String CompanyName) {
        ArrayList<String> Date_year = new ArrayList<>();
        String studentRangeSQL =
                """
                        SELECT Date_year
                        FROM Details WHERE ConmpanyName = ?
                        """;
        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement preparedStatement =
                     conn.prepareStatement(studentRangeSQL)) {
            preparedStatement.setString(1, CompanyName);

            ResultSet results = preparedStatement.executeQuery();

            while (results.next()) {
                Date_year.add(results.getString("Date_year"));
            }
            System.out.println("Finished simple query");
            return Date_year;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
            return null;
        }
    }


    public static void main(String[] args) {
//        removeDB();
//        createDB();
//        setupDB();
//        addDataFromQuestionableSource("New", "Student", 110.0);
//        queryDataSimple(65.0, 75.0);
//        queryDataWithJoin("SOFT3202");
    }

    /**
     * @param companySymbol
     * This method Delete the company data from the database
     */
    public void DeleteSpecificCompany(String companySymbol) {
        String updateSQL =
                """
                        DELETE FROM Details
                        WHERE  ConmpanyName = ?
                        """;
        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement preparedStatement =
                     conn.prepareStatement(updateSQL)) {
            preparedStatement.setString(1, companySymbol);
            preparedStatement.executeUpdate();
            System.out.println("Finished update query");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    /**
     * This method Delete all the data from the database
     */
    public void delete_cache() {
        String deleteSQL =
                """
                        DELETE FROM Details
                """;
        try (Connection conn = DriverManager.getConnection(dbURL);
             Statement statement = conn.createStatement()) {
            statement.executeUpdate(deleteSQL);
            System.out.println("Finished delete query");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }






}
