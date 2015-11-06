package model;

// 000000000 -> no privileges
// 111111111 -> all privileges

public class Privileges {
	int bitmapValue;
	
	public Privileges(int bitmap) {
		this.bitmapValue = Integer.parseInt(String.valueOf(bitmap), 2);
	}

	public boolean isCategoryRead() {
		return (bitmapValue & 0b100000000) > 0;
	}

	public boolean isCategoryWrite() {
		return (bitmapValue & 0b010000000) > 0;
	}

	public boolean isCategoryDelete() {
		return (bitmapValue & 0b001000000) > 0;
	}

	public boolean isItemRead() {
		return (bitmapValue & 0b000100000) > 0;
	}

	public boolean isItemWrite() {
		return (bitmapValue & 0b000010000) > 0;
	}

	public boolean isItemDelete() {
		return (bitmapValue & 0b000001000) > 0;
	}

	public boolean isItemCommentRead() {
		return (bitmapValue & 0b000000100) > 0;
	}

	public boolean isItemCommentWrite() {
		return (bitmapValue & 0b000000010) > 0;
	}

	public boolean isItemCommentDelete() {
		return (bitmapValue & 0b000000001) > 0;
	}
}