package ui;

import java.util.Hashtable;
import java.util.ArrayList;

public class Cart {
	
	private ArrayList<Hashtable> cart = new ArrayList<>();
	
	public boolean addProduct(Hashtable product) {
		if ( cart.add(product)) return true;
		return false;
	}
	
	public ArrayList<Hashtable> getAllProducts() {
		return cart;
	}
	
	public boolean removeProduct(Hashtable product) {
		if ( cart.remove(product)) return true;
		return false;
	}
	
	public void clear() {
		if (!cart.isEmpty()) cart.clear();
	}
	
	
}
