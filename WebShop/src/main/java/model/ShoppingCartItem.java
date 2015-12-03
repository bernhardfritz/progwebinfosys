package model;

public class ShoppingCartItem {
	private Item item;
	private int amount;
	
	public ShoppingCartItem(Item item) {
		this.item = item;
		amount = 1;
	}
	
	public Item getItem() {
		return item;
	}
	
	public void setItem(Item item) {
		this.item = item;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
}
