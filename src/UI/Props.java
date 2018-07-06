package UI;

import java.util.Arrays;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import Study.Bible;
import Study.Verse;

public abstract class Props {
	JFrame frame;
	JList<String> list;
	DefaultListModel<String> model;
	
	Props(JFrame frame, JList<String> list, DefaultListModel<String> model) {
		this(frame);
		this.list = list;
		this.model = model;
		this.list.setModel(this.model);
	}
	
	Props(JFrame frame) {
		this.frame = frame;
	}
}

class RefedVerses extends Props {
	
	public RefedVerses(JFrame frame, JList<String> list, DefaultListModel<String> model) {
		super(frame,list,model);
	}
	
	public void update(int[] verseData) {
		model.clear();
		for(Verse v : Bible.mass_verses) {
			if(Arrays.equals(verseData, v.getVerseData()) && 
					!(v.getReferences().isEmpty())) {
				for(int[] data : v.getReferences()) {
					System.out.println("getting ref: ");AddRef.getVC(v.getReferences().get(0));
					model.addElement(verseBlock(data));
				}
			}
		}
	}
	
	private String verseBlock(int[] data) {
		return Bible.Book_Verses.MAP.get(data[0])+" "+data[1]+": "+data[2];
	}
	
	public JList<String> getListing() {
		return list;
	}
}

