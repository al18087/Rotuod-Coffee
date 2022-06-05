package la.bean;

public class ProductBean {
	private int productId;
	private String name;
	private int price;
	private int quantity;
	
	public ProductBean() {}
	
	public ProductBean(int productId, String name, int price) {
		this.productId = productId;
		this.name = name;
		this.price = price;
	}
	
	public ProductBean(int productId, String name, int price, int quantity) {
		this.productId = productId;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}
	
	public int getProductId() {
		return productId;
	}
	
	public void setProductId(int productId) {
		this.productId = productId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
