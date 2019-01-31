import java.io.IOException;

public abstract class BookContent implements Hashable {
	private String type;
	private int prev;
		
	public BookContent(String typeName) {
		this.type = typeName;
		this.prev = 0;
	}
	
	@Override
	public int hashMe() {
		int checksum = 0;
		String s = this.toString();
		for(int i=0; i<s.length(); i++){
			checksum = (checksum >> 1) + ((checksum & 1) << 15);
			checksum += s.charAt(i);
			checksum &= 0xffff;
		}
		return checksum;
	}
	
	public abstract boolean verifyContent() throws IOException;
	
	public int getPrev(){
		return prev;
	}
	
	public BookContent getPrevContent(){
		if(prev < 0 || prev >= Book.addrSize){
			System.out.println("Prev value was set out of bounds: "+prev);
			return null;
		}
		return Book.nodeAddrs[prev];
	}
	
	public String getType(){
		return type;
	}
	
	public void setPrev(int newPrev){
		if(newPrev < 0 || newPrev >= Book.addrSize){
			System.out.println("You are trying to set prev an invalid value!");
			return;
		}
		prev = newPrev;
		
		return;
	}

	public String toString() {
		String temp = "Type: " + this.type + " ";
		temp += "previous: " + this.prev +" ";
		return temp;
	}
	
}
