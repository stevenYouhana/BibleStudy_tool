package FX_UI;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;

import java.util.HashSet;

import javafx.scene.control.TextArea;

public class TextProp implements Runnable {
	Utility.Log p = new Utility.Log(); 
	TextField txtField = null;
	TextArea txtArea = null;
	
	String id = null;
	//Use to search through prop IDs
	static HashSet<TextProp> props = new HashSet<>();

	public TextProp(TextInputControl input) {
		if(input instanceof TextField) {
			this.txtField = new TextField();
			this.txtField = (TextField) input;
			id = txtField.getId();
			props.add(this);
		}
		else if(input instanceof TextArea) {
			this.txtArea = new TextArea();
			this.txtArea = (TextArea) input;
			this.txtArea.setWrapText(true);
			id = txtArea.getId();
			props.add(this);
		}
		else return;
		
		if(input.getId() == Home.REF) input.setEditable(false);
		
	}
	public TextProp() {}
	public String getID() {
		return id;
	}
	public TextField getTextField() {
		return txtField;
	}
	public TextArea getTextArea() {
		return txtArea;
	}
	private void managePlaceHolders() {
		props.forEach(obj -> {
			switch(obj.id) {
			case "verse": obj.setPlaceHolder(Home.ACT_V);
			break;
			
			case "notes": obj.setPlaceHolder(Home.CMNT);
			break;
			
			case "references": obj.setPlaceHolder(Home.REF);
			break;
			
			case "ch": obj.setPlaceHolder(Home.CH);
			break;
			
			case "verse number": obj.setPlaceHolder(Home.VNUM);
			break;
			
			case "version": obj.setPlaceHolder(Home.VERSION);
			break;
			//	txtSearch
			default: obj.setPlaceHolder(Home.SEARCH);
			break;
			}
		});
	}
	private void setPlaceHolder(String def) {
		try {
			if(txtField != null) {
				txtField.setPromptText(def);
			}
			else if(txtArea != null) {
				txtArea.setPromptText(def);
			}
		}
		catch(NullPointerException npe) {
			p.p("ERR setPlaceHolder(str): "+npe);
		}

	}
	@Override
	public void run() {
		if(props.size() == Home.txtProps.length) {
			managePlaceHolders();
		}
	}
}
