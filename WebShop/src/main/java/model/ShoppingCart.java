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
			sci.setAmount(sci.getAmount() + 1);
		} else content.add(new ShoppingCartItem(item));
	}
	
	public void setAmount(Item item, Integer amount) {
		int i = indexOf(item);
		if(i > -1) { // ShoppingCart contains item
			content.get(i).setAmount(amount);
		} else {
			addItem(item);
			setAmount(item, amount);
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