import java.net.*;
import java.io.*;

public class ReferenceContent extends BookContent {
	private String refID;
	private String title;
	private String[] authors;
	
	
	public ReferenceContent(String id, String ttl, String[] auts) {
		super("reference");
		String[] buff = new String[auts.length];
		for(int i=0; i < auts.length; i++) {
			buff[i] = auts[i];
		}
		this.authors = buff; 
		this.refID = id;
		this.title = ttl;
	}
	
	
	public boolean verifyContent() throws IOException {
		String surname;
		String name;
		char firstLet;
		boolean bool = false;
		for(String au : this.authors) {
			String[] temp = au.split(" ");
			surname = temp[temp.length-1];
			firstLet = surname.toLowerCase().charAt(0);
			String tempURL = "http://dblp.uni-trier.de/pers/hd/";
			tempURL += firstLet;
			tempURL += "/";	
			name = au.replace(surname, "");
			name = name.replace(' ', '_');
			name = name.substring(0, name.length()-1);
			name = name.replace(".", "=");	
			tempURL += surname + ":" + name;
			System.out.println("Connecting to URL: " + tempURL);
			URL dblp = new URL(tempURL);
			URLConnection yc = dblp.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			String content = "";
		    String str;
		    while((str = in.readLine()) != null) {
		    		content += str;
		    }
		    in.close();
		    if(content.contains(this.title)) {
		    		bool = true;
		    } else {
		    		bool = false; 
		    }
		}  
		return bool;
	}
	
	public String getRefID(){
		return refID;
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getAuthors(){
		String res = "";
		for(String aut:authors)
			res += " "+aut;
		return res;
	}
	
	public void setTitle(String newTitle){
		title = newTitle;
	}

	public String toString() {
		String temp = super.toString();
		temp += "ID: " + this.getRefID();
		temp += " title: " + this.getTitle();
		temp += " authors:" + this.getAuthors();
		return temp;
	}
	
}
