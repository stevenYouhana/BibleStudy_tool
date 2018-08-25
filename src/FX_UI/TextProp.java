package FX_UI;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;

import java.util.LinkedList;

import javafx.scene.control.TextArea;

public class TextProp implements Runnable {
	Utility.Log p = new Utility.Log(); 
	TextField txtField;
	TextArea txtArea;
	
	String id = null;
	private static LinkedList<TextProp> list = new LinkedList<>();

	public TextProp(TextInputControl input) {
		if(input instanceof TextField) {
			this.txtField = new TextField();
			this.txtField = (TextField) input;
			id = txtField.getId();
			list.add(this);
		}
		else if(input instanceof TextArea) {
			this.txtArea = new TextArea();
			this.txtArea = (TextArea) input;
			id = txtArea.getId();
			list.add(this);
		}
		else return;
	}
	private void managePlaceHolders() {
		list.forEach(obj -> {
			switch(obj.id) {
			case "verse": obj.setFocusProperty(Home.actV_PHOLDER); p.p(Home.actV_PHOLDER); 
			break;
			
			case "notes": obj.setFocusProperty(Home.coment_PHOLDER); p.p(Home.coment_PHOLDER);
			break;
			
			case "References": obj.setFocusProperty(Home.ref_PHOLDER); p.p(Home.ref_PHOLDER);
			break;
			
			case "ch": obj.setFocusProperty(Home.ch_PHOLDER); p.p("CH");
			break;
			
			case "verse number": obj.setFocusProperty(Home.vnum_PHOLDER);
			break;
			
			case "version": obj.setFocusProperty(Home.version_PHOLDER);
			break;
			//	txtSearch
			default: obj.setFocusProperty(Home.search_PHOLDER);
			break;
			}
		});
		
	}
	public void setFocusProperty(String def) {
		if(txtField != null) {
			txtField.focusedProperty().addListener( (obj, of, on) -> {
				if(!on && txtField.getText().isEmpty()) txtField.setText(def);
	    			else if(on && txtField.getText().equals(def)) txtField.setText("");
			});	
		}
		else if(txtArea != null) {
			txtArea.focusedProperty().addListener( (obj, of, on) -> {
				if(!on && txtArea.getText().isEmpty()) txtArea.setText(def);
	    			else if(on && txtArea.getText().equals(def)) txtArea.setText("");
			});	
		}

	}
	@Override
	public void run() {
		if(list.size() == Home.txtProps.length) {
			p.p("running TextProps: length of array: "+Home.txtProps.length);
			list.forEach(e -> {
				p.p(e.id);
			});
			managePlaceHolders();
		
		}
	}
}
