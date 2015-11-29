package model;

// 000000000 -> no privileges
// 111111111 -> all privileges

public class Privileges {
	long bitmapValue;
	
	public Privileges(long bitmap) {
		this.bitmapValue = Integer.parseInt(String.valueOf(bitmap), 2);
	}

	public boolean isCategoryRead() {
		return (bitmapValue & 0b100000000000) > 0;
	}

	public boolean isCategoryWrite() {
		return (bitmapValue & 0b010000000000) > 0;
	}

	public boolean isCategoryDelete() {
		return (bitmapValue & 0b001000000000) > 0;
	}

	public boolean isItemRead() {
		return (bitmapValue & 0b000100000000) > 0;
	}

	public boolean isItemWrite() {
		return (bitmapValue & 0b000010000000) > 0;
	}

	public boolean isItemDelete() {
		return (bitmapValue & 0b000001000000) > 0;
	}

	public boolean isItemCommentRead() {
		return (bitmapValue & 0b000000100000) > 0;
	}

	public boolean isItemCommentWrite() {
		return (bitmapValue & 0b000000010000) > 0;
	}

	public boolean isItemCommentDelete() {
		return (bitmapValue & 0b000000001000) > 0;
	}
	
	public boolean isUserPromote() {
		return (bitmapValue & 0b000000000100) > 0;
	}
	
	public boolean isUserDemote() {
		return (bitmapValue & 0b000000000010) > 0;
	}
	
	public boolean isUserDelete() {
		return (bitmapValue & 0b000000000001) > 0;
	}
}