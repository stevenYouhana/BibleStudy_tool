package UI;
import javax.swing.*;

public class Utilities {
	
	public void disable_buttons(JButton[] buttons) {
		for(JButton button : buttons) {
			button.setEnabled(false);
		}
	}
	
	public void enable_buttons(JButton[] buttons) {
		for(JButton button : buttons) {
			button.setEnabled(true);
		}
	}
}
