package Aquire;

import Create.*;
import java.util.Scanner;

import Create.*;

public class User {
	private char use = ' ';
	private String dir = "//Users//steven//Documents/";
	private String fileName = "";
	private String notes = "";
	Task task;
	
	public User(char use) {
		this.use = use;
	}

	public Task usage() {
		
		if(use == 'r') {
			Scanner abstScan = new Scanner(System.in);
			System.out.println("Please type file name");
			fileName = abstScan.next();
			Reader read = new Reader(dir, fileName);
			System.out.println("FNAME: "+fileName);
			System.out.println("DIR: "+dir);
			abstScan.close();
			return read;
		}
		else if (use == 'w'){
			System.out.println("Dir: ");
			Scanner yourDir = new Scanner(System.in);
			dir = yourDir.nextLine();

			System.out.println("File name: ");
			Scanner yourFName = new Scanner(System.in);
			fileName = yourFName.nextLine();
			
			System.out.println("your notes: ");
			Scanner yourNotes = new Scanner(System.in);
			notes = yourNotes.nextLine();
			
			Task write = new Writer(dir, fileName, notes);
			yourDir.close();
			yourFName.close();
			yourNotes.close();
			return write;
		}
		else {
			System.out.println("invalid value!");
		}
		System.out.println("Thanks");
		return null;
	}

}
