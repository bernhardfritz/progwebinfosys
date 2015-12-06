package model;

public class ShoppingCartItem {
	private Item item;
	private int quantity;
	
	public ShoppingCartItem(Item item) {
		this.item = item;
		quantity = 1;
	}
	
	public Item getItem() {
		return item;
	}
	
	public void setItem(Item item) {
		this.item = item;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int amount) {
		this.quantity = amount;
	}
}
