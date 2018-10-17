package db;

import java.sql.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import bl.EnumAuth;
import bl.Product;

public class Database {
	
	private static Connection conn;
	private static Database jdbc = new Database();
	
	/**
	 * Private constructor so that it wont return a new instance?
	 */
	private Database() {}
	 
	/**
	 * This 'JavaDatabaseConnectivity' class is a singleton class
	 * That is the reason it returns a static instance of itself,
	 * in other word, only one instance
	 * @return
	 */
	public static Database getInstance() {
		connect();
		return jdbc;
	}
	
	/**
	 * Connects to the database
	 */
	public static void connect() {
		
		try {
			String dbName = "webshop";
			String server = "jdbc:mysql://localhost:3306/" + dbName + "?UseClientEnc=UTF8&useSSL=false";
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(server, "root", "password");
			System.out.println("connected!");	
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Disconnect to the database
	 */
	public static void disconnect() {
		try {
			if (conn != null) conn.close();	
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Validates user and if user is valid, it will return an Enumeration on what type
	 * of user it is. 
	 * @param user
	 * @param password
	 * @return
	 */
	public EnumAuth tryLogin(String user, String password) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		System.out.println("user: " + user + ", password: " + password);
		String query = "SELECT * FROM User WHERE User.username = ? AND User.password = ?;";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user);
			pstmt.setString(2, password);
			rset = pstmt.executeQuery();
			if(rset.first()) {
				System.out.println("FOUND ONE USER..");
				for(EnumAuth e : EnumAuth.values()) {
					if(e.getName().equals(rset.getString("auth"))) {
						System.out.println("USER VALIDATED");
						return e;
					}
				}
			}
			
			System.out.println("USER IS NOT VALIDATED");
		}
		catch(NullPointerException e) {
			System.out.println("Null pointer exception: " + e.getMessage());
			e.printStackTrace();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {
			try {
				if(pstmt != null) pstmt.close();
				if(rset != null) rset.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public boolean trySignup(String username, String password, EnumAuth auth) {
		
		PreparedStatement pstmt = null;
		String query = "";
		
		try {
			conn.setAutoCommit(false);
			query = "INSERT User(username, password, auth) VALUES (?, ?, ?);";
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			pstmt.setString(3, auth.getName());
			
			if(pstmt.executeUpdate() == 0) {
				System.out.println("PUSER REGISTRATION ABORTED");
				throw new SQLException();
			}
			
			System.out.println("USER REGISTRATION COMPLETE");
			
			return true;
		}
		catch(NullPointerException e) {
			System.out.println("USER REGISTRATION ABORTED");
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		catch(SQLException e) {
			System.out.println("USER REGISTRATION ABORTED");
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				conn.setAutoCommit(true);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return false;
	}
	
	
	/**
	 * Returns all of the product
	 * @return
	 */
	public ArrayList<Product> getAllProduct() {
		
		ArrayList<Product> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "SELECT DISTINCT Product.productId, Product.title, Product.price, Stock.quantity  "
				+ "FROM Product, Stock "
				+ "WHERE Product.productId = Stock.productId;";
		Product Product;
		
		try {
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery(query);
			while(rset.next()) {
				Product = new Product(rset.getString("title"), rset.getInt("price"), rset.getString("productId"), rset.getInt("quantity"));
				list.add(Product);
			}
		}
		catch(NullPointerException e) {
			e.printStackTrace();
		}
		catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(rset != null) rset.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}
	
	/**
	 * Returns list of products that match the price 
	 * @param price
	 * @return
	 */
	public ArrayList<Product> getProductByPrice(int price) {
		
		ArrayList<Product> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "SELECT DISTINCT Product.productId, Product.title, Product.price, Stock.quantity  "
				+ "FROM Product, Stock "
				+ "WHERE Product.price= "+ price +" AND Product.productId = Stock.productId;";
		Product Product;
		try {
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery(query);
			while(rset.next()) {
				Product = new Product(rset.getString("title"), rset.getInt("price"), rset.getString("productId"), rset.getInt("quantity"));
				list.add(Product);
			}
		}
		catch(NullPointerException e) {
			e.printStackTrace();
		}
		catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(rset != null) rset.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	/**
	 * Returns a single product that match the specific id
	 * @param i
	 * @return
	 */
	public Product getProductById(String i) {
		System.out.println("pre SQL get product by id: " + i);
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "SELECT DISTINCT Product.productId, Product.title, Product.price, Stock.quantity  "
				+ "FROM Product, Stock "
				+ "WHERE Product.productId='"+ i.trim() +"' AND Product.productId = Stock.productId;";
		Product Product = null;
		try {
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery(query);
			System.out.println("row in resultset: " + rset.first());
			if(rset.first()) {
				System.out.println("if it is true then... " + rset.getString("title") + "... BINGO!");
				Product = new Product(rset.getString("title"), rset.getInt("price"), rset.getString("productId"), rset.getInt("quantity"));
			}
			
			return Product;
		}
		catch(NullPointerException e) {
			e.printStackTrace();
		}
		catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(rset != null) rset.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
	
	/**
	 * Returns a list of products where the string match
	 * the beginning or part of the beginning of the product id
	 * @param id
	 * @return
	 */
	public ArrayList<Product> getProductsById(String id){
		ArrayList<Product> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			String query = "SELECT DISTINCT Product.productId, Product.title, Product.price, Stock.quantity "
				+ "FROM Product, Stock WHERE Product.productId = Stock.productId AND Product.productId LIKE ?;";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id+"%");
			rset = pstmt.executeQuery();
			while(rset.next()) {
				list.add(new Product(rset.getString("title"), rset.getInt("price"), rset.getString("productId"), rset.getInt("quantity")));
			}
			return list;
		}
		catch(NullPointerException e) {
			e.printStackTrace();
		}
		catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(rset != null) rset.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
	
	/**
	 * Returns a list of all of the transactions
	 * @return
	 */
	public ArrayList<String> getAllTransaction() {
		ArrayList<String> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "SELECT * FROM Product, TransactionHistory WHERE Product.productId = TransactionHistory.productId;";
		String transaction;
		try {
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery(query);
			while(rset.next()) {
				transaction = "transactionsId: " + rset.getString("transactionId") + ", " +
						"productId: " + rset.getString("productId") + ", " + "title: "+ rset.getString("title") + ", " +
						"price: "+ rset.getInt("price") + ", " + ", user: " + rset.getString("username") + ", " + 
						"quantity: " + rset.getInt("quantity") + ", date: " + rset.getString("date");
				list.add(transaction);
			}
		}
		catch(NullPointerException e) {
			e.printStackTrace();
		}
		catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(rset != null) rset.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}
	
	/**
	 * This is a private method that helps 'insertTransaction()'
	 * by updating the stock database. 
	 * @param l
	 * @return
	 */
	private boolean updateQuantityOfStock(ArrayList<Product> l) {
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ArrayList<Product> list = l;
		String sql = "";
		
		try {
			conn.setAutoCommit(false);
			System.out.println("LIST: " + list.size());
			stmt = conn.createStatement();
			
			for(Product p : list) {
				sql = "UPDATE Stock SET quantity = quantity - 1 WHERE Stock.productId = '"+ p.getProductId() +"' AND Stock.quantity > 0;";
				if(stmt.executeUpdate(sql) == 0) {
					System.out.println("UPDATE ABORTED");
					throw new SQLException();
				}
			}
			System.out.println("UPDATE COMPLETED.");
			return true;
		}
		catch(NullPointerException e) {
			System.out.println("UPDATE ABORTED");
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		catch(SQLException e) {
			System.out.println("UPDATE ABORTED");
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				conn.setAutoCommit(true);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return false;

	}
	
	/**
	 * Returns a boolean of weather the transaction have been completed
	 * or disrupted and aborted. This method gets a list of products that is 
	 * going to be transaction by a user whom sends its unique 'username'
	 * @param transaction
	 * @param username
	 * @return
	 */
	public boolean insertTransaction(ArrayList<Product> transaction, String username) {
		
		PreparedStatement pstmt = null;
		ArrayList<Product> list = transaction;
		ArrayList<String> alreadyInserted = new ArrayList<String>();
		String query = "";
		int quantity = 0;
		LocalDate date = LocalDate.now();
		
		try {
			conn.setAutoCommit(false);
			System.out.println("LIST: " + list.size());
			
			for(Product p : list) {
				
				if(alreadyInserted.contains(p.getProductId())) continue;
				
				query = "INSERT TransactionHistory(productId, username, quantity, date) values (?, ?, ?, ?);";
				pstmt = conn.prepareStatement(query);
				
				pstmt.setString(1, p.getProductId());
				pstmt.setString(2, username);
				
				for(Product p1 : transaction) {
					if(p1.getProductId().equals(p.getProductId())) quantity++;
				}
				
				alreadyInserted.add(p.getProductId());
				
				pstmt.setInt(3, quantity);
				pstmt.setString(4, date.toString());
				
				if(pstmt.executeUpdate() == 0) {
					System.out.println("TRANSACTION ABORTED");
					throw new SQLException();
				}
				quantity = 0;
			}
			
			if(updateQuantityOfStock(transaction)) {
				System.out.println("TRANSACTION COMPLETED.");
				return true;
			} else {
				System.out.println("TRANSACTION ABORTED");
				throw new SQLException();
			}
		}
		catch(NullPointerException e) {
			System.out.println("TRANSACTION ABORTED");
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		catch(SQLException e) {
			System.out.println("TRANSACTION ABORTED");
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				conn.setAutoCommit(true);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return false;
		
	}

	/**
	 * Returns true if the insertion of a product have been completed. Otherwise it will return false
	 * @param product
	 * @return
	 */
	public boolean insertProduct(Product product) {
		
		PreparedStatement pstmt = null;
		String query = "";
		Product p = product;
		
		try {
			conn.setAutoCommit(false);
			query = "INSERT Product(productId, title, price) VALUES (?, ?, ?);";
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, p.getProductId());
			pstmt.setString(2, p.getTitle());
			pstmt.setInt(3, p.getPrice());
			
			if(pstmt.executeUpdate() == 0) {
				System.out.println("PRODUCT INSERTION ABORTED");
				throw new SQLException();
			}
			
			query = "INSERT Stock(productId, quantity) VALUES (?, ?);";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, p.getProductId());
			pstmt.setInt(2, p.getQuantity());
			
			if(pstmt.executeUpdate() == 0) {
				System.out.println("PRODUCT INSERTION ABORTED");
				throw new SQLException();
			}
			
			System.out.println("PRODUCT INSERTION COMPLETE");
			
			return true;
		}
		catch(NullPointerException e) {
			System.out.println("PRODUCT INSERTION ABORTED");
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		catch(SQLException e) {
			System.out.println("PRODUCT INSERTION ABORTED");
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				conn.setAutoCommit(true);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return false;
	}
	
	/**
	 * This method deletes a product by locating it by its own id and returns true
	 * if it completes its deletion 
	 * @param id
	 * @return
	 */
	public boolean deleteProductById(String id) {
		System.out.println("pedning deleting product /"+ id.trim() + "/..");
		PreparedStatement pstmt = null;
		String query = "";
		try {
			query = "DELETE FROM Stock WHERE Stock.productId = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id.trim());
			if(pstmt.executeUpdate() == 0) {
				System.out.println("DELETE ABORTED");
				throw new SQLException();
			}
			query = "DELETE FROM Product WHERE Product.productId = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id.trim());
			if(pstmt.executeUpdate() == 0) {
				System.out.println("DELETE ABORTED");
				throw new SQLException();
			}
			System.out.println("DELETION COMPLETED");
			return true;
		}
		catch(NullPointerException e) {
			System.out.println("DELETE ABORTED");
			e.printStackTrace();
		}
		catch(SQLException e) {
			System.out.println("DELETE ABORTED");
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}

		return false;
	}
	
}


