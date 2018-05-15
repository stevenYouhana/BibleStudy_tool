package Create;

public abstract class Task {
	
	String dir = "";
	String fileName = "";
	String text = "";
	
	public Task(String dir, String fileName, String text) {
		this.dir = dir;
		this.fileName = fileName;
		this.text = text;
	}
	public Task(String dir, String fileName) {
		this.dir = dir;
		this.fileName = fileName;
	}
	public Task() {
		
	}
	public abstract void doTask();
	
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
