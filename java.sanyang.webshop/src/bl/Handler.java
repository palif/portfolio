package bl;

import java.util.ArrayList;

import db.Database;

public class Handler {
	
	private static Handler app = new Handler();
	private static Database jdbc = Database.getInstance();
	
	private Handler() {}
	
	/**
	 * There is only one instance of this object because it is a singleton class
	 * @return
	 */
	public static Handler getInstance() {
		return app;
	}
	
	public EnumAuth tryLogin(String username, String password) {
		return jdbc.tryLogin(username, password);
	}
	
	public boolean trySignup(String username, String password, EnumAuth auth) {
		return jdbc.trySignup(username, password, auth);
	}
	
	public ArrayList<Product> getAllProduct() {
		ArrayList<Product> list;
		list = jdbc.getAllProduct();
		return list;
	}
	
	public ArrayList<Product> getProductByPrice(int price) {
		ArrayList<Product> list;
		list = jdbc.getProductByPrice(price);
		return list;
	}
	
	public Product getProductById(String id) {
		Product Product;
		Product = jdbc.getProductById(id);
		return Product;
	}
	
	public ArrayList<Product> getProductsById(String id){
		return jdbc.getProductsById(id);
	}
	
	public ArrayList<String> getAllTransaction() {
		return jdbc.getAllTransaction();
	}
	
	public boolean insertTransaction(ArrayList<Product> transaction, String username) {
		System.out.println("pending..");
		return jdbc.insertTransaction(transaction, username);
	}
	
	public boolean insertProduct(Product product) {
		System.out.println("pending..");
		return jdbc.insertProduct(product);
	}
	
	public boolean deleteProductById(String id) {
		return jdbc.deleteProductById(id);
	}
	
}
