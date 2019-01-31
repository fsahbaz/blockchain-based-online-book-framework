import java.util.ArrayList;
import java.util.regex.*;

public class TextContent extends BookContent {
	private String text;
	private static final int limitSize = 250;
	
	public TextContent(String text) {
		super("text");
		this.text = text;
	}
	
	public boolean verifySize() {
		String[] words = this.text.split(" ");
		if(words.length <= limitSize) {
			return true;
		} else {
			return false;
		}
	}
	
	boolean findCitation(String citation) {
		
		boolean bool = false;
		BookContent cont = this.getPrevContent();
		ReferenceContent ref;
		while(cont != null) {
			if(cont instanceof ReferenceContent) {
				ref = (ReferenceContent) cont;
				if(ref.getRefID().equals(citation)) bool = true;
			}
			cont = cont.getPrevContent();
		}
		return bool;
	}
	
	public boolean verifyCitations() {
		
		boolean midBool = true;
		boolean finBool = false;
		ArrayList<String> ids = new ArrayList<String>();
		String in = this.text;
		
		String[] sBrac = in.split("\\[");
		String[] eBrac = in.split("\\]");
		
		Pattern p = Pattern.compile("\\[(.*?)\\]");
		Matcher m = p.matcher(in);

		while(m.find()) {
			ids.add(m.group(1));
		}
		
		for(int i=1; i<sBrac.length; i++) {
			if(!sBrac[i].contains("]")) {
				midBool = false;
				System.out.println("The following text contains ill-formed references: " + this.text);
			}
			
		}
		
		for(int j=0; j<eBrac.length-1; j++) {
			if(!eBrac[j].contains("[")) {
				midBool = false;
				System.out.println("The following text contains ill-formed references: " + this.text);
			}
			
		}
		
		if(midBool) {
			for(String id : ids) {
				if(findCitation(id)) {
					finBool = true;
				} else {
					finBool = false;
					System.out.println("Reference for the citation \"" + id + "\" cannot be found.");
				}
			}
		}
		return finBool;
	}
	
	public boolean verifyContent() {
		if(verifySize() && verifyCitations()) {
			return true;
		} else {
			return false;
		}
	}
	
	public String getText(){
		return text;
	}
	
	public String toString() {
		String temp = super.toString();
		temp += "text: " + this.text;
		return temp;
	}
	
}
