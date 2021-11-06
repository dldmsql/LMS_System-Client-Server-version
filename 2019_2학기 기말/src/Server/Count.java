package Server;

public class Count {
	static int count = 0;
	
	public int getCount() {
		return this.count;
	}
	public void setCount(int count) {
		//lock
		this.count = count;
	}
	public int addCount() {
		//lock
		this.count = this.count + 1;
		//unlock
		return this.count;
	}
}
