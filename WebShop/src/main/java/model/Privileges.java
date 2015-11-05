package model;

// 000000000 -> no privileges
// 111111111 -> all privileges

public class Privileges {
	int bitmap;
	
	public Privileges(int bitmap) {
		this.bitmap = bitmap;
	}

	public boolean isCategoryRead() {
		return (bitmap & 0b100000000) == 1;
	}

	public boolean isCategoryWrite() {
		return (bitmap & 0b010000000) == 1;
	}

	public boolean isCategoryDelete() {
		return (bitmap & 0b001000000) == 1;
	}

	public boolean isItemRead() {
		return (bitmap & 0b000100000) == 1;
	}

	public boolean isItemWrite() {
		return (bitmap & 0b000010000) == 1;
	}

	public boolean isItemDelete() {
		return (bitmap & 0b000001000) == 1;
	}

	public boolean isItemCommentRead() {
		return (bitmap & 0b000000100) == 1;
	}

	public boolean isItemCommentWrite() {
		return (bitmap & 0b000000010) == 1;
	}

	public boolean isItemCommentDelete() {
		return (bitmap & 0b000000001) == 1;
	}
}
