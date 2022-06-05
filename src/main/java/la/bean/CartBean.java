package la.bean;

import java.util.ArrayList;
import java.util.List;

public class CartBean {
	private List<ProductBean> products = new ArrayList<ProductBean>();
	private int total;
	
	public CartBean() {}
	
	public List<ProductBean> getProducts() {
		return products;
	}
	
	public int getTotal() {
		return total;
	}
	
	public void addCart(ProductBean bean, int quantity) {
		ProductBean product = null;
		for (ProductBean p : products) {
			if (p.getProductId() == bean.getProductId()) {
				product = p;
				break;
			}
		}
		
		if (product == null) {
			bean.setQuantity(quantity);
			products.add(bean);
		} else {
			product.setQuantity(quantity + product.getQuantity());
		}
		recalcTotal();
	}
	
	public void deleteCart(int id) {
		for (ProductBean p : products) {
			if (p.getProductId() == id) {
				products.remove(p);
				break;
			}
		}
		recalcTotal();
	}
	
	private void recalcTotal() {
		total = 0;
		for (ProductBean p : products) {
			total += p.getPrice() * p.getQuantity();
		}
	}
}
