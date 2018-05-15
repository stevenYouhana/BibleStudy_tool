package Create;

import java.io.*;

public class Writer extends Task {

	public Writer(String dir, String fileName, String text) {
		super(dir,fileName,text);
	}
	@Override
	public void doTask() {
		try(FileOutputStream fos = new FileOutputStream(dir+fileName)){
			BufferedOutputStream bos =  new BufferedOutputStream(fos);
			byte[] b = text.getBytes();
			bos.write(b);
			bos.flush();
			System.out.println("succes");
		}
		catch(IOException ioe) {
			System.out.println(ioe);
		}
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getDir() {
		return dir;
	}
	public String getFName() {
		return fileName;
	}
	public String getNotes() {
		return text;
	}

}
