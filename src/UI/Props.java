package UI;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;

public abstract class Props {
	JFrame frame;
	
	Props(JFrame frame) {
		this.frame = frame;
	}
	class RefedVerses extends Props {
		JList<String> list;
		DefaultListModel<String> model = new DefaultListModel<String>();
		
		public RefedVerses(JFrame frame) {
			super(frame);
		}
		
		public void update(int[] verseData) {
			
		}
		public JList<String> getListing() {
			
			return list;
		}
	}
}
