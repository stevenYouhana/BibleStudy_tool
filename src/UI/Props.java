package UI;

import java.util.Arrays;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import Study.Bible;
import Study.Verse;
import Utility.Log;

public abstract class Props {
	JFrame frame;
	JList<String> list;
	DefaultListModel<String> model;
	public Log p = new Log();
	
	Props(JFrame frame, JList<String> list, DefaultListModel<String> model) {
		this(frame);
		this.list = list;
		this.model = model;
		this.list.setModel(this.model);
	}
	
	Props(JFrame frame) {
		this.frame = frame;
	}
	Props(){
		
	}
	abstract void update(int[] verseData);
	abstract JList<String> getListing(); 
	
}

class RefedVerses extends Props {
	
	public RefedVerses(JFrame frame, JList<String> list, DefaultListModel<String> model) {
		super(frame,list,model);
	}
	public RefedVerses() {
		
	}
	
	@Override
	public void update(int[] verseData) {
		//model.clear();
		p.p("verseData: "+verseData[0]);
		for(Verse v : Bible.mass_verses) {
			if(Arrays.equals(verseData, v.getVerseData()) && 
					!(v.getReferences().isEmpty())) {
				for(int data : v.getReferences()) {
					//p.p("found for "+v.toString()+">> "+data[0]+data[1]+data[2]);
					model.addElement(verseBlock(data));
				}
			}
		}
	}
	
	private String verseBlock(int id) {
		int[] data = Bible.Book_Verses.getData(id);
		return Bible.Book_Verses.MAP.get(data[0])+" "+data[1]+": "+data[2];
	}
	
	@Override
	public JList<String> getListing() {
		return list;
	}
	public class TestClass {
		public void test(int[] data) {
			p.p("TEST METHOD "+data[0]);
		}
	}
}

