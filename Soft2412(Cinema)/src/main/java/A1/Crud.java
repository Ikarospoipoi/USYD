package A1;
import java.sql.*;

public class Crud{

	// Previously called card_valid
	public static int cardExists(String card_number) {
		Connection conn = null;
		ResultSet resultSet = null;
		int result = 0;
		try {
			// Connect to DB
			conn = DriverManager.getConnection("jdbc:sqlserver://soft2412.cyg3iolyiokd.ap-southeast-2.rds.amazonaws.com:1433;", "admin", "gr0up!wo");
			Statement statement = conn.createStatement();
			// Create and Run Query
			String card_exists_query = "Select COUNT(*) FROM xyzBank.dbo.customers WHERE card_number LIKE '" + card_number + "'";
			resultSet = statement.executeQuery(card_exists_query);
			// Print Result
			while(resultSet.next()) {
				int i = Integer.parseInt(resultSet.getString(1));
				result = i;
			}
		}
		catch (SQLException e){
			throw new Error("Problem", e);
		}finally{
			try{
				if (conn!=null){
					conn.close();
				}
			} catch (SQLException ex){
				System.out.println(ex.getMessage());
			}
		}
		return result;
	}

	public static CardStatus getCardStatus(String card_number){
		Connection conn = null;
		ResultSet resultSet = null;
		String result = null;
		try {
			// Connect to DB
			conn = DriverManager.getConnection("jdbc:sqlserver://soft2412.cyg3iolyiokd.ap-southeast-2.rds.amazonaws.com:1433;", "admin", "gr0up!wo");
			Statement statement = conn.createStatement();
			// Create and Run Query
			String card_status_query = "Select card_status FROM xyzBank.dbo.customers WHERE card_number LIKE '" + card_number + "'";
			resultSet = statement.executeQuery(card_status_query);
			// Print Result
			while(resultSet.next()) {
				result = (resultSet.getString(1));
			}
		}
		catch (SQLException e){
			throw new Error("Problem", e);
		}finally{
			try{
				if (conn!=null){
					conn.close();
				}
			} catch (SQLException ex){
				System.out.println(ex.getMessage());
			}
		}
		result = result.toUpperCase();
		// Return a CardStatus enum
		if (result.equals("ACTIVE")) {
			return CardStatus.ACTIVE;
		} else if (result.equals("BLOCKED")) {
			return CardStatus.BLOCKED;
		} else if (result.equals("LOST")) {
			return CardStatus.LOST;
		} else if (result.equals("STOLEN")) {
			return CardStatus.STOLEN;
		}
		return null;

	}

	public static boolean isCardAdmin(String card_number){
		Connection conn = null;
		ResultSet resultSet = null;
		boolean result = false;
		try {
			// Connect to DB
			conn = DriverManager.getConnection("jdbc:sqlserver://soft2412.cyg3iolyiokd.ap-southeast-2.rds.amazonaws.com:1433;", "admin", "gr0up!wo");
			Statement statement = conn.createStatement();
			// Create and Run Query
			String is_admin_query = "Select Is_Admin FROM xyzBank.dbo.customers WHERE card_number LIKE '" + card_number + "'";
			resultSet = statement.executeQuery(is_admin_query);
			// Print Result
			while(resultSet.next()) {
				int i = Integer.parseInt(resultSet.getString(1));
				if(i==1){
					result = true;
				}
				else{
					result = false;
				}
			}
		}
		catch (SQLException e){
			throw new Error("Problem", e);
		}finally{
			try{
				if (conn!=null){
					conn.close();
				}
			} catch (SQLException ex){
				System.out.println(ex.getMessage());
			}
		}
		return result;

	}

	public static float availableFunds(String card_number){
		Connection conn = null;
		ResultSet resultSet = null;
		float result = 0;
		try {
			// Connect to DB
			conn = DriverManager.getConnection("jdbc:sqlserver://soft2412.cyg3iolyiokd.ap-southeast-2.rds.amazonaws.com:1433;", "admin", "gr0up!wo");
			Statement statement = conn.createStatement();
			// Create and Run Query
			String available_funds_query = "Select available_funds FROM xyzBank.dbo.customers WHERE card_number LIKE '" + card_number + "'";
			resultSet = statement.executeQuery(available_funds_query);
			// Print Result
			while(resultSet.next()) {
				result = Float.valueOf(resultSet.getString(1));

			}
		}
		catch (SQLException e){
			throw new Error("Problem", e);
		}finally{
			try{
				if (conn!=null){
					conn.close();
				}
			} catch (SQLException ex){
				System.out.println(ex.getMessage());
			}
		}
		return result;

	}

	public static String getCardPin(String card_number){
		Connection conn = null;
		ResultSet resultSet = null;
		String result = null;
		try {
			// Connect to DB
			conn = DriverManager.getConnection("jdbc:sqlserver://soft2412.cyg3iolyiokd.ap-southeast-2.rds.amazonaws.com:1433;", "admin", "gr0up!wo");
			Statement statement = conn.createStatement();
			// Create and Run Query
			String get_card_pin_query = "Select pin FROM xyzBank.dbo.customers WHERE card_number LIKE '" + card_number + "'";
			resultSet = statement.executeQuery(get_card_pin_query);
			// Print Result
			while(resultSet.next()) {
				result = (resultSet.getString(1));

			}
		}
		catch (SQLException e){
			throw new Error("Problem", e);
		}finally{
			try{
				if (conn!=null){
					conn.close();
				}
			} catch (SQLException ex){
				System.out.println(ex.getMessage());
			}
		}
		return result;

	}


	public static String getCardStartDate(String card_number){
		Connection conn = null;
		ResultSet resultSet = null;
		String result = null;
		try {
			// Connect to DB
			conn = DriverManager.getConnection("jdbc:sqlserver://soft2412.cyg3iolyiokd.ap-southeast-2.rds.amazonaws.com:1433;", "admin", "gr0up!wo");
			Statement statement = conn.createStatement();
			// Create and Run Query
			String get_card_start_date_query = "Select start_date FROM xyzBank.dbo.customers WHERE card_number LIKE '" + card_number + "'";
			resultSet = statement.executeQuery(get_card_start_date_query);
			// Print Result
			while(resultSet.next()) {
				result = (resultSet.getString(1));

			}
		}
		catch (SQLException e){
			throw new Error("Problem", e);
		}finally{
			try{
				if (conn!=null){
					conn.close();
				}
			} catch (SQLException ex){
				System.out.println(ex.getMessage());
			}
		}
		return result;

	}

	public static String getCardExpiryDate(String card_number){
		Connection conn = null;
		ResultSet resultSet = null;
		String result = null;
		try {
			// Connect to DB
			conn = DriverManager.getConnection("jdbc:sqlserver://soft2412.cyg3iolyiokd.ap-southeast-2.rds.amazonaws.com:1433;", "admin", "gr0up!wo");
			Statement statement = conn.createStatement();
			// Create and Run Query
			String get_card_exp_date_query = "Select exp_date FROM xyzBank.dbo.customers WHERE card_number LIKE '" + card_number + "'";
			resultSet = statement.executeQuery(get_card_exp_date_query);
			// Print Result
			while(resultSet.next()) {
				result = (resultSet.getString(1));

			}
		}
		catch (SQLException e){
			throw new Error("Problem", e);
		}finally{
			try{
				if (conn!=null){
					conn.close();
				}
			} catch (SQLException ex){
				System.out.println(ex.getMessage());
			}
		}
		return result;

	}

	public static float getATMBalance(String atmId){
		Connection conn = null;
		ResultSet resultSet = null;
		float result = 0;
		try {
			// Connect to DB
			conn = DriverManager.getConnection("jdbc:sqlserver://soft2412.cyg3iolyiokd.ap-southeast-2.rds.amazonaws.com:1433;", "admin", "gr0up!wo");
			Statement statement = conn.createStatement();
			// Create and Run Query
			String get_balance_query = "Select atm_balance FROM xyzBank.dbo.atm WHERE atm_id = " + atmId;
			resultSet = statement.executeQuery(get_balance_query);
			// Print Result
			while(resultSet.next()) {
				result = Float.valueOf(resultSet.getString(1));

			}
		}
		catch (SQLException e){
			throw new Error("Problem", e);
		}finally{
			try{
				if (conn!=null){
					conn.close();
				}
			} catch (SQLException ex){
				System.out.println(ex.getMessage());
			}
		}
		return result;
	}

	public static void updateATMBalance(String atmId, double adjustBy){
		Connection conn = null;

		try {
			// Connect to DB
			conn = DriverManager.getConnection("jdbc:sqlserver://soft2412.cyg3iolyiokd.ap-southeast-2.rds.amazonaws.com:1433;", "admin", "gr0up!wo");
			Statement statement = conn.createStatement();
			// Write Query and Update Database
			String update_query = "UPDATE xyzBank.dbo.atm SET atm_balance = " + adjustBy + " WHERE atm_id = " + atmId;
			statement.executeUpdate(update_query);

		}
		catch (SQLException e){
			throw new Error("Problem", e);
		}finally{
			try{
				if (conn!=null){
					conn.close();
				}
			} catch (SQLException ex){
				System.out.println(ex.getMessage());
			}
		}


	}

	public static int rowsinTransactionTable(){
		Connection conn = null;
		ResultSet resultSet = null;
		int no_rows = 0;
		try {
			// Connect to DB
			conn = DriverManager.getConnection("jdbc:sqlserver://soft2412.cyg3iolyiokd.ap-southeast-2.rds.amazonaws.com:1433;", "admin", "gr0up!wo");
			Statement statement = conn.createStatement();
			// Create and Run Query
			String get_lastTransactionID = "Select count(*) FROM xyzBank.dbo.transactions";
			resultSet = statement.executeQuery(get_lastTransactionID);
			// Print Result
			while(resultSet.next()) {
				no_rows = Integer.parseInt(resultSet.getString(1));
			}
		}
		catch (SQLException e){
			throw new Error("Problem", e);
		}finally{
			try{
				if (conn!=null){
					conn.close();
				}
			} catch (SQLException ex){
				System.out.println(ex.getMessage());
			}
		}
		return no_rows;

	}

	public static int lastTransactionNumber(String atmId){
		Connection conn = null;
		ResultSet resultSet = null;
		int result = 0;
		try {
			// Connect to DB
			conn = DriverManager.getConnection("jdbc:sqlserver://soft2412.cyg3iolyiokd.ap-southeast-2.rds.amazonaws.com:1433;", "admin", "gr0up!wo");
			Statement statement = conn.createStatement();
			// Create and Run Query
			String get_lastTransactionID = "Select TOP 1 transaction_id FROM xyzBank.dbo.transactions Order By transaction_id DESC";
			resultSet = statement.executeQuery(get_lastTransactionID);
			// Print Result
			while(resultSet.next()) {
				result = Integer.parseInt(resultSet.getString(1));
			}
		}
		catch (SQLException e){
			throw new Error("Problem", e);
		}finally{
			try{
				if (conn!=null){
					conn.close();
				}
			} catch (SQLException ex){
				System.out.println(ex.getMessage());
			}
		}
		return result;

	}

	public static void updateTransaction(int id, TransactionType type, double altered_amount, double new_balance, String atmId){
		Connection conn = null;

		try {
			// Connect to DB
			conn = DriverManager.getConnection("jdbc:sqlserver://soft2412.cyg3iolyiokd.ap-southeast-2.rds.amazonaws.com:1433;", "admin", "gr0up!wo");
			Statement statement = conn.createStatement();
			// Write Query and Update Database
			if(type == TransactionType.CHECKBALANCE){
				String update_query = "INSERT INTO xyzBank.dbo.transactions " +
						"(transaction_type, account_balance, atm_id) " +
						"VALUES ( " + "'BALANCE CHECK', " + new_balance + ", " + atmId + ")";
				statement.executeUpdate(update_query);
			}
			else if(type == TransactionType.DEPOSIT){
				String update_query = "INSERT INTO xyzBank.dbo.transactions " +
						"(transaction_type, amount_deposited, account_balance, atm_id) " +
						"VALUES ( " + "'DEPOSIT', " + altered_amount + ", " + new_balance + ", " + atmId + ")";
				statement.executeUpdate(update_query);
			}
			else if(type == TransactionType.WITHDRAW){
				String update_query = "INSERT INTO xyzBank.dbo.transactions " +
						"(transaction_type, amount_withdrawn, account_balance, atm_id) " +
						"VALUES ( " + "'WITHDRAW', " + altered_amount + ", " + new_balance + ", " + atmId + ")";
				statement.executeUpdate(update_query);
			}

		}
		catch (SQLException e){
			throw new Error("Problem", e);
		}finally{
			try{
				if (conn!=null){
					conn.close();
				}
			} catch (SQLException ex){
				System.out.println(ex.getMessage());
			}
		}


	}

	public static void updateCustomer(String cardNum, TransactionType type, float amount){
		Connection conn = null;

		try {
			// Connect to DB
			conn = DriverManager.getConnection("jdbc:sqlserver://soft2412.cyg3iolyiokd.ap-southeast-2.rds.amazonaws.com:1433;", "admin", "gr0up!wo");

			// Write Query and Update Database
			String update_query = "UPDATE xyzBank.dbo.customers " + "SET available_funds = " + amount + "where card_number = " + cardNum + ";";
			Statement statement = conn.createStatement();
			statement.executeUpdate(update_query);
			conn.close();


		}
		catch (SQLException e){
			throw new Error("Problem", e);
		}finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}

	public static void updateCardStatus(String cardNumber, String cardStatus){
		Connection conn = null;

		try {
			// Connect to DB
			conn = DriverManager.getConnection("jdbc:sqlserver://soft2412.cyg3iolyiokd.ap-southeast-2.rds.amazonaws.com:1433;", "admin", "gr0up!wo");

			// Write Query and Update Database
			String update_query = "UPDATE xyzBank.dbo.customers " + "SET card_status = '" + cardStatus + "' where card_number = " + cardNumber + ";";
			Statement statement = conn.createStatement();
			statement.executeUpdate(update_query);
			conn.close();


		}
		catch (SQLException e){
			throw new Error("Problem", e);
		}finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}

}
