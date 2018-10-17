package bl;

public enum EnumAuth {
	USER("USER"), ADMIN("ADMIN"), STOCK_PERSONNEL("STOCK_PERSONNEL");
	
	private String name;
	
	EnumAuth(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
