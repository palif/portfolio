package bl;

import java.util.ArrayList;
import java.util.Hashtable;

public class Look {
	
	/**
	 * There is only one instance of this object because it is a singleton class
	 * @return
	 */
	public Look() {
		
	}
	
	public EnumAuth tryLogin(String username, String password) {
		Handler handler = Handler.getInstance();
		return handler.tryLogin(username, password);
	}
	
	public boolean trySignup(String username, String password) {
		Handler handler = Handler.getInstance();
		EnumAuth auth = EnumAuth.USER;
		return handler.trySignup(username, password, auth);
	}
	
	public ArrayList<Hashtable<String, Object>> getAllProduct() {
		ArrayList<Hashtable<String, Object>> list = new ArrayList<>();
		Handler handler = Handler.getInstance();
		Hashtable<String, Object> ht;
		for(Product p : handler.getAllProduct()) {
			ht = new Hashtable<>();
			ht.put("id", p.getProductId());
			ht.put("title", p.getTitle());
			ht.put("price", p.getPrice());
			ht.put("quantity", p.getQuantity());
			list.add(ht);
		}
		return list;
	}
	
	public ArrayList<Hashtable<String, Object>> getProductByPrice(int price) {
		ArrayList<Hashtable<String, Object>> list = new ArrayList<>();
		Handler handler = Handler.getInstance();
		Hashtable<String, Object> ht;
		for(Product p : handler.getProductByPrice(price)) {
			ht = new Hashtable<>();
			ht.put("id", p.getProductId());
			ht.put("title", p.getTitle());
			ht.put("price", p.getPrice());
			ht.put("quantity", p.getQuantity());
			list.add(ht);
		}
		return list;
	}
	
	public ArrayList<Hashtable<String, Object>> getProductsById(String id){
		ArrayList<Hashtable<String, Object>> list = new ArrayList<>();
		Handler handler = Handler.getInstance();
		Hashtable<String, Object> ht;
		for(Product p : handler.getProductsById(id)) {
			ht = new Hashtable<>();
			ht.put("id", p.getProductId());
			ht.put("title", p.getTitle());
			ht.put("price", p.getPrice());
			ht.put("quantity", p.getQuantity());
			list.add(ht);
		}
		return list;
	}
	
	public Hashtable getProductById(String id) {
		Handler handler = Handler.getInstance();
		Product p = handler.getProductById(id);
		Hashtable<String, Object> ht = new Hashtable<>();
		ht.put("id", p.getProductId());
		ht.put("title", p.getTitle());
		ht.put("price", p.getPrice());
		ht.put("quantity", p.getQuantity());
		return ht;
	}
	
	
	
	public ArrayList<Hashtable<String, Object>> getAllTransaction() {
		Handler handler = Handler.getInstance();
		ArrayList<Hashtable<String, Object>> list = new ArrayList<>();
		Hashtable<String, Object> ht = new Hashtable<>();
		for(String s : handler.getAllTransaction()) {
			ht.put("transaction", s);
			list.add(ht);
		}
		return list;
	}
	
	public boolean insertTransaction(ArrayList<Hashtable> t, String username) {
		Handler handler = Handler.getInstance();
		ArrayList<Product> transaction = new ArrayList<>();
		Product p;
		int price, quantity;
		String id, title;
		for(Hashtable h : t) {
			
			id = (String) h.get("id");
			title = (String) h.get("title");
			price = (int) h.get("price");
			quantity = (int) h.get("quantity");
			
			p = new Product(title, price, id, quantity);
			System.out.println(p);
			
			transaction.add(p);
		}
		return handler.insertTransaction(transaction, username);
	}
	
	public boolean insertProduct(Hashtable h) {
		
		Handler handler = Handler.getInstance();
		String id = (String) h.get("id");
		String title = (String) h.get("title");
		int price = (int) h.get("price");
		int quantity = (int) h.get("quantity");
		
		Product p = new Product(title, price, id, quantity);
		System.out.println(p);
		
		return handler.insertProduct(p);
	}
	
	public boolean deleteProductById(String id) {
		Handler handler = Handler.getInstance();
		return handler.deleteProductById(id);
	}
	

}
