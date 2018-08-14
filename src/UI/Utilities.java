package UI;
import javax.swing.*;

import Utility.Log;

public class Utilities {
	static Log p = new Log();
	public static void disable_buttons(JButton[] buttons) {
		p.p("disable_buttons");
		for(JButton button : buttons) {
			p.p("disable buttons");
			button.setEnabled(false);
		}
	}
	
	public static void enable_buttons(JButton[] buttons) {
		p.p("");
		for(JButton button : buttons) {
			p.p("buttons clossed");
			button.setEnabled(true);
		}
	}
}
