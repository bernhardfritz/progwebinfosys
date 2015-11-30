package model;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
	private Map<Item, Integer> content;
	
	public ShoppingCart() {
		this.content = new HashMap<Item, Integer>();
	}
	
	public void addItem(Item item) {
		content.put(item, 1);
	}
	
	public void setAmount(Item item, Integer amount) {
		if (content.containsKey(item)) {
			content.put(item, amount);
		}
	}
	
	public void deleteItem(Item item) {
		content.remove(item);
	}
	
	public Map<Item, Integer> getContent() {
		return content;
	}
}