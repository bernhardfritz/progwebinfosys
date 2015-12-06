package model;

import java.util.*;

public class ShoppingCart {
	private List<ShoppingCartItem> content;
	
	public ShoppingCart() {
		this.content = new ArrayList<ShoppingCartItem>();
	}
	
	public int indexOf(Item item) {
		for(int i = 0; i < content.size(); i++)
			if(content.get(i).getItem().getId() == item.getId()) return i;
		return -1;
	}
	
	public void addItem(Item item) {
		int i = indexOf(item);
		if(i > -1) { // ShoppingCart contains item
			ShoppingCartItem sci = content.get(i);
			sci.setQuantity(sci.getQuantity() + 1);
		} else content.add(new ShoppingCartItem(item));
	}
	
	public void setQuantity(Item item, Integer quantity) {
		int i = indexOf(item);
		if(i > -1) { // ShoppingCart contains item
			content.get(i).setQuantity(quantity);
		} else {
			addItem(item);
			setQuantity(item, quantity);
		}
	}
	
	public void deleteItem(Item item) {
		int i = indexOf(item);
		if(i > -1) {
			content.remove(i);
		}
	}
	
	public List<ShoppingCartItem> getContent() {
		return content;
	}
}