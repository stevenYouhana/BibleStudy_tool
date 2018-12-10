package UI;
import javax.swing.*;

import Utility.Log;
import javafx.scene.control.Button;

public class Utilities {
	static Log p = new Log();
	public static void disable_buttons(Button[] buttons) {
		p.p("disable_buttons");
		for(Button button : buttons) {
			p.p("disable buttons");
			button.disableProperty().set(false);
		}
	}
	
	public static void enable_buttons(Button[] buttons) {
		p.p("");
		for(Button button : buttons) {
			p.p("buttons clossed");
			button.disableProperty().set(true);
		}
	}
}
