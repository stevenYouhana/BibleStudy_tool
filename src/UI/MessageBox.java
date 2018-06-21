package UI;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MessageBox {
	private JFrame frame;
	private final String COMMENT_UPDATE = "Do you want to update the comment?";
	private final String WARNING = "Warning!";
	private final String INFO = "Alert";
	private final String NULLVERSE = "verse being referenced not added.";
	
	public MessageBox(JFrame frame) {
		this.frame = frame;
	}
	public int existingComment() {
		return JOptionPane.showConfirmDialog(
				frame, COMMENT_UPDATE,WARNING,JOptionPane.YES_NO_OPTION);
	}
	public void addReferenceVerse() {
		JOptionPane.showMessageDialog(frame, NULLVERSE, INFO, JOptionPane.INFORMATION_MESSAGE);
	}
	public void unknownException(String exc, String src) {
		JOptionPane.showMessageDialog(frame, exc, INFO, JOptionPane.WARNING_MESSAGE);
	}
}
