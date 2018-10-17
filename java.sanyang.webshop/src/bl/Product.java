package bl;

public class Product {

	private String title;
	private int price;
	private String productId;
	private int quantity;
	
	public Product(String title, int price, String ProductId, int quantity) {
		this.title = title;
		this.price = price;
		this.productId = ProductId;
		this.quantity = quantity;
	}
	
	public String getTitle() { return this.title; }	
	public int getPrice() { return this.price; }
	public String getProductId() { return this.productId; }
	public int getQuantity() { return this.quantity; }
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public void setProductId(String ProductId) {
		this.productId = ProductId;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public String toString() {
		return this.getProductId() + " " + this.getTitle() + " " + this.getPrice() + " " + this.getQuantity();
	}
	
	
}
