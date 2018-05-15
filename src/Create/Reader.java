package Create;

import java.io.*;

public class Reader extends Task {

	public Reader(String dir, String fileName) {
		super(dir, fileName);
	}
	
	@Override
	public void doTask() {
		char c = ' ';
		try(FileInputStream fis = new FileInputStream(dir+fileName)){
			BufferedInputStream bis = new BufferedInputStream(fis);
			while (bis.available() > 0) {
				text += (char)bis.read();	 
			}
		}
		catch(IOException ioe) {
			System.out.println(ioe);
		}
		System.out.println(text);
		System.out.println("succes");	
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getText() {
		return text;
	}
	public void setText(String t) {
		text = t;
	}

}
